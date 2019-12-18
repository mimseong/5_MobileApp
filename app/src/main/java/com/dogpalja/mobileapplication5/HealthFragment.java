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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class HealthFragment extends Fragment {

    TextView vomit_s, tagNum, eV1date, eV2date, eV3date, eV4date, eHdate, eRdate, eBefore;
    EditText eName, eNow;
    CheckBox cV1, cV2, cV3, cV4, cH, cR, cNT, cVomit, cDiar, cNasal;
    ImageButton bInsert, bInsert_w;

    String[] data;
    String dog, vc1, vc1d, vc2, vc2d, vc3, vc3d, vc4, vc4d, vH, vhd, vR, vrd, weight, vo;

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

        data = new String[9];

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

        bInsert = (ImageButton) view.findViewById(R.id.insert);
        bInsert_w = (ImageButton) view.findViewById(R.id.insert_w);

        bInsert.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                dog = eName.getText().toString();
                data[0] = dog;
                Toast.makeText(getContext(), data[0]+"의 건강체크를 시작합니다",
                        Toast.LENGTH_LONG).show();
            }
        });

        callbackMethod1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                vc1 = "접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일";
                eV1date.setText(vc1);
                data[1] = vc1;
            }
        };

        callbackMethod2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                vc2 = "접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일";
                eV2date.setText(vc2);
                data[2] = vc2;
            }
        };

        callbackMethod3 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                vc3 = "접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일";
                eV3date.setText(vc3);
                data[3] = vc3;
            }
        };

        callbackMethod4 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                vc4 = "접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일";
                eV4date.setText(vc4);
                data[4] = vc4;
            }
        };

        callbackMethodH = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                vH = "접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일";
                eHdate.setText(vH);
                data[5] = vH;
            }
        };

        callbackMethodR = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                vR = "접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일";
                eRdate.setText(vR);
                data[6] = vR;
            }
        };

        cV1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cV1.isChecked() == true) {
                    DatePickerDialog dialog = new DatePickerDialog(getContext(), callbackMethod1, 2019, 12, 19);
                    dialog.show();
                } else {
                    vc1d = "접종일을 입력해주세요";
                    eV1date.setText(vc1d);
                    data[1] = vc1d;
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
                    vc2d = "접종일을 입력해주세요";
                    eV2date.setText(vc2d);
                    data[2] = vc2d;
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
                    vc3d = "접종일을 입력해주세요";
                    eV3date.setText(vc3d);
                    data[3] = vc3d;
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
                    vc4d = "접종일을 입력해주세요";
                    eV4date.setText(vc4d);
                    data[4] = vc4d;
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
                    vhd = "접종일을 입력해주세요";
                    eHdate.setText(vhd);
                    data[5] = vhd;
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
                    vrd = "접종일을 입력해주세요";
                    eRdate.setText(vrd);
                    data[6] = vrd;
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
                weight = eNow.getText().toString();
                eBefore.setText(weight);
                data[7] = weight;
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
                                    vo = "상태 : " + vomitArray[which];
                                    vomit_s.setText(vo);
                                    data[8] = vo;
                                }
                            });
                    dlg.setPositiveButton("닫기", null);
                    dlg.show();
                } else {
                    Toast.makeText(getContext(), "구토는 멈췄나요? 뭐니뭐니해도 건강이 최고!",
                            Toast.LENGTH_LONG).show();
                    vo = "상태 : 건강함 :)";
                    vomit_s.setText(vo);
                    data[8] = vo;
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