package com.dogpalja.mobileapplication5;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class HealthFragment extends Fragment {

    TextView vomit_s, tagNum, eV1date, eV2date, eV3date, eV4date, eHdate, eRdate, eBefore;
    EditText eName, eNow;
    CheckBox cV1, cV2, cV3, cV4, cH, cR, cNT, cVomit, cDiar, cNasal;
    Button bInsert_w;

    private DatePickerDialog.OnDateSetListener callbackMethod1;
    private DatePickerDialog.OnDateSetListener callbackMethod2;
    private DatePickerDialog.OnDateSetListener callbackMethod3;
    private DatePickerDialog.OnDateSetListener callbackMethod4;
    private DatePickerDialog.OnDateSetListener callbackMethodH;
    private DatePickerDialog.OnDateSetListener callbackMethodR;

    public HealthFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health, container, false);
        ;

        eName = (EditText) view.findViewById(R.id.name);

        eV1date = (TextView) view.findViewById(R.id.vaccin1_date);
        eV2date = (TextView) view.findViewById(R.id.vaccin2_date);
        eV3date = (TextView) view.findViewById(R.id.vaccin3_date);
        eV4date = (TextView) view.findViewById(R.id.vaccin4_date);
        eHdate = (TextView) view.findViewById(R.id.heartw_date);
        eRdate = (TextView) view.findViewById(R.id.rabies_date);

        eNow = (EditText) view.findViewById(R.id.now);

        eBefore = (TextView) view.findViewById(R.id.before);

        vomit_s = (TextView) view.findViewById(R.id.select_vomit);
        tagNum = (TextView) view.findViewById(R.id.insert_tn);

        cV1 = (CheckBox) view.findViewById(R.id.vaccin1);
        cV2 = (CheckBox) view.findViewById(R.id.vaccin2);
        cV3 = (CheckBox) view.findViewById(R.id.vaccin3);
        cV4 = (CheckBox) view.findViewById(R.id.vaccin4);
        cH = (CheckBox) view.findViewById(R.id.heartw);
        cR = (CheckBox) view.findViewById(R.id.rabies);
        cNT = (CheckBox) view.findViewById(R.id.ntag);
        cVomit = (CheckBox) view.findViewById(R.id.vomit);
        cDiar = (CheckBox) view.findViewById(R.id.diar);
        cNasal = (CheckBox) view.findViewById(R.id.nasal);

        bInsert_w = (Button) view.findViewById(R.id.insert_w);

        callbackMethod1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                eV1date.setText("접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일");
            }
        };

        callbackMethod2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                eV2date.setText("접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일  ");
            }
        };

        callbackMethod3 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                eV3date.setText("접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일  ");
            }
        };

        callbackMethod4 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                eV4date.setText("접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일  ");
            }
        };

        callbackMethodH = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                eHdate.setText("접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일  ");
            }
        };

        callbackMethodR = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                eRdate.setText("접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일  ");
            }
        };

        cV1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cV1.isChecked() == true) {
                    DatePickerDialog dialog = new DatePickerDialog(getContext(), callbackMethod1, 2019, 12, 19);
                    dialog.show();
                } else {
                    eV1date.setText("접종일을 입력해주세요");
                }
            }
        });

        cV2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cV2.isChecked() == true) {
                    DatePickerDialog dialog = new DatePickerDialog(getContext(), callbackMethod2, 2019, 12, 19);
                    dialog.show();
                } else {
                    eV2date.setText("접종일을 입력해주세요");
                }
            }
        });

        cV3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cV3.isChecked() == true) {
                    DatePickerDialog dialog = new DatePickerDialog(getContext(), callbackMethod3, 2019, 12, 19);
                    dialog.show();

                } else {
                    eV3date.setText("접종일을 입력해주세요");
                }
            }
        });

        cV4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cV4.isChecked() == true) {
                    DatePickerDialog dialog = new DatePickerDialog(getContext(), callbackMethod4, 2019, 12, 19);
                    dialog.show();
                } else {
                    eV4date.setText("접종일을 입력해주세요");
                }
            }
        });

        cH.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cH.isChecked() == true) {
                    DatePickerDialog dialog = new DatePickerDialog(getContext(), callbackMethodH, 2019, 12, 19);
                    dialog.show();
                } else {
                    eHdate.setText("접종일을 입력해주세요");
                }
            }
        });

        cR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cR.isChecked() == true) {
                    DatePickerDialog dialog = new DatePickerDialog(getContext(), callbackMethodR, 2019, 12, 19);
                    dialog.show();
                } else {
                    eRdate.setText("접종일을 입력해주세요");
                }
            }
        });

        cNT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cNT.isChecked() == true) {
                    HealthDialog commentInput = new HealthDialog(getContext());
                } else {
                    Toast.makeText(getContext(), "강아지 등록은 필수입니다",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

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
                    AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                    dlg.setTitle("가장 유사한 구토의 색 및 형태는?");
                    dlg.setSingleChoiceItems(vomitArray, 0,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            Toast.makeText(getContext(), "흰색은 거품토! 기침 및 호흡기 문제일 수 있어요",
                                                    Toast.LENGTH_LONG).show();
                                            break;
                                        case 1:
                                            Toast.makeText(getContext(), "노란색은 일명 공복 구토! 배고프다고 하네요!",
                                                    Toast.LENGTH_LONG).show();
                                            break;
                                        case 2:
                                            Toast.makeText(getContext(), "붉은색은 매우 위험해요! 위나 입안, 식도의 출혈을 확인하세요!",
                                                    Toast.LENGTH_LONG).show();
                                            break;
                                        case 3:
                                            Toast.makeText(getContext(), "초록색은 답즙 구토에요! 십이지장의 문제거나 물을 너무 마셨을 수 있어요!",
                                                    Toast.LENGTH_LONG).show();
                                            break;
                                        case 4:
                                            Toast.makeText(getContext(), "사료색은 소화불량 구토! 과식했을 확률 90%!",
                                                    Toast.LENGTH_LONG).show();
                                            break;
                                        case 5:
                                            Toast.makeText(getContext(), "WARNNING!!!!!!소장, 대장, 위 등의 문제! 병원으로 즉시 내원!!",
                                                    Toast.LENGTH_LONG).show();
                                            break;
                                    }
                                    vomit_s.setText("상태 : " + vomitArray[which]);
                                }
                            });
                    dlg.setPositiveButton("닫기", null);
                    dlg.show();
                } else {
                    Toast.makeText(getContext(), "구토는 멈췄나요? 뭐니뭐니해도 건강이 최고!",
                            Toast.LENGTH_LONG).show();
                    vomit_s.setText("상태 : 건강함 :)");
                }
            }
        });

        cDiar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cDiar.isChecked() == true) {
                    Toast.makeText(getContext(), "우선은 금식! 지속적이면 병원에 데려가주세요!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "이제 괜찮아졌나요? 간식 급여는 신중하게 해주세요 :)",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        cNasal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cNasal.isChecked() == true) {
                    Toast.makeText(getContext(), "콧물은 감기 전초증상! 병원에 데려가주세요!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "콧물은 이제 멈췄나요? 건강해져서 다행이에요!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}