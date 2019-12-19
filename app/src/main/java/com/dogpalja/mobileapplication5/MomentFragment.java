package com.dogpalja.mobileapplication5;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
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
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private static final int CROP_FROM_IMAGE = 2;
    private static final int REQUEST_FOR_CAMERA = 3;
    private static final int CAPTURE_IMAGE = 4;

    private Uri mImageCaptureUri;
    private CircleImageView iv_UserPhoto;
    private TextView tv_name;
    private TextView tv_sub_name;
    private int id_view;
    private String absoultePath;
    GridView gridview;
    Uri mImageUri;


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

        iv_UserPhoto = (CircleImageView) view.findViewById(R.id.profile_image);
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

                Intent intent = new Intent(getContext().getApplicationContext(), GridItemActivity.class);
                intent.putExtra("image_tags", storyTitleV.elementAt(i));
                //intent.putExtra("story_image", imageNameV.elementAt(i));
                intent.putExtra("image_time", picture_timeV.elementAt(i));
                startActivity(intent);
            }
        });

        initString();
        loadProfileImg();


        try {
            MakeList();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            loadResult(getPositionFile());      //저장된 텍스트 값 불러오기
        } catch (IOException e) {
            e.printStackTrace();
        }


        return view;
    }

    public void initString(){
        tv_name.setText("이름");
        tv_sub_name.setText("강아지 소개");
    }

//여기추가
    public void loadResult(File file) throws IOException {
        BufferedReader br=new BufferedReader(new FileReader(file));
        String line=null;

        int i = 0;
        while((line=br.readLine())!=null) {
            if(i == 0)
                tv_name.setText(line);
            else
                tv_sub_name.setText(line);
            i++;
        }
    }

    public void loadProfileImg(){
        File imgFile = getImageFile1();

        if(imgFile.exists()){
            Bitmap bitmap_tmp = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            Bitmap myBitmap = rotateBitmap(bitmap_tmp, 90);

            iv_UserPhoto.setImageBitmap(myBitmap);
        }
    }


    public void saveResult(){
        writeSettings(getPositionFile());
    }

    public void writeSettings(File file){
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String writeTmp = "";
        writeTmp += tv_name.getText() + "\n";
        writeTmp += tv_sub_name.getText() + "\n";

        try {
            // 기존 파일의 내용에 이어서 쓰려면 true를, 기존 내용을 없애고 새로 쓰려면 false를 지정한다.
            writer.write(writeTmp);
            writer.flush();

        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null) writer.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
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
            capturePhoto1();
//            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
////                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
////                        ((CircleImageView)getView().findViewById(R.id.profile_image)).setEnabled(false);
////                        ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
////                    }
//                    if(!checkPermission()){
//                        ((CircleImageView)getView().findViewById(R.id.profile_image)).setEnabled(false);
//                    };
//                    doTakePhotoAction();
//                }
//            };
//            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    doTakeAlbumAction();
//                }
//            };
//
//            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            };
//
//            new AlertDialog.Builder(getActivity(), R.style.CustomDialogStyle)
//                    .setTitle("업로드할 이미지 선택")
//                    .setPositiveButton("사진촬영", cameraListener)
//                    .setNeutralButton("앨범선택", albumListener)
//                    .setNegativeButton("취소", cancelListener)
//                    .show()
//                    .getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
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

    public boolean checkPermission(){
        int result;
        List<String> permissionList = new ArrayList<>();
        List<String> permissions = new ArrayList<>();
        permissions.add("Manifest.permission.CAMERA");
        permissions.add("Manifest.permission.WRITE_EXTERNAL_STORAGE");
        permissions.add("Manifest.permission.READ_EXTERNAL_STORAGE");
        for (String pm : permissions) {
            result = ContextCompat.checkSelfPermission(getContext(), pm);
            if (result != PackageManager.PERMISSION_GRANTED) { //사용자가 해당 권한을 가지고 있지 않을 경우 리스트에 해당 권한명 추가
                permissionList.add(pm);
            }
        }
        if (!permissionList.isEmpty()) { //리스트에 추가가 되면 권한이 없는것이므로 , 요청을 진행합니다.
            requestPermissions(permissionList.toArray(new String[permissionList.size()]), REQUEST_FOR_CAMERA);
        }
        return true;
    }


    //사진 촬영 함수
    private void doTakePhotoAction(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(photoFile != null){
            mImageCaptureUri = FileProvider.getUriForFile(
                    getContext(),
                    getContext().getApplicationContext().getPackageName() + ".fileprovider",
                    photoFile);
        }


        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }
    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        //currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private File getPositionFile(){
        File storageDir = getContext().getExternalFilesDir("ProfileTxt");
        File Position = new File(storageDir, "profile.txt");
        return Position;
    }


    //사진 촬영 함수
    private void capturePhoto1(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        if(cameraIntent.resolveActivity(getContext().getPackageManager()) != null) {
            File imageFile = null;
            imageFile = getImageFile1();

            if (imageFile != null){
                mImageUri = FileProvider.getUriForFile(getContext(), "com.dogpalja.mobileapplication5", imageFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,mImageUri);
            }

            startActivityForResult(cameraIntent, CAPTURE_IMAGE);

        }
    }

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK){

            try {
                Bitmap bitmap_tmp = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),mImageUri);
                Bitmap bitmap = rotateBitmap(bitmap_tmp, 90);

                if(bitmap != null) {
                    iv_UserPhoto.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getContext(),"이제 완료 버튼을 눌러주세요!",Toast.LENGTH_LONG).show();
        }
    }

    private File getImageFile1(){
        File storageDir = getContext().getExternalFilesDir("Profile");
        File imageFile = new File(storageDir, "profileImg" + ".jpg");

        return imageFile;
    }

    ///

    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
// DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
// ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
// TODO handle non-primary volumes
                }
// DownloadsProvider
                else if (isDownloadsDocument(uri)) {
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                    return getDataColumn(context, contentUri, null, null);
                }
// MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{
                            split[1]
                    };
                    return getDataColumn(context, contentUri, selection, selectionArgs);
                }
            }
// MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {
                return getDataColumn(context, uri, null, null);
            }
// File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
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
                saveResult();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });
    }




}
