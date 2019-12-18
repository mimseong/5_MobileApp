package com.dogpalja.mobileapplication5;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class MomentFragment extends Fragment implements View.OnClickListener{

    public MomentFragment(){

    }

    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_iMAGE = 2;

    private Uri mImageCaptureUri;
    private CircleImageView iv_UserPhoto;
    private TextView tv_name;
    private TextView tv_sub_name;
    private int id_view;
    String absoultePath;
    GridView gridview;


    //성민 추가 변수


    File[] files;
    Vector<String> imageNameV;
    Vector<String> storyTitleV;
    Vector<String> picture_timeV;

    File storageDir, imageFile;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_moment, container, false);

        iv_UserPhoto = view.findViewById(R.id.profile_image);
        iv_UserPhoto.setOnClickListener(this);

        tv_name = (TextView) view.findViewById(R.id.display_name);
        tv_name.setOnClickListener(this);

        tv_sub_name = (TextView) view.findViewById(R.id.description);
        tv_sub_name.setOnClickListener(this);

        storageDir = getContext().getExternalFilesDir("PictureDate");
        files = storageDir.listFiles();

        gridview = (GridView) view.findViewById(R.id.images_grid_layout);
        CustomAdaptor customAdaptor = new CustomAdaptor();

        imageNameV = new Vector<String>(files.length);
        picture_timeV = new Vector<String>(files.length);
        storyTitleV = new Vector<String>(files.length);

        gridview.setAdapter(customAdaptor);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Toast.makeText(getContext(), "클릭됨",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getContext().getApplicationContext(), GridItemActivity.class);
                intent.putExtra("image_tags", storyTitleV.elementAt(i));
                //intent.putExtra("story_image", imageNameV.elementAt(i));
                intent.putExtra("image_time", picture_timeV.elementAt(i));
                startActivity(intent);
            }
        });



        try {
            MakeList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return view;
    }

    private class CustomAdaptor extends BaseAdapter {
        @Override
        public int getCount() {
            return imageNameV.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.row_data, null);

            ImageView imageView = view.findViewById(R.id.images);
            imageView.setPadding(1,1,1,1);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setImageResource(images[i]);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;

            String stringImage = imageNameV.elementAt(i);
            String imageDataBytes = stringImage.substring(stringImage.indexOf(",")+1);
            InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
            Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);

            imageView.setImageBitmap(bitmap);

            return view;
        }
    }

    public void MakeList() throws IOException, ClassNotFoundException {


        for(int j = 0; j < files.length; j++) {


            FileInputStream fileStream = null;
            fileStream = new FileInputStream(storageDir.getAbsolutePath() + "/" + files[j].getName());

            ObjectInputStream objectInputStream = null;
            objectInputStream = new ObjectInputStream(fileStream);


            Object object = null;
            object = objectInputStream.readObject();

            objectInputStream.close();

            HashMap hashMap = (HashMap) object;

            Iterator<String> it = hashMap.keySet().iterator();

            while(it.hasNext()) {  // 맵키가 존재할경우

                String key = it.next();  // 맵키를 꺼냄
                String value = (String) hashMap.get(key);  // 키에 해당되는 객체 꺼냄

                if(key.equals("image_name")){
                    imageNameV.addElement(value);
                } else if (key.equals("title")) {
                    picture_timeV.addElement(value);
                } else if(key.equals("time")){
                    storyTitleV.addElement(value);
                }
            }

        }
    }

    @Override
    public void onClick(View v) {
        id_view = v.getId();
        if(id_view == R.id.profile_image) {
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakePhotoAction();
                }
            };
            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakeAlbumAction();
                }
            };

            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };

            new AlertDialog.Builder(getActivity(), R.style.CustomDialogStyle)
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("사진촬영", cameraListener)
                    .setNeutralButton("앨범선택", albumListener)
                    .setNegativeButton("취소", cancelListener)
                    .show()
                    .getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        else if(id_view == R.id.display_name) {
            final TextView name = (TextView) getView().findViewById(id_view);
            callFunction(name);

        }
        else if(id_view == R.id.description){
            final TextView sub_name = (TextView) getView().findViewById(id_view);
            callFunction(sub_name);
        }

    }



    /////성민 추가 부분

    //사진 촬영 함수
    private void doTakePhotoAction(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        if(cameraIntent.resolveActivity(getContext().getPackageManager()) != null) {
            imageFile = getImageFile();

            if (imageFile != null){

                mImageCaptureUri = FileProvider.getUriForFile(getContext(), "com.dogpalja.mobileapplication5", imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageCaptureUri);
            }

            startActivityForResult(cameraIntent, PICK_FROM_CAMERA);

        }
    }

    //파일 경로와 사진파일 이름 설정
    private File getImageFile(){
        //사진 찍은 시간 가져옴
        String imageName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_";
        //파일 경로 지정
        File Dir = getContext().getExternalFilesDir("ProfileImage");

        File imageFile = new File(Dir, imageName + ".jpg");

        Toast.makeText(getContext(),imageFile.getAbsolutePath(),Toast.LENGTH_LONG).show();

        return imageFile;
    }



    /////성민 추가 부분 끝


    /**
     * 앨범에서 이미지 가져오기
     */
    public void doTakeAlbumAction() // 앨범에서 이미지 가져오기
    {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(resultCode != RESULT_OK)
            return;

        switch(requestCode)
        {
            case PICK_FROM_ALBUM:
            {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행
                mImageCaptureUri = data.getData();
                Log.d("Profile",mImageCaptureUri.getPath().toString());
            }

            case PICK_FROM_CAMERA:
            {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),mImageCaptureUri);

                    if(bitmap != null) {
                        iv_UserPhoto.setImageBitmap(bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


//                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정.
//                // 이후에 이미지 크롭 어플리케이션을 호출.
//                Intent intent = new Intent("com.android.camera.action.CROP");
//                intent.setDataAndType(mImageCaptureUri, "image/*");
//                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//
//                // CROP할 이미지를 200*200 크기로 저장
//                intent.putExtra("outputX", 200); // CROP한 이미지의 x축 크기
//                intent.putExtra("outputY", 200); // CROP한 이미지의 y축 크기
//                intent.putExtra("aspectX", 1); // CROP 박스의 X축 비율
//                intent.putExtra("aspectY", 1); // CROP 박스의 Y축 비율
//                intent.putExtra("scale", true);
//                intent.putExtra("return-data", true);
//                startActivityForResult(intent, CROP_FROM_iMAGE); // CROP_FROM_CAMERA case문 이동
                break;
            }
            case CROP_FROM_iMAGE:
            {
//                // 크롭이 된 이후의 이미지를 넘겨 받음.
//                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
//                // 임시 파일 삭제.
//                if(resultCode != RESULT_OK) {
//                    return;
//                }
//
//                final Bundle extras = data.getExtras();
//
//                // CROP된 이미지를 저장하기 위한 FILE 경로
//                String filePath = getContext().getExternalFilesDir("ProfileImage")+".jpg";
//
//                if(extras != null)
//                {
//                    Bitmap photo = extras.getParcelable("data"); // CROP된 BITMAP
//                    iv_UserPhoto.setImageBitmap(photo); // 레이아웃의 이미지칸에 CROP된 BITMAP을 보여줌
//
//                    storeCropImage(photo, filePath); // CROP된 이미지를 외부저장소, 앨범에 저장한다.
//                    absoultePath = filePath;
//                    break;
//
//                }
//                // 임시 파일 삭제
//                File f = new File(mImageCaptureUri.getPath());
//                if(f.exists())
//                {
//                    f.delete();
//                }
            }
        }


    }



    //camera permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                ((CircleImageView)getView().findViewById(R.id.profile_image)).setEnabled(true);
            }
        }
    }

    /*
     * Bitmap을 저장하는 부분
     */
    private void storeCropImage(Bitmap bitmap, String filePath) {
        // Profile 폴더를 생성하여 이미지를 저장하는 방식이다.
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Profile";
        File directory_Profile = new File(dirPath);

        if(!directory_Profile.exists()) // Profile 디렉터리에 폴더가 없다면 (새로 이미지를 저장할 경우에 속한다.)
            directory_Profile.mkdir();

        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {

            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            // sendBroadcast를 통해 Crop된 사진을 앨범에 보이도록 갱신한다.
            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(copyFile)));

            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void callFunction(final TextView main_label) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(getActivity());

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //커스텀 다이얼로그 배경 투명 처리
        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.profile_name_dialog);

        //다이얼로그 바깥부분 터치 막음
        dlg.setCanceledOnTouchOutside(false);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText name = (EditText) dlg.findViewById(R.id.profile_name);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '확인' 버튼 클릭시 메인 액티비티에서 설정한 main_label에
                // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
                main_label.setText(name.getText().toString());
                //Toast.makeText(context, "\"" +  name.getText().toString() + "\" 을 입력하였습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "취소 했습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }


}
