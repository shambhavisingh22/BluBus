package com.example.shamb.busreservation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by shamb on 4/16/2018.
 */

public class BookForm extends AppCompatActivity implements View.OnClickListener {
    PostModel model;

    EditText phone,seat,email1,name;
    Button submit;
    String email,id,date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_form);

        SharedPreferences shrd  = getSharedPreferences("Bus Reservation", Context.MODE_PRIVATE);
        email = shrd.getString("email",null);


        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        id  = bd.getString("ID");
        date = bd.getString("date");

        phone=(EditText)findViewById(R.id.phone);
        email1=(EditText)findViewById(R.id.email);
        email1.setText(email);
        name=(EditText)findViewById(R.id.name);
        seat=(EditText)findViewById(R.id.seat);
        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(this);



    }



        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.book) {



                model.setUser_id(email);
                model.setBus_id(id);
                model.setName(name.getText().toString());
                model.setDate(date);
                model.setBooked_seats(Integer.parseInt(seat.getText().toString()));

                String apiEndPoint = "add_bookinfo";

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://vast-reef-61137.herokuapp.com/mybus/")
                        .addConverterFactory(JacksonConverterFactory.create())
                        .build();

                AddAPI api = retrofit.create(AddAPI.class);
                Call<PostAPIResponse> call = api.getData(apiEndPoint,model);
                call.enqueue(new Callback<PostAPIResponse>() {


                    @Override
                    public void onResponse(Call<PostAPIResponse> call, retrofit2.Response<PostAPIResponse> response) {
                        PostAPIResponse response1 = response.body();
                        if(response1!=null && response1.getResponse().equals("success")) {
                            Toast.makeText(getApplicationContext(),"Data successfully added",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PostAPIResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }

