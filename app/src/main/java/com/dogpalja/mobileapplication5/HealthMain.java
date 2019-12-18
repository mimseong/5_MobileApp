package com.dogpalja.mobileapplication5;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HealthMain extends Activity {

    TextView vomit_s, tagNum, eV1date, eV2date, eV3date, eV4date, eHdate, eRdate,  eBefore;
    EditText eName, eNow, TN;
    CheckBox cV1, cV2, cV3, cV4, cH, cR, cNT, cVomit, cDiar, cNasal;
    Button bInsert, bInsert_w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_check);

        eName = (EditText) findViewById(R.id.name);

        eV1date = (TextView) findViewById(R.id.vaccin1_date);
        eV2date = (TextView) findViewById(R.id.vaccin2_date);
        eV3date = (TextView) findViewById(R.id.vaccin3_date);
        eV4date = (TextView) findViewById(R.id.vaccin4_date);
        eHdate = (TextView) findViewById(R.id.heartw_date);
        eRdate = (TextView) findViewById(R.id.rabies_date);

        eNow = (EditText) findViewById(R.id.now);

        eBefore = (TextView) findViewById(R.id.before);

        vomit_s = (TextView) findViewById(R.id.select_vomit);
        tagNum = (TextView) findViewById(R.id.insert_tn);

        cV1 = (CheckBox) findViewById(R.id.vaccin1);
        cV2 = (CheckBox) findViewById(R.id.vaccin2);
        cV3 = (CheckBox) findViewById(R.id.vaccin3);
        cV4 = (CheckBox) findViewById(R.id.vaccin4);
        cH = (CheckBox) findViewById(R.id.heartw);
        cR = (CheckBox) findViewById(R.id.rabies);
        cNT = (CheckBox) findViewById(R.id.ntag);
        cVomit = (CheckBox) findViewById(R.id.vomit);
        cDiar = (CheckBox) findViewById(R.id.diar);
        cNasal = (CheckBox) findViewById(R.id.nasal);

        bInsert = (Button)findViewById(R.id.insert);
        bInsert_w = (Button)findViewById(R.id.insert_w);

        bInsert_w.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                eBefore.setText(eNow.getText().toString());
            }
        });

        cVomit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cVomit.isChecked() == true) {
                    final String[] vomitArray = new String[]{"흰색+거품", "노란색", "붉은색", "초록색", "사료색", "암적색"};
                    AlertDialog.Builder dlg = new AlertDialog.Builder(
                            HealthMain.this);
                    dlg.setTitle("가장 유사한 구토의 색 및 형태는?");
                    dlg.setSingleChoiceItems(vomitArray, 0,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            Toast.makeText(getApplicationContext(), "흰색은 거품토! 기침 및 호흡기 문제일 수 있어요",
                                                    Toast.LENGTH_LONG).show();
                                            break;
                                        case 1:
                                            Toast.makeText(getApplicationContext(), "노란색은 일명 공복 구토! 배고프다고 하네요!",
                                                    Toast.LENGTH_LONG).show();
                                            break;
                                        case 2:
                                            Toast.makeText(getApplicationContext(), "붉은색은 매우 위험해요! 위나 입안, 식도의 출혈을 확인하세요!",
                                                    Toast.LENGTH_LONG).show();
                                            break;
                                        case 3:
                                            Toast.makeText(getApplicationContext(), "초록색은 답즙 구토에요! 십이지장의 문제거나 물을 너무 마셨을 수 있어요!",
                                                    Toast.LENGTH_LONG).show();
                                            break;
                                        case 4:
                                            Toast.makeText(getApplicationContext(), "사료색은 소화불량 구토! 과식했을 확률 90%!",
                                                    Toast.LENGTH_LONG).show();
                                            break;
                                        case 5:
                                            Toast.makeText(getApplicationContext(), "WARNNING!!!!!!소장, 대장, 위 등의 문제! 병원으로 즉시 내원!!",
                                                    Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                    vomit_s.setText("상태 : " + vomitArray[which]);
                                }
                            });
                    dlg.setPositiveButton("닫기", null);
                    dlg.show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "구토는 멈췄나요? 뭐니뭐니해도 건강이 최고!",
                            Toast.LENGTH_LONG).show();
                    vomit_s.setText("상태 : 건강함 :)");
                }
            }
        });
    }

    //설사 체크박스 클릭 시 토스트 띄우는 부분
    public void onDiarClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        if (checked)
            Toast.makeText(getApplicationContext(), "우선은 금식! 지속적이면 병원에 데려가주세요!",
                    Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), "이제 괜찮아졌나요? 간식 급여는 신중하게 해주세요 :)",
                    Toast.LENGTH_LONG).show();
    }

    //콧물 체크박스 클릭 시 토스트 띄우는 부분
    public void onNasalClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        if (checked)
            Toast.makeText(getApplicationContext(), "콧물은 감기 전초증상! 병원에 데려가주세요!",
                    Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getApplicationContext(), "콧물은 이제 멈췄나요? 건강해져서 다행이에요!",
                    Toast.LENGTH_LONG).show();
    }

}
