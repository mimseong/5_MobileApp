package com.dogpalja.mobileapplication5;

<<<<<<< HEAD

=======
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
>>>>>>> parent of 4c773e7... 날짜와 이미지 데이터베이스에 올리기 쉽도록 Map 부분 추가
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
<<<<<<< HEAD
import java.io.File;
import java.io.IOException;
=======

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
>>>>>>> parent of 4c773e7... 날짜와 이미지 데이터베이스에 올리기 쉽도록 Map 부분 추가

import static android.app.Activity.RESULT_OK;


public class CameraFragment extends Fragment {

    private ImageButton moment_camera_btn, essay_camera_btn;

<<<<<<< HEAD
    //사진촬영 후 완료버튼, 사진 미리보기 이미지
=======
    Button moment_ok_btn;
    ImageView moment_selected_photo;
>>>>>>> parent of 4c773e7... 날짜와 이미지 데이터베이스에 올리기 쉽도록 Map 부분 추가
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

<<<<<<< HEAD
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
=======
    private void storyAndImageTitle(){
        final EditText editText = new EditText(getContext());
        editText.setTextColor(Color.BLACK);
        editText.setHint("Set Title/Tags for story");
        editText.setHintTextColor(Color.GRAY);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Story Title");
        builder.setCancelable(false);
        builder.setView(editText);

        //ok를 눌렀을 때
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                if(OkToUpload) {
                    mStoryTitle = editText.getText().toString();
                    imageToString = convertImageToString();
                    uploadStory();
                }else{
                    Toast.makeText(getContext(),"Please take a photo first!",Toast.LENGTH_LONG).show();
                }



            }
        });

        //취소를 눌렀을 때
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();

    }

    private String convertImageToString(){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);   //100 : 원본
        byte[] imageByteArray = baos.toByteArray();
        String result =  Base64.encodeToString(imageByteArray,Base64.DEFAULT);

        return result;
    }

    private void uploadStory(){


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

                        if(bitmap != null){
                            moment_selected_photo.setImageBitmap(bitmap);
                            Log.i("bitmap", bitmap.toString());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


>>>>>>> parent of 4c773e7... 날짜와 이미지 데이터베이스에 올리기 쉽도록 Map 부분 추가
            }
        }
    }
}
