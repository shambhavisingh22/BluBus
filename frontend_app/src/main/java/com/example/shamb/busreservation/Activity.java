package com.example.shamb.busreservation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class Activity extends AppCompatActivity implements View.OnClickListener {

    CardView login, signup;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        SharedPreferences pref = getSharedPreferences(getResources().getString(R.string.app_name),MODE_PRIVATE);
        Boolean isLogin = pref.getBoolean("islogin",false);

        if(isLogin) {
            Intent intent = new Intent("android.intent.action.MainActivity");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        login = (CardView) findViewById(R.id.cardlogin);
        login.setOnClickListener(this);
        signup = (CardView) findViewById(R.id.signup1);
        signup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.signup1) {

            Intent intent = new Intent("android.intent.action.Signup");
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        if (id == R.id.cardlogin) {

            Intent intent = new Intent("android.intent.action.Login");
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}