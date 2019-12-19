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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HealthFragment extends Fragment {

    TextView vomit_s, tagNum, eV1date, eV2date, eV3date, eV4date, eHdate, eRdate, eBefore;
    EditText eName, eNow;
    CheckBox cV1, cV2, cV3, cV4, cH, cR, cNT, cVomit, cDiar, cNasal;
    ImageButton bInsert, bInsert_w;
    Button btn_capture_img;

    String[] data;

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

        init(view);             //변수 값 만들기
        initString();           //String 배열 초기화
        putOnClickListener();   //버튼에 onClick 함수 달기

        try {
            loadResult(getPositionFile());      //저장된 텍스트 값 불러오기
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_capture_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveResult();           //문자열 내장 메모리에 저장
            }
        });
    }

    public void initString(){
        data[0] = "이름";
        for(int i = 1;  i < 7; i++){
            data[i] = "날짜를 입력하세요";
        }
        data[7] = "0";
        data[8] = "";
    }

    public void loadResult(File file) throws IOException {
        BufferedReader br=new BufferedReader(new FileReader(file));
        String line=null;

        int i = 0;
        while((line=br.readLine())!=null) {
            data[i] = line;
            i++;
        }

        setResult();        //불러온 문자열 setText해서 화면에 보여짐
    }

    public void setResult() {
        eName.setText(data[0]);
        eV1date.setText(data[1]);
        eV2date.setText(data[2]);
        eV3date.setText(data[3]);
        eV4date.setText(data[4]);
        eHdate.setText(data[5]);
        eRdate.setText(data[6]);
        eBefore.setText(data[7]);
        vomit_s.setText(data[8]);
    }

    public void saveResult(){
        writeSettings(getPositionFile());
    }

    public void writeSettings(File file){
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String writeTmp = "";

        for(int i = 0; i < data.length; i++){
            writeTmp += data[i] + '\n';
        }

        try {
            // 기존 파일의 내용에 이어서 쓰려면 true를, 기존 내용을 없애고 새로 쓰려면 false를 지정한다.
            writer.write(writeTmp);
            writer.flush();

        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null) writer.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File getPositionFile(){
        File storageDir = getContext().getExternalFilesDir("HealthCheck");
        File Position = new File(storageDir, "health.txt");
        return Position;
    }


    //버튼 클릭하게 하는 부분 함수 분리함
    public void putOnClickListener() {

        bInsert.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                data[0] = eName.getText().toString();
                Toast.makeText(getContext(), data[0]+"의 건강체크를 시작합니다",
                        Toast.LENGTH_LONG).show();
            }
        });

        callbackMethod1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                data[1] = "접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일";
                eV1date.setText(data[1]);
            }
        };

        callbackMethod2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                data[2] = "접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일";
                eV2date.setText(data[2]);
            }
        };

        callbackMethod3 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                data[3] = "접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일";
                eV3date.setText(data[3]);
            }
        };

        callbackMethod4 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                data[4] = "접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일";
                eV4date.setText(data[4]);
            }
        };

        callbackMethodH = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                data[5] = "접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일";
                eHdate.setText(data[5]);
            }
        };

        callbackMethodR = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                data[6] = "접종일 : " + year + "년  " + monthOfYear + 1 + "월  " + dayOfMonth + "일";
                eRdate.setText(data[6]);
            }
        };

        cV1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cV1.isChecked() == true) {
                    DatePickerDialog dialog = new DatePickerDialog(getContext(), callbackMethod1, 2019, 12, 19);
                    dialog.show();
                } else {
                    data[1] = "접종일을 입력해주세요";
                    eV1date.setText(data[1]);
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
                    data[2] = "접종일을 입력해주세요";
                    eV2date.setText(data[2]);
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
                    data[3] = "접종일을 입력해주세요";
                    eV3date.setText(data[3]);
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
                    data[4] = "접종일을 입력해주세요";
                    eV4date.setText(data[4]);
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
                    data[5] = "접종일을 입력해주세요";
                    eHdate.setText(data[5]);
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
                    data[6] = "접종일을 입력해주세요";
                    eRdate.setText(data[6]);
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
                data[7] = eNow.getText().toString();
                eBefore.setText(data[7]);
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
                                    data[8] = "상태 : " + vomitArray[which];
                                    vomit_s.setText(data[8]);
                                }
                            });
                    dlg.setPositiveButton("닫기", null);
                    dlg.show();
                } else {
                    Toast.makeText(getContext(), "구토는 멈췄나요? 뭐니뭐니해도 건강이 최고!",
                            Toast.LENGTH_LONG).show();
                    data[8] = "상태 : 건강함 :)";
                    vomit_s.setText(data[8]);
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

    }

    //View에 추가하는 부분
    public void init(View view){
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

        btn_capture_img = (Button) view.findViewById(R.id.btn_capture_img);
    }


}