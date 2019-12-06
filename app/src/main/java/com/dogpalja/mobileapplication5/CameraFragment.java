package com.dogpalja.mobileapplication5;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;


public class CameraFragment extends Fragment {

    //fragment_camera.xml의 모멘트 촬영, 에세이 촬영 버튼
    ImageButton moment_camera_btn, essay_camera_btn;

    //사진촬영 후 완료버튼, 사진 미리보기 이미지
    final int CAPTURE_IMAGE = 1 ,GALLARY_PICK = 2;
    Bitmap bitmap;

    Uri mImageUri;


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

        moment_camera_btn = (ImageButton) view.findViewById(R.id.moment_camera_btn);
        essay_camera_btn = (ImageButton) view.findViewById(R.id.essay_camera_btn);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        moment_camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePhoto();
            }
        });

        essay_camera_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePhoto();
            }
        });


    }

    private void capturePhoto(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String imageName = "image.jpg";
        mImageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),imageName));
        //File file = new File(Environment.getExternalStorageDirectory(),imageName);
        //mImageUri = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".provider", file);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageUri);
        startActivityForResult(cameraIntent, CAPTURE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAPTURE_IMAGE){
            if(resultCode == RESULT_OK){
                bitmap =  (Bitmap) data.getExtras().get("data");
                Toast.makeText(getContext(),"Now Click on Upload Button to Upload image",Toast.LENGTH_LONG).show();
                if(bitmap != null) {
                    ImageResultFragment imageResultFragment = new ImageResultFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("image",bitmap);  // bitmap is the captured image
                    imageResultFragment.setArguments(bundle);


                    // Go to the new fragment "ShowImageResultFragment"
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.main_fragment_content, imageResultFragment);
                    ft.commit();
                }
            }
        }
    }
}
