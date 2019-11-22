package com.dogpalja.mobileapplication5;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class SettingFragment extends Fragment {

    TextView btn_contact_us, btn_developer_info, btn_app_info, btn_cheer_up;

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

        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        btn_contact_us = (TextView) view.findViewById(R.id.btn_contact_us);
        btn_developer_info = (TextView) view.findViewById(R.id.btn_developer_info);
        btn_app_info = (TextView) view.findViewById(R.id.btn_app_info);
        btn_cheer_up = (TextView) view.findViewById(R.id.btn_cheer_up);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_contact_us.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext() , "btn_contact_us", Toast.LENGTH_LONG).show();
            }
        });

        btn_developer_info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext() , "btn_developer_info", Toast.LENGTH_LONG).show();
            }
        });

        btn_app_info.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext() , "btn_app_info", Toast.LENGTH_LONG).show();
            }
        });

        btn_cheer_up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext() , "btn_cheer_up", Toast.LENGTH_LONG).show();
            }
        });


    }
}
