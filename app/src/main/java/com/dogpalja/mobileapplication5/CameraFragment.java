package com.dogpalja.mobileapplication5;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.content.pm.PackageManager;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class CameraFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    Button upload_btn, capture_btn;
    ImageView captured_iv;

    final int CAPTURE_IMAGE = 1;

    public CameraFragment() {
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

        upload_btn = (Button) view.findViewById(R.id.upload_btn);
        capture_btn = (Button) view.findViewById(R.id.capture_btn);
        captured_iv = (ImageView) view.findViewById(R.id.captured_iv);

        return view;
    }


    //Call after view is created
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//        }
//    }



}
