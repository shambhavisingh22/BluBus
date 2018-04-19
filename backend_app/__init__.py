from flask_sqlalchemy import SQLAlchemy
from flask_migrate import Migrate
from flask_cors import CORS

from flask import Flask
import lib.log as log
import logging
from config import config, APP_NAME
from elasticsearch import *
import re
Logger = logging.getLogger(APP_NAME)

logging.basicConfig(level=logging.INFO)

bonsai = "https://jli29tfugb:hiko7cvis3@jasmine-1900495.us-east-1.bonsaisearch.net"
auth = re.search('https\:\/\/(.*)\@', bonsai).group(1).split(':')
host = bonsai.replace('https://%s:%s@' % (auth[0], auth[1]), '')

# Connect to cluster over SSL using auth for best security:
es_header = [{
 'host': host,
 'port': 443,
 'use_ssl': True,
 'http_auth': (auth[0],auth[1])
}]

# Instantiate the new Elasticsearch connection:
es = Elasticsearch(es_header) # ORM wrapper (by default local host)
db = SQLAlchemy()


def initialize_db(app):
    db.init_app(app)

    migrate = Migrate(app, db)


def create_app(config_name): # initialize app
    app = Flask(__name__)
    CORS(app,resources={r"":{"origins":""}})
    app.config.from_object(config[config_name])
    config[config_name].init_app(app)
    log.setup_logging(config[config_name])

    initialize_db(app)

    from app.BusApp.views import mybus as my_router
    app.register_blueprint(my_router, url_prefix='/mybus') # initialize views # giving hash


    return app
