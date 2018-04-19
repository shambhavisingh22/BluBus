package com.example.shamb.busreservation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by shamb on 3/15/2018.
 */

public class first_page extends android.support.v4.app.Fragment implements View.OnClickListener {

    CardView cd1,cd2,cd3,cd4;
    Context tfirst;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        tfirst = getContext();
        View view = inflater.inflate(R.layout.first_page, container, false);

      //  Bundle bundle = getArguments();
      // String name  = bundle.getString("name");

        cd1=(CardView) view.findViewById(R.id.cardsearch);
        cd1.setOnClickListener(this);
        cd2=(CardView) view.findViewById(R.id.cardmytrips);
        cd2.setOnClickListener(this);
        cd3=(CardView) view.findViewById(R.id.cardmusic);
        cd3.setOnClickListener(this);
        cd3=(CardView) view.findViewById(R.id.cardbookticket);
        cd3.setOnClickListener(this);
        return view;
    }
    @Override
    public void onClick(View view) {

        int id = view.getId();

        if(id==R.id.cardsearch){
            FragmentTransaction frag= MainActivity.frman.beginTransaction();
            Search search=new Search();
            frag.remove(MainActivity.current_frag);
            frag.replace(R.id.pf1,search);
            frag.show(search);
            MainActivity.current_frag=search;
            frag.commit();
        }

        if(id==R.id.cardmusic){

            Intent intent = new Intent("android.intent.action.Tunes");
            startActivity(intent);

        }
    }
}
