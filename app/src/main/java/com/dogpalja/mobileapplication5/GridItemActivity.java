package com.dogpalja.mobileapplication5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Base64;
import android.view.Display;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class GridItemActivity extends AppCompatActivity {

    SquareImageView story_image;
    TextView image_tags, image_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_item);

        image_tags = findViewById(R.id.image_tags);
        story_image = findViewById(R.id.story_image);
        image_time = findViewById(R.id.image_time);

        Intent intent = getIntent();
        image_tags.setText(intent.getStringExtra("image_tags"));
        image_time.setText(intent.getStringExtra("image_time"));




//          이미지 String으로 변경하고 사이즈 조절
//        String stringImage = intent.getStringExtra("story_image");
//        String imageDataBytes = stringImage.substring(stringImage.indexOf(",")+1);
//        InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        Bitmap bitmap = readImageWithSampling(stream,size.x,0);
//        story_image.setImageBitmap(bitmap);


        //이미지 String으로 변경해서 전송
        ///BitmapFactory.Options options = new BitmapFactory.Options();
        //            options.inSampleSize = 8;
        //
        //            String stringImage = imageNameV.elementAt(i);
        //            String imageDataBytes = stringImage.substring(stringImage.indexOf(",")+1);
        //            InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
        //            Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
        //
        //            imageView.setImageBitmap(bitmap);

    }

    //사이즈 조절하는 함수
    public Bitmap readImageWithSampling(InputStream stream, int targetWidth, int targetHeight) {
        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(stream, null, bmOptions);

        int photoWidth  = bmOptions.outWidth;
        int photoHeight = bmOptions.outHeight;

        if (targetHeight <= 0) {
            targetHeight = (targetWidth * photoHeight) / photoWidth;
        }

        // Determine how much to scale down the image
        int scaleFactor = 1;
        if (photoWidth > targetWidth) {
            scaleFactor = Math.min(photoWidth / targetWidth, photoHeight / targetHeight);
        }

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(stream, null, bmOptions);
    }
}
