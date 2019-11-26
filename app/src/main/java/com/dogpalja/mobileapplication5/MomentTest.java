package com.dogpalja.mobileapplication5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.support.v4.media.session.PlaybackStateCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MomentTest extends AppCompatActivity {
    ImageButton intent_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_camera);

        //moment_input으로 넘어가는 버튼
        intent_btn = findViewById(R.id.moment_camera_btn);

        intent_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //인텐트 선언 -> 현재 액티비티, 넘어갈 액티비티
                Intent intent = new Intent(MomentTest.this, MomentInput.class);
                //인텐트 실행
                startActivity(intent);
            }
        });


    }
}
