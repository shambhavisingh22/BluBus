package com.example.shamb.busreservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

/**
 * Created by shamb on 3/12/2018.
 */

public class Login extends AppCompatActivity implements View.OnClickListener{

   EditText name,pass;
   CardView log;
    TextView text1,text2;
    private FirebaseAuth mAuth;
    private ProgressBar bar;
    private GoogleSignInOptions geo;
    private GoogleSignInClient client;
    private static final int GOOGLE_SIGN_IN = 3001;
    private SignInButton googleSignInButton;
    private Button signout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        signout=(Button)findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                out();
            }
        });
        googleSignInButton = findViewById(R.id.googlesignin);
        googleSignInButton.setSize(SignInButton.SIZE_STANDARD);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singIn();
            }
        });

        geo = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        client = GoogleSignIn.getClient(this,geo);

        bar = findViewById(R.id.progress);
        name=(EditText) findViewById(R.id.username);
        name.setOnClickListener(this);
        pass=(EditText) findViewById(R.id.password);
        pass.setOnClickListener(this);
        log=(CardView)findViewById(R.id.cardlogin);
        log.setOnClickListener(this);
        text1=(TextView) findViewById(R.id.info1);
        text1.setOnClickListener(this);
        text2=(TextView) findViewById(R.id.info2);
        text2.setOnClickListener(this);




    }

    private void singIn() {
        Intent intent  = client.getSignInIntent();
        startActivityForResult(intent,GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(getApplicationContext(),"Successfully logged In",Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),account.getDisplayName(),Toast.LENGTH_SHORT).show();

            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void out() {
        client.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Logged Out",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            Toast.makeText(getApplicationContext(), "Already Logged In", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), account.getDisplayName(), Toast.LENGTH_SHORT).show();
        }
        super.onStart();
    }

    @Override
    public void onClick(View view) {

        int id=view.getId();
        if(id == R.id.cardlogin) {

            if (name != null && !name.getText().toString().equals("")) {
            }
            else {
                Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pass != null && !pass.getText().toString().equals("")) {
            } else {
                Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
                return;
            }
            bar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(name.getText().toString(), pass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getBaseContext(), "Register first", Toast.LENGTH_SHORT).show();
                                bar.setVisibility(View.GONE);
                            } else {
                                FirebaseUser user = mAuth.getCurrentUser();
                                String name = user.getEmail();
                                if(!user.isEmailVerified()) {
                                    Toast.makeText(getBaseContext(), "Verify Your Email First", Toast.LENGTH_SHORT).show();
                                    bar.setVisibility(View.GONE);
                                    return;
                                }

                                Toast.makeText(getBaseContext(),"Successfully logged In" ,Toast.LENGTH_SHORT).show();
                                SharedPreferences pref = getSharedPreferences(getResources().getString(R.string.app_name),MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putBoolean("islogin",true);
                                editor.putString("email",name);
                                editor.apply();
                                // Bundle bundle  = new Bundle();
                                //  bundle.putString("email",name.getText().toString());

                                //SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.app_name),MODE_PRIVATE).edit();
                                //editor.putBoolean("isLogin",true); // to keep login
                                //editor.apply();
                                Intent intent = new Intent("android.intent.action.MainActivity");
                                startActivity(intent);
                                bar.setVisibility(View.GONE);
                            }

                        }
                    });

        }

        if(id==R.id.info1)
        {
            Intent intent = new Intent("android.intent.action.Signup");
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        }

    }
}



