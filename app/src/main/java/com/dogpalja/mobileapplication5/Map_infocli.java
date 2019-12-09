package com.dogpalja.mobileapplication5;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.Nullable;

public class Map_infocli extends Activity implements View.OnClickListener {

    //확인 버튼 취소 버튼
    private Button mCofirm, mCancel;
    private Button add_pic;// 사진추가 버튼

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.stroy_map_picture);

        setContent();
    }

    private void setContent() {
        mCancel = (Button)findViewById(R.id.cancel);
        mCofirm = (Button)findViewById(R.id.ok);
        add_pic = (Button)findViewById(R.id.add_pic);
        mCofirm.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        add_pic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case R.id.cancel :
            this.finish();
            break;
        case R.id.ok :
            this.finish();
            break;
        case R.id.add_pic :
            break;
    }
    }
}
