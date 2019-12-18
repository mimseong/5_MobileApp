package com.dogpalja.mobileapplication5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
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
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private static final String TAG = "MainActivity";

    String [] permission_list = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int result : grantResults){
            if(result == PackageManager.PERMISSION_DENIED){
                return;
            }
        }
        init();
    }

    public void init() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            requestPermissions(permission_list, 0);
        } else {
            init();
        }

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
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment_content, new MomentFragment()).commit();



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
                        fragment = new MomentFragment();

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


    /*
    //camera 2nd try

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
        } else {
            Log.i(TAG, "Requesting permission");
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
                Toast.makeText(this,"User interaction was cancelled.",Toast.LENGTH_LONG).show();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted. Kick off the process of building and connecting
                // GoogleApiClient.
                // perform your operation
                Toast.makeText(this,"카메라 접근 권한 승인되었습니다.",Toast.LENGTH_LONG).show();
            } else {
                // Permission denied.
                Toast.makeText(this,"카메라 접근이 제한될 수 있습니다.",Toast.LENGTH_LONG).show();
            }
        }
    }
    */

    //camera 3rd try



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
