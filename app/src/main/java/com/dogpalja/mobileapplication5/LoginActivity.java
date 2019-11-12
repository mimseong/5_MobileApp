package com.dogpalja.mobileapplication5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    LinearLayout mLoginContainer;
    EditText username_et, password_et;
    ProgressBar mProgressBar;
    Button login_btn;
    TextView sign_up_btn, forgot_pass_btn;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLoginContainer = (LinearLayout) findViewById(R.id.login_container);
        username_et = (EditText) findViewById(R.id.user_name);
        password_et = (EditText) findViewById(R.id.user_password);

        mProgressBar = new ProgressBar(this);

        sign_up_btn = (TextView) findViewById(R.id.sign_up_btn);
        forgot_pass_btn = (TextView) findViewById(R.id.forgot_pass_btn);

        login_btn = (Button) findViewById(R.id.login_btn);

        mProgressDialog = new ProgressDialog(this);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //사용자가 로그인 버튼을 누르면 로그인 함수로 이동
                logIn();
            }
        });

        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이동할꺼라 Activity 종료
                finish();
                //LoginActivity.this 여기서 SignUpActivity.class 로 이동한다.
                Intent signUpIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

        forgot_pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void logIn(){

        mProgressDialog.setTitle("Log In");
        mProgressDialog.setMessage("잠시 기다려주세요..");
        mProgressDialog.show();

        //string 가져옴
        final String username = username_et.getText().toString();
        final String password = password_et.getText().toString();

        //유효한 값인지 검사 - 조건을 더 추가하는 게 좋을 듯
        //값이 비어있다면 바로 종료
        if(TextUtils.isEmpty(username)){
            username_et.setError("아이디를 적어주세요");
            username_et.requestFocus();
            mProgressDialog.dismiss();
            return;
        }

        if(TextUtils.isEmpty(password)){
            username_et.setError("비밀번호를 적어주세요");
            username_et.requestFocus();
            mProgressDialog.dismiss();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLS.login_api,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(!jsonObject.getBoolean("error")){
                                JSONObject jsonObjectUser = jsonObject.getJSONObject(("user"));

                                User user = new User(jsonObjectUser.getInt("id"), jsonObjectUser.getString("email"), jsonObjectUser.getString("username"));

                                //store user data inside sharedprefrences

                                //let user in
                                finish();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }

                        } catch(JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                //에러났을 떄
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String, String> authenticationVariables = new HashMap<>();
                authenticationVariables.put("username", username);
                authenticationVariables.put("password", password);
                return authenticationVariables;
            }
        };  //end of string Request
    }
}
