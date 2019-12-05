package com.dogpalja.mobileapplication5;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")

public class HealthMain extends TabActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_main);
//탭 호스트 정의
        TabHost tabHost = getTabHost();
        Resources res = getResources();
        TabHost.TabSpec spec;
        Intent intent;

//각 탭 별로 액티비티 불러오기
        TabHost.TabSpec tabSpecDog1 = tabHost.newTabSpec("첫번째").setIndicator("Dog1");
        tabSpecDog1.setContent(R.layout.health_first);
        tabHost.addTab(tabSpecDog1);

        TabHost.TabSpec tabSpecDog2 = tabHost.newTabSpec("두번째").setIndicator("Dog2");
        tabSpecDog2.setContent(R.layout.health_second);
        tabHost.addTab(tabSpecDog2);

        TabHost.TabSpec tabSpecDog3 = tabHost.newTabSpec("세번째").setIndicator("Dog3");
        tabSpecDog3.setContent(R.layout.health_third);
        tabHost.addTab(tabSpecDog3);

        tabHost.setCurrentTab(0); //초기 탭 설정
    }


}
