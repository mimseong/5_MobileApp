package com.dogpalja.mobileapplication5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

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
        story_image.setImageResource(intent.getIntExtra("story_image", 0));
        image_time.setText(intent.getStringExtra("image_time"));
    }
}
