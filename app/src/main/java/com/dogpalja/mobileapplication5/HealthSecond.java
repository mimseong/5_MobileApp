package com.dogpalja.mobileapplication5;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class HealthSecond extends AppCompatActivity {

    SharedPreferences shared = getPreferences(Context.MODE_PRIVATE);
    EditText name, vaccin1_d, vaccin2_d, vaccin3_d, vaccin_d, heart_d, rabies_d, now_w, prev_w;
    //d는 날짜 입력.
    Button name_b, vaccin1_c, vaccin2_c, vaccin3_c, vaccin4_c, heart_c, rabies_c, id_c, insert_w, vomit_c, dia_c, nasal_c;
    //b는 버튼, c는 체크박스

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_second);
    }
}
