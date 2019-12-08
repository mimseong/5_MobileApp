package com.dogpalja.mobileapplication5;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dogpalja.mobileapplication5.R;
import com.google.android.material.navigation.NavigationView;


public class SettingFragment extends Fragment {

    NavigationView mNavigationView;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_setting, container, false);
        mNavigationView = view.findViewById(R.id.setting_nav_view);
        mNavigationView.setNavigationItemSelectedListener(navListner);

        return view;
    }
    private NavigationView.OnNavigationItemSelectedListener navListner =
        new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                ///in bottom navigation view
                if(item.getItemId() == R.id.contact_us){
                    fragment = new ContactFragment();

                }
                else if(item.getItemId() == R.id.developer_info){
                    fragment = new DeveloperFragment();

                }
                else if(item.getItemId() == R.id.app_info){
                    fragment = new AppinfoFragment();

                }
                else if(item.getItemId() == R.id.cheer_up){
                    fragment = new CheerUpFragment();

                }
                else {
                    Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
                }

                if(fragment != null)
                    getFragmentManager().beginTransaction().replace(R.id.main_fragment_content, fragment).commit();

                return true;
            }
        };
}
