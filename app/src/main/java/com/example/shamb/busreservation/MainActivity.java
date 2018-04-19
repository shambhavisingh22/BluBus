package com.example.shamb.busreservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.support.design.widget.Snackbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static FragmentManager frman;
    FragmentTransaction frtran;
    public static Fragment current_frag;
    first_page frgobj;
    LinearLayout lin1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_bar);

        frgobj = new first_page();

        lin1 = (LinearLayout) findViewById(R.id.pf1);
        frman = getSupportFragmentManager();
        frtran = frman.beginTransaction();
        if(current_frag == null)
        {
            frtran.replace(R.id.pf1,frgobj);
            frtran.show(frgobj);
            current_frag = frgobj;
        }
        else
        {
            frtran.remove(current_frag);
            frtran.replace(R.id.pf1,frgobj);
            frtran.show(frgobj);
            current_frag = frgobj;
        }
        frtran.commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_close,R.string.navigation_drawer_open);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout:
                startActivity(new Intent(this, Activity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {

        if(current_frag!=frgobj)
        {
            FragmentTransaction frtranback = frman.beginTransaction();
            frtranback.remove(current_frag);// removing old fragment

            frtranback.replace(R.id.pf1,frgobj);
            frtranback.show(frgobj);
            current_frag = frgobj;
            frtranback.commit();
        }

        else super.onBackPressed();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home)
        {
            Toast.makeText(this,"Coming soon  " , Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.my_trips)
        {
            Toast.makeText(this,"Coming soon  " , Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.music)
        {
            Toast.makeText(this,"Coming soon  " , Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.share)
        {
            Toast.makeText(this,"Coming soon  " , Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.settings)
        {
            Toast.makeText(this,"Coming soon  " , Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.help)
        {
            Toast.makeText(this,"Coming soon  " , Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.about)
        {
            Toast.makeText(this,"Coming soon  " , Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.feedback)
        {
            Toast.makeText(this,"Coming soon  " , Toast.LENGTH_SHORT).show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);   //to close
        return true;
    }

}
