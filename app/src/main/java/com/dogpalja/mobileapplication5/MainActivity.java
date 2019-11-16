package com.dogpalja.mobileapplication5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mActionBarDrawerToggle;
    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //layout variables
        mDrawerLayout = findViewById(R.id.main_drawer_layout);
        mNavigationView = findViewById(R.id.main_nav_view);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener((mActionBarDrawerToggle));
        mActionBarDrawerToggle.syncState();

        setSupportActionBar((Toolbar)findViewById(R.id.my_toolbar));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_menu);

        //Default fragment to be display
        //changeFragmentDisplay(R.id.);


        //listener for navigation view
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return changeFragmentDisplay(item);
            }
        });
    }

    private boolean changeFragmentDisplay(MenuItem item){

        Fragment fragment = null;

        if(item.getItemId() == R.id.healthcheck){
            fragment = new HealthFragment();
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        }
        if(item.getItemId() == R.id.settings){
            fragment = new SettingFragment();
            return true;
        }
        else if(item.getItemId() == R.id.logout){
            Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_LONG).show();
            return true;
        }
        else {
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
        }

        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_fragment_content, fragment);
            ft.commit();
        }


        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mActionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        boolean isUserLoggedIn = SharedPrefrenceManager.getInstance(getApplicationContext()).isUserLoggeddIn();
//        if(!isUserLoggedIn){    //사용자가 로그인 상태가 아니라면 로그인 창으로 이동
//            startActivity(new Intent(MainActivity.this, LoginActivity.class));
//        } else{
//            //do nothing just stay here
//        }
//    }
}
