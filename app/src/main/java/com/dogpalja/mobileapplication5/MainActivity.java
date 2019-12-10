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
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mActionBarDrawerToggle;
    NavigationView mNavigationView;
    BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //layout variables
        mDrawerLayout = findViewById(R.id.main_drawer_layout);
        mNavigationView = findViewById(R.id.main_nav_view);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener((mActionBarDrawerToggle));
        mActionBarDrawerToggle.syncState();

        setSupportActionBar((Toolbar)findViewById(R.id.my_toolbar));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_category);

        //Default fragment to be display
        //changeFragmentDisplay(R.id.moment);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_content, new SignUpPhotoFragment()).commit();


        //listener for navigation view
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                changeFragmentDisplay(item);
                return true;
            }
        });

        mBottomNavigationView.setOnNavigationItemSelectedListener(navListner);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment = null;

                    ///in bottom navigation view
                    if(item.getItemId() == R.id.moment){
                        fragment = new SignUpPhotoFragment();

                    }
                    else if(item.getItemId() == R.id.camera){
                        fragment = new CameraFragment();

                    }
                    else if(item.getItemId() == R.id.map){
                        fragment = new MapFragment();

                    }
                    else {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }

                    if(fragment != null)
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_content, fragment).commit();

                    return true;
                }
            };


    private void changeFragmentDisplay(MenuItem item){
        Fragment fragment = null;

        ///in toolbar
        if(item.getItemId() == R.id.healthcheck){
            fragment = new HealthFragment();
        }
        else if(item.getItemId() == R.id.settings){
            fragment = new SettingFragment();
        }
        else if(item.getItemId() == R.id.logout){
            Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_LONG).show();
            SharedPrefrenceManager sharedPrefrenceManager = SharedPrefrenceManager.getInstance(getApplicationContext());
            sharedPrefrenceManager.logUserOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        else {
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
        }

        //hide naviagtion drawer
        mDrawerLayout.closeDrawer(GravityCompat.START);

        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_fragment_content, fragment);
            ft.commit();
        }
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
