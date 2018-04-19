package com.example.shamb.busreservation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by shamb on 3/12/2018.
 */

public class Signup extends AppCompatActivity implements View.OnClickListener{
    EditText phone,name,email,pass;
    CardView sign;
    TextView text2;
    private FirebaseAuth mAuth;
    DatabaseReference ref;
    SignupModel model;
    FirebaseDatabase database;
    private ProgressBar bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        bar = findViewById(R.id.progress);
        model = new SignupModel();
        database  = FirebaseDatabase.getInstance();

        ref = database.getReference("Login_users");

        phone=(EditText) findViewById(R.id.phone);
        phone.setOnClickListener(this);
        name=(EditText) findViewById(R.id.name);
        name.setOnClickListener(this);
        email=(EditText) findViewById(R.id.email);
        email.setOnClickListener(this);
        pass=(EditText) findViewById(R.id.setpassword);
        pass.setOnClickListener(this);
        sign=(CardView)findViewById(R.id.cardsignup);
        sign.setOnClickListener(this);
        text2=(TextView) findViewById(R.id.info1);
        text2.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();

       if(id==R.id.cardsignup)
       {
           if (phone != null && !phone.getText().toString().equals("")) {
           }
           else {
               Toast.makeText(this, "Enter Phone", Toast.LENGTH_SHORT).show();
               return;
           }
           if (name != null && !name.getText().toString().equals("")) {
           }
           else {
               Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
               return;
           }
           if (email != null && !email.getText().toString().equals("")) {
           }
           else  {
               Toast.makeText(this, "Enter email Id", Toast.LENGTH_SHORT).show();
               return;
           }
           if (pass != null && !pass.getText().toString().equals("")) {
           } else {
               Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
               return;
           }
           bar.setVisibility(View.VISIBLE);
           mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                   .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {
                               model.setPhone(Long.parseLong(phone.getText().toString()));
                               model.setEmail(email.getText().toString());
                               model.setName(name.getText().toString());
                               model.setPassword(pass.getText().toString());
                               ref.push().setValue(model);
                               Toast.makeText(getBaseContext(), "SignUp successfully",
                                       Toast.LENGTH_SHORT).show();
                               // next page ka intent laga dena.
                               //  Bundle bundle  = new Bundle();
                               //  bundle.putString("email",email.getText().toString());
                               FirebaseUser user = mAuth.getCurrentUser();



                               user.sendEmailVerification()
                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               if (task.isSuccessful()) {
                                                   Toast.makeText(getBaseContext(), "Email Sent",
                                                           Toast.LENGTH_SHORT).show();
                                               }
                                           }
                                       });
                               SharedPreferences pref = getSharedPreferences(getResources().getString(R.string.app_name),MODE_PRIVATE);
                               SharedPreferences.Editor editor = pref.edit();
                               editor.putBoolean("islogin",true);
                               editor.apply();
                               // Intent intent = new Intent("android.intent.action.MAIN");
                               // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK); // In case of shared pref. Use this, back button handle. aage vali activity chlegi to prev vali band.
                               //intent.putExtras(bundle);
                               //startActivity(intent);
                               bar.setVisibility(View.GONE);
                               Intent intent = new Intent("android.intent.action.Login");
                               startActivity(intent);
                           } else {
                               Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });

       }
        if(id==R.id.info1)
        {
            Intent intent = new Intent("android.intent.action.Login");
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }
}








