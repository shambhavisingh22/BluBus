package com.example.shamb.busreservation;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by shamb on 3/14/2018.
 */

public class Search extends android.support.v4.app.Fragment implements View.OnClickListener{


    CardView search;
    Context context;

    EditText ed1,ed2;
    ImageView image;

    Context tsearch;
    DatePicker dp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.search_page, container, false);

        //Bundle bundle = getArguments();
      //  String name  = bundle.getString("name");

        ed1= (EditText) view.findViewById(R.id.source);
        ed1.setOnClickListener(this);
        ed2= (EditText) view.findViewById(R.id.destination);
        ed2.setOnClickListener(this);
        image=(ImageView) view.findViewById(R.id.interchange);
        image.setOnClickListener(this);
        dp=(DatePicker)view.findViewById(R.id.dateofjourney);
        dp.setSpinnersShown(false);
        search=(CardView) view.findViewById(R.id.cardsearch);
        search.setOnClickListener(this);

        tsearch = getContext();



        return view;


    }

    // https://vast-reef-61137.herokuapp.com/mybus/search_bus?source=NewDelhi&destination=Lucknow

    @Override
    public void onClick(View view) {
        int id=view.getId();



        if(id==R.id.cardsearch){

            if (ed1 != null && !ed1.getText().toString().equals("")) {
            }
            else {
                Toast.makeText(tsearch, "Enter source", Toast.LENGTH_SHORT).show();
                return;
            }
            if (ed2 != null && !ed2.getText().toString().equals("")) {
            } else {
                Toast.makeText(tsearch, "Enter destination", Toast.LENGTH_SHORT).show();
                return;
            }

            // get the values for day of month , month and year from a date picker
            String day = ""+dp.getDayOfMonth();
            String month = "" + (dp.getMonth() + 1);
            String year = "" + dp.getYear();
 /*           year = "" + year.charAt(year.length()-4) + year.charAt(year.length()-3) + year.charAt(year.length()-2) + year.charAt(year.length()-1);
            month = "" + month.charAt(month.length()-2) + month.charAt(month.length()-1);
            if((""+month.charAt(month.length()-2)).equals(" "))
            {

                month = "" + month.charAt(month.length()-1);

            }
            day = "" + day.charAt(day.length()-2) + day.charAt(day.length()-1);
            if((""+day.charAt(day.length()-2)).equals(" "))
            {

                day = "" + day.charAt(day.length()-1);

            }
*/
            String date= (day+"-"+month+"-"+year);
            String s= ed1.getText().toString();
            String d=ed2.getText().toString();


            FragmentTransaction frag= MainActivity.frman.beginTransaction();
            SearchResult search=new SearchResult();
            Bundle bundle = new Bundle(); // to send ID aage.
            bundle.putString("date",date);
            bundle.putString("source",s);
            bundle.putString("destination",d);
            search.setArguments(bundle);
            frag.remove(MainActivity.current_frag);
            frag.replace(R.id.pf1,search);
            frag.show(search);
            MainActivity.current_frag=search;
            frag.commit();
        }


    }
}
