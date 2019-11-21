package com.dogpalja.mobileapplication5;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class CameraFragment extends Fragment {

    Button upload_btn, capture_btn;
    ImageView captured_iv;
    Uri mImageUri;
    Bitmap bitmap;

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

        capture_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                capturePhoto();
            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                imageTitle();
            }
        });

    }

    public void capturePhoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String imageName = "image.jpg";
        mImageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), imageName));
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
        startActivityForResult(cameraIntent, CAPTURE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAPTURE_IMAGE){
            if(resultCode == RESULT_OK){
                if(mImageUri != null){

                    //convert uri to bitmap
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), mImageUri);

                        if(bitmap != null){ //null이 아닐 시 captured_iv 이미지 변경
                            captured_iv.setImageBitmap(bitmap);
                            Log.i("bitmap", bitmap.toString());
                        }

                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public void imageTitle(){

    }


}
