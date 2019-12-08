package com.dogpalja.mobileapplication5;


import android.app.ProgressDialog;
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
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dogpalja.mobileapplication5.SquareImageView;

import static android.app.Activity.RESULT_OK;


public class CameraFragment extends Fragment {

    SquareImageView moment_selected_photo;
    Button moment_ok_btn, btn_capture_img;

    Bitmap bitmap;
    String mStoryTitle, imageToString, mProfileImage;
    boolean OkToUpload;
    TextView moment_comment_btn;
    final int CAPTURE_IMAGE = 1;


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

        moment_selected_photo = (SquareImageView) view.findViewById(R.id.moment_selected_photo);
        moment_ok_btn = (Button) view.findViewById(R.id.moment_ok_btn);
        btn_capture_img = (Button) view.findViewById(R.id.btn_capture_img);

        moment_comment_btn = (TextView) view.findViewById(R.id.moment_comment_txt);

        OkToUpload = false;

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getProfileImage();

        final TextView moment_comment_tv = (TextView) getView().findViewById(R.id.moment_comment_txt);
        btn_capture_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturePhoto();
            }
        });

        moment_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(OkToUpload) {
                    mStoryTitle = moment_comment_tv.getText().toString();

                    //imageToString = convertImageToString();
                    Toast.makeText(getContext(),mStoryTitle ,Toast.LENGTH_LONG).show();
                    //uploadStory();
                }else{
                    Toast.makeText(getContext(),"사진 촬영 후 업로드 가능합니다",Toast.LENGTH_LONG).show();
                }
            }
        });

        moment_comment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MomentCommentInput commentInput = new MomentCommentInput(getContext());

                commentInput.callFunction(moment_comment_tv);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAPTURE_IMAGE){
            if(resultCode == RESULT_OK){

                OkToUpload = true;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),mImageUri);
                    if(bitmap != null) {
                        moment_selected_photo.setImageBitmap(bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(),"이제 업로드 버튼을 눌러주세요!",Toast.LENGTH_LONG).show();


            }
        }

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

    private void getProfileImage(){
        User user = SharedPrefrenceManager.getInstance(getContext()).getUserData();
        int user_id = user.getId();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLS.get_user_data+user_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")){

                                JSONObject jsonObjectUser =  jsonObject.getJSONObject("user");

                                mProfileImage =  jsonObjectUser.getString("image");



                            }else{

                                Toast.makeText(getContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            }


                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                }


        );

        VolleyHandler.getInstance(getContext().getApplicationContext()).addRequestToQueue(stringRequest);
    }



    private String convertImageToString(){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);   //100 : 원본
        byte[] imageByteArray = baos.toByteArray();
        String result =  Base64.encodeToString(imageByteArray,Base64.DEFAULT);

        return result;
    }

    private void uploadStory(){

        if(!OkToUpload){
            Toast.makeText(getContext(),"업로드 할 이미지가 없습니다",Toast.LENGTH_LONG).show();
            return;
        }

        final String dateOfImage = dateOfImage();
        final String currentTime = currentReadableTime();

        User user = SharedPrefrenceManager.getInstance(getContext()).getUserData();
        final String username = user.getUsername();
        final int user_id = user.getId();
        final String profile_image = mProfileImage;


        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());

        mProgressDialog.setTitle("Log In");
        mProgressDialog.setMessage("잠시 기다려주세요..");
        mProgressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.upload_story_image,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")){
                                mProgressDialog.dismiss();

                                JSONObject jsonObjectUser = jsonObject.getJSONObject(("image"));

                                Toast.makeText(getContext(), jsonObject.getString("업로드가 완료되었습니다."), Toast.LENGTH_LONG).show();

                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.main_fragment_content, new MomentFragment());
                                ft.commit();

                            } else {
                                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }

                        } catch(JSONException e){
                            e.printStackTrace();

                        }

                    }
                },
                //에러났을 떄
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                    }
                }
        ){
            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> imageMap = new HashMap<>();
                imageMap.put("image_name", dateOfImage);
                imageMap.put("image_encoded", imageToString);
                imageMap.put("title", mStoryTitle);

                imageMap.put("time", currentTime);
                imageMap.put("username", username);
                imageMap.put("user_id", String.valueOf(user_id));
                imageMap.put("profile_image", profile_image);

                return imageMap;
            }
        };  //end of string Request

        VolleyHandler.getInstance(getContext().getApplicationContext()).addRequestToQueue(stringRequest);
        OkToUpload = false;
    }

    private String dateOfImage(){
        //기기에서 시간 받아옴
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.toString();
    }

    private String currentReadableTime(){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //사람이 읽기 쉬운 형태로 반환
        Date date = new Date(timestamp.getTime());
        return date.toString();
    }
}
