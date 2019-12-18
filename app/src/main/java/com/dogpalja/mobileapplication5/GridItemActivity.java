package com.dogpalja.mobileapplication5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GridItemActivity extends AppCompatActivity {

    TextView image_tags, image_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_item);

        image_tags = findViewById(R.id.image_tags);
        image_time = findViewById(R.id.image_time);

        Intent intent = getIntent();
        image_tags.setText(intent.getStringExtra("image_time")); //실수로 값 반대로 저장함
        image_time.setText(intent.getStringExtra("image_tags"));

    }
}
