package com.example.shamb.busreservation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by shamb on 3/14/2018.
 */

public class SearchResult extends android.support.v4.app.Fragment implements View.OnClickListener,Callback<Response>{

    RecyclerView recycler;
    Response res;
    List<Model> list;
    String dat;
    String date;

    String ed1,ed2;
    Context tsearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.search_result, container, false);

        Bundle bundle = getArguments();
        String date  = bundle.getString("date");
        String source  = bundle.getString("source");
        String destination  = bundle.getString("destination");


        tsearch = getContext();

        recycler = (RecyclerView) view.findViewById(R.id.recyclerview);

        String apiendp = "mybus/search_bus";

        Retrofit retrofit = new Retrofit.Builder()// R hits the link.. and converts the raw to iss type. widout R fit. no converter.
                .baseUrl("https://vast-reef-61137.herokuapp.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        API api = retrofit.create(API.class);
        Call<Response> call = api.getApires(apiendp,source,destination,date);
        call.enqueue(this); // call
            /*FragmentTransaction frag= MainActivity.frman.beginTransaction();
            SearchResult searchresult=new SearchResult();
            frag.remove(MainActivity.current_frag);
            frag.replace(R.id.pf1,searchresult);
            frag.show(searchresult);
            MainActivity.current_frag=searchresult;
            frag.commit();*/

        return view;

    }


    // https://vast-reef-61137.herokuapp.com/mybus/search_bus?source=NewDelhi&destination=Lucknow

    @Override
    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
        //    loading.dismiss();
        if(response==null) {
            Toast.makeText(tsearch,"Something went wrong",Toast.LENGTH_SHORT).show();
        } else {
            res = response.body();
            list = res.getData();
            dat = date;
            showData();
        }
    }

    public void showData() {
        recycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(tsearch);// Tells R View ki kaise aayenge.. R/V ko nhi pta.
        llm.setOrientation(LinearLayoutManager.VERTICAL);// Retard R view. wants Lin. Lay Man. and adapter.
        recycler.setLayoutManager(llm);
        recycler.setAdapter(new SearchAdapter(list,tsearch,dat));
    }
    @Override
    public void onFailure(Call<Response> call, Throwable t) {
        //    loading.dismiss();
        Toast.makeText(tsearch,"Something went wrong",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(View view) {



    }
}
