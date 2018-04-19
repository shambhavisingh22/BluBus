from . import mybus

from flask import jsonify,request
from app import es
import uuid
import pytz
from datetime import datetime

@mybus.route('/', methods=['GET']) # to test if app is working/ deployed without errors.
def test():
    return jsonify({"msg" : "Welcome to my BusApp"})

@mybus.route('/add_businfo',methods=['POST']) # not in front end app
def add_businfo():
    requestObject= request.get_json()    #in case of post get_json()
    try:

            #if id comes from front end then it is update and if not then it is addnew
            print "dollz"
            requestObject['start_Time']=datetime.strptime(requestObject['start_Time'], '%H:%M:%S')# formatted datetime, for comparison
            idn = str(uuid.uuid4()) #this generates a new random id and converts it to string
            es.index(index = "busdb",doc_type="businfo",id=idn,body=requestObject)  #create a new entry,it means add [ db, table]
            return jsonify({"response":"success","id":idn})

    except Exception as e:
        print str(e)
        return jsonify({"response":"failure","error":str(e)})

@mybus.route('/add_seatsinfo',methods=['POST']) # uss day pe kitni seats hain.
def add_seatsinfo():
    requestObject= request.get_json()    #in case of post get_json()
    try:
        result = es.get( index="busdb", doc_type="businfo",id = requestObject['bus_id'] ) # get method takes id and returns, 1 entry of that id
        if result["found"] == "false":
               return jsonify({"response": "failure", "error": "No bus with given id"})
        result = result.get('_source')
        if 'seatsinfo' not in result :# to check if no key in dict
            result['seatsinfo']= []
        result['seatsinfo'].append(requestObject)
        es.update(index="busdb", doc_type="businfo",id = requestObject['bus_id'],body = {"doc":result}) # update info of bus
        return jsonify({"response": "success"})

    except Exception as e:
        print str(e)
        return jsonify({"response":"failure","error":str(e)})

@mybus.route('/add_datesinfo',methods=['POST']) # which dates app will run
def add_datesinfo():
    requestObject= request.get_json()    #in case of post get_json()
    try:
        result = es.get( index="busdb", doc_type="businfo",id = requestObject['bus_id'] )
        if result["found"] == "false":
               return jsonify({"response": "failure", "error": "No bus with given id"})
        result = result.get('_source')
        if 'datesinfo' not in result :
            result['datesinfo']= []
        result['datesinfo'].append(requestObject['date'])
        es.update(index="busdb", doc_type="businfo",id = requestObject['bus_id'],body = {"doc":result})
        return jsonify({"response": "success"})

    except Exception as e:
        print str(e)
        return jsonify({"response":"failure","error":str(e)})

@mybus.route('/add_bookinfo',methods=['POST'])
def add_bookinfo():
    requestObject= request.get_json()    #in case of post get_json()
    try:

        #requestObject['start_Time']=datetime.strptime(requestObject['start_Time'], '%H:%M:%S')
        idn = str(uuid.uuid4()) #this generates a new random id and converts it to string
        result = es.get( index="busdb", doc_type="businfo",id = requestObject['bus_id'] )
        if result["found"] == "false":
               return jsonify({"response": "failure", "error": "No bus with given id"})
        result = result.get('_source')
        for i in result['seatsinfo']:
            if i['date'] == requestObject['date']:
                i['seats_left']= i['seats_left'] -  requestObject['booked_seats']
                if i['seats_left']<0:
                  return jsonify({"response":"failure","error":"Seats Full"})
        es.update(index="busdb", doc_type="businfo",id = requestObject['bus_id'],body = {"doc":result})
        es.index(index = "busdb",doc_type="bookinfo",id=idn,body=requestObject)  #create a new entry,it means add
        return jsonify({"response":"success","id":idn})

    except Exception as e:
        print str(e)
        return jsonify({"response":"failure","error":str(e)})

@mybus.route('/get_mytrips',methods=['GET'])
def get_mytrips():
    mail=request.args["mail_id"]
    #in case of post get_json()
    try:
        
        query ={
            "query": {
                "match_all": {}
            }
        }
        result = es.search(index = "busdb", doc_type="bookinfo",body=query)

        res = result["hits"]["hits"]
        filtered_result =[]
        for i in res :
            if i['_source']['user_id'] == mail : 
               filtered_result.append(i["_source"])
        return jsonify({"response":"success", "data":filtered_result})

    except Exception as e:
        print str(e)
        return jsonify({"response":"failure","error":str(e)})

# function to get time and convert to string
def getCurrentTime():
 tz = pytz.timezone('Asia/Calcutta')
 return datetime.now(tz).strftime('%H:%M:%S')

@mybus.route('/search_bus',methods=['GET'])
def search_bus():
    source=request.args["source"]
    destination=request.args["destination"]
    date=request.args["date"]

    try:

        time = datetime.strptime(getCurrentTime(), '%H:%M:%S')

        query ={

            "sort":[
                {"cost":{"order":"asc"}}
            ],
            "query": {
                "bool" :{
                "must":[
                    {"match":{"source":source}},
                    {"match": {"destination": destination}},
                    #{"term": {"Start_Time": time}}
                ],
                    "filter": [
                        {"range": {"start_Time":{"gte": time}}},
                      # {"match": {"Destination": destination}},
                        # {"term": {"Start_Time": time}}
                    ]
            },

        }
        }

        result = es.search(index = "busdb", doc_type="businfo",body=query)
        print result

        res = result["hits"]["hits"]
        filtered_result =[]

        for i in res :
            data = {}
            n=[]
            print i
            for j in i['_source']['datesinfo']:
                if j == date:
                    print i["_source"]
                    data['cost'] = i["_source"]['cost']
                    data['name'] = i["_source"]['name']
                    data['reached_Time'] = i["_source"]['reached_Time']
                    data['destination'] = i["_source"]['destination']
                    data['source'] = i["_source"]['source']
                    data['start_Time'] = i["_source"]['start_Time']
                    n = i["_source"]['seatsinfo']
                    for k in n :
                        if k['date'] == date:
                            data['seats_left'] = k['seats_left']


                    filtered_result.append(data)
                    break

        return jsonify({"response":"success", "data":filtered_result})
    except Exception as e:
        print str(e)
        return jsonify({"response":"failure","error":str(e)})

@mybus.route('/match_all',methods=['GET'])
def match_all():
    try:
        query ={
            "query": {
                "match_all": {}
            }
        }
        result = es.search(index = "busdb", doc_type="businfo",body=query) #  searches for query's response # returns a list

        res = result["hits"]["hits"]
        filtered_result =[]
        for i in res :
            filtered_result.append(i["_source"])

        return jsonify({"response":"success", "data":filtered_result})
    except Exception as e:
        print str(e)
        return jsonify({"response":"failure","error":str(e)})

@mybus.route('/match_all_bookings',methods=['GET'])
def match_all_bookings():
    try:
        query ={
            "query": {
                "match_all": {}
            }
        }
        result = es.search(index = "busdb", doc_type="bookinfo",body=query)

        res = result["hits"]["hits"]
        filtered_result =[]
        for i in res :
            filtered_result.append(i["_source"])

        return jsonify({"response":"success", "data":filtered_result})
    except Exception as e:
        print str(e)
        return jsonify({"response":"failure","error":str(e)})