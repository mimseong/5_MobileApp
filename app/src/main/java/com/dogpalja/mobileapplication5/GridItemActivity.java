package com.dogpalja.mobileapplication5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
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

        String stringImage = intent.getStringExtra("story_image");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;

        String imageDataBytes = stringImage.substring(stringImage.indexOf(",")+1);
        InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
        Bitmap bitmap = BitmapFactory.decodeStream(stream);

        story_image.setImageBitmap(bitmap);

    }
}
