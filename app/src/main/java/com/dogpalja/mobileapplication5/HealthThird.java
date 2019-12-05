package com.dogpalja.mobileapplication5;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class HealthThird extends AppCompatActivity {

    SharedPreferences shared = getPreferences(Context.MODE_PRIVATE);
    EditText name, vaccin1_d, vaccin2_d, vaccin3_d, vaccin4_d, heart_d, rabies_d, now_w, prev_w;
    //d는 날짜 입력.
    Button name_b, vaccin1_c, vaccin2_c, vaccin3_c, vaccin4_c, heart_c, rabies_c, id_c, insert_w, vomit_c, dia_c, nasal_c;
    //b는 버튼, c는 체크박스

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_third);

        name = (EditText) findViewById(R.id.dog);
        vaccin1_d = (EditText) findViewById(R.id.vaccin1_date);
        vaccin2_d = (EditText) findViewById(R.id.vaccin2_date);
        vaccin3_d = (EditText) findViewById(R.id.vaccin3_date);
        vaccin4_d = (EditText) findViewById(R.id.vaccin4_date);
        heart_d = (EditText) findViewById(R.id.heart_date);
        rabies_d = (EditText) findViewById(R.id.rabies_date);
        now_w = (EditText) findViewById(R.id.now_edit);
        prev_w = (EditText) findViewById(R.id.before_edit);

        name_b = (Button) findViewById(R.id.insert_n);
        vaccin1_c = (Button) findViewById(R.id.vaccin1_check);
        vaccin2_c = (Button) findViewById(R.id.vaccin2_check);
        vaccin3_c = (Button) findViewById(R.id.vaccin3_check);
        vaccin4_c = (Button) findViewById(R.id.vaccin4_check);
        heart_c = (Button) findViewById(R.id.heart_check);
        rabies_c = (Button) findViewById(R.id.rabies_check);
        id_c = (Button) findViewById(R.id.idntify_check);
        insert_w = (Button) findViewById(R.id.insert_w);
        vomit_c = (Button) findViewById(R.id.vomit_check);
        dia_c = (Button) findViewById(R.id.diarrhea_check);
        nasal_c = (Button) findViewById(R.id.nasal_check);
    }
}
