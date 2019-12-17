package com.dogpalja.mobileapplication5;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class CameraFragment extends Fragment {
    ImageView moment_selected_photo;

    Button moment_ok_btn, btn_capture_img;

    Bitmap bitmap;
    String mStoryTitle;
    String imageName;
    boolean OkToUpload;
    TextView moment_comment_btn, picture_day;
    final int CAPTURE_IMAGE = 1;

    String timeStamp, picture_time;
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

        moment_selected_photo = (ImageView) view.findViewById(R.id.moment_selected_photo);

        moment_ok_btn = (Button) view.findViewById(R.id.moment_ok_btn);
        btn_capture_img = (Button) view.findViewById(R.id.btn_capture_img);

        picture_day = (TextView) view.findViewById(R.id.picture_day);
        moment_comment_btn = (TextView) view.findViewById(R.id.moment_comment_txt);


        OkToUpload = false;

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

                    uploadStory();
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



    //이미지 크기 변경하는 메소드
    public Bitmap resizeBitmap(Bitmap source){

        int targetWidth = source.getWidth();
        int targetHight = targetWidth;

        Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHight, false);

        //원본 이미지 객체를 소멸시킨다
        if(result != source)
            source.recycle();

        return result;
    }

    //각도 변경하는 메소드
    public Bitmap rotateBitmap(Bitmap source, float degree){
        try{
            //원본 이미지의 가로, 세로 길이를 구한다.
            int targetWidth = source.getWidth();
            int targetHight = source.getHeight();

            Matrix matrix = new Matrix();
            matrix.postRotate(degree);
            Bitmap resizeBitmap = Bitmap.createBitmap(source, 0, 0, targetWidth, targetHight, matrix, true);
            source.recycle();

            return resizeBitmap;


        } catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    //각도 알아내는 메소드
    public float getDegree(){
        try{
            //이미지 파일에 저장된 정보를 가져온다
            ExifInterface exif = new ExifInterface(mImageUri.getPath());

            int degree = 0;

            //회전 각도 값, 없으면 -1
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            switch (ori){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
            return (float)degree;

        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }


    //사진 촬영 함수
    private void capturePhoto(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        if(cameraIntent.resolveActivity(getContext().getPackageManager()) != null) {
            File imageFile = null;
            imageFile = getImageFile();

            if (imageFile != null){
                mImageUri = FileProvider.getUriForFile(getContext(), "com.dogpalja.mobileapplication5", imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageUri);
            }

            startActivityForResult(cameraIntent, CAPTURE_IMAGE);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK){
                picture_time = dateOfImage().substring(0, 16);
                picture_day.setText(picture_time);

                try {
                    Bitmap bitmap_tmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),mImageUri);
                    float degree = getDegree();
                    Toast.makeText(getContext(),Float.toString(degree),Toast.LENGTH_LONG).show();
                    bitmap = rotateBitmap(resizeBitmap(bitmap_tmp), degree);

                    if(bitmap != null) {
                        OkToUpload = true;
                        moment_selected_photo.setImageBitmap(bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(),"이제 완료 버튼을 눌러주세요!",Toast.LENGTH_LONG).show();


        }

    }

    private File getImageFile(){
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageName = timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir("Images");

        File imageFile = new File(storageDir, imageName + ".jpg");

        return imageFile;
    }

    private File getTxtFile(){
        File storageDir = getContext().getExternalFilesDir("PictureDate");

        File txtFile = new File(storageDir, imageName + ".ser");

        return txtFile;
    }

    private void uploadStory(){
        if(!OkToUpload){
            Toast.makeText(getContext(),"업로드 할 이미지가 없습니다",Toast.LENGTH_LONG).show();
            return;
        }

        String imageToString = convertImageToString();
        Map<String, String> imageMap = new HashMap<>();

        imageMap.put("image_name", imageToString);
        imageMap.put("title", mStoryTitle);
        imageMap.put("time", picture_time);

        writeSettings(imageMap);


        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_fragment_content, new MomentFragment());
        ft.commit();

        OkToUpload = false;
    }

    public void writeSettings(Map<String, String> imageMap) {
        File fileName = getTxtFile();

        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(imageMap);
        } catch (IOException ex) {
            ex.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.flush();
                fos.close();
                if (out != null)
                    out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String convertImageToString(){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imageByteArray = baos.toByteArray();
        String result =  Base64.encodeToString(imageByteArray,Base64.DEFAULT);

        return result;


    }

    //사진 미리보기에서 시간 표시
    private String dateOfImage(){
        //기기에서 시간 받아옴
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.toString();
    }

}
