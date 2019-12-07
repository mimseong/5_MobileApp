package com.dogpalja.mobileapplication5;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")

public class HealthMain extends TabActivity {

    TabHost tabs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_main);

        tabs = getTabHost();
        tabs.getTabWidget().setStripEnabled(false);

        TabHost.TabSpec tabSpec1 = tabs.newTabSpec("첫째");
        tabSpec1.setIndicator("첫째", getResources().getDrawable(R.drawable.dog));
        tabSpec1.setContent(new Intent(this, HealthFirst.class)); // Tab Content
        tabs.addTab(tabSpec1);

        TabHost.TabSpec tabSpec2 = tabs.newTabSpec("둘째");
        tabSpec2.setIndicator("둘째", getResources().getDrawable(R.drawable.dog));
        tabSpec2.setContent(new Intent(this, HealthSecond.class)); // Tab Content
        tabs.addTab(tabSpec2);


        // Tab3 Setting
        TabHost.TabSpec tabSpec3 = tabs.newTabSpec("셋째");
        tabSpec3.setIndicator("셋째", getResources().getDrawable(R.drawable.dog));
        tabSpec3.setContent(new Intent(this, HealthThird.class)); // Tab Content
        tabs.addTab(tabSpec3);

        tabs.setCurrentTab(0);  // 시작 탭의 위치

        for (int i = 0; i < tabs.getTabWidget().getChildCount(); i++) {
            tabs.getTabWidget().getChildAt(i).getLayoutParams().height = (int) (70 * (getResources().getDisplayMetrics().density));
            tabs.getTabWidget().getChildAt(i).getLayoutParams().width = (int) (60 * (getResources().getDisplayMetrics().density));
        }
    }
}
