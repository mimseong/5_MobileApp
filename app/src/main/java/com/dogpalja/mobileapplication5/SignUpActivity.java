package com.dogpalja.mobileapplication5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

public class SignUpActivity extends AppCompatActivity {

    LinearLayout mLoginContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mLoginContainer = (LinearLayout) findViewById(R.id.login_container);

    }
}
