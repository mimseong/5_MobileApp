package com.dogpalja.mobileapplication5;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class StoryMap extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener{

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("clis", "맵 생성");
        setContentView(R.layout.activity_story_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.storymap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        // 맵 터치 이벤트 구현 //
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                final MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("대표사진보기");
                mOptions.position(new LatLng(point.latitude, point.longitude));
                // 마커(핀) 추가 , 맵 클릭시 확인창 띄우기
                AlertDialog.Builder dialog = new AlertDialog.Builder(StoryMap.this);
                dialog.setTitle("가봤던 곳").setMessage("핀을 추가 하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("clis", "마커 추가");
                                googleMap.addMarker(mOptions); // 마커 추가.
                                Toast.makeText(StoryMap.this, "추가 하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("clis", "클릭했습니다.");
                        Toast.makeText(StoryMap.this, "추가 하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
            }
        });

        mMap.setOnMarkerClickListener(this); //정보창 클릭 리스너
        mMap.setOnInfoWindowClickListener(this); // 마커 클릭 리스너
        ////카메라 줌부분.
        LatLng knu = new LatLng(35.89, 128.61);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(knu, 10));

    } //mapready 마지막 부분.

    //마커 클릭 이벤트
    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i("clis", "마커 클릭");
        return false;
    }

    //정보창 클릭 이벤트
    @Override
    public void onInfoWindowClick(Marker marker) {
        Context mContext = getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

        //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
        View layout = inflater.inflate(R.layout.fragment_map,(ViewGroup) findViewById(R.id.popup));
        AlertDialog.Builder aDialog = new AlertDialog.Builder(this);

        aDialog.setTitle("대표 사진"); //타이틀바 제목
        aDialog.setView(layout); //dialog.xml 파일을 뷰로 셋팅
        //그냥 닫기버튼을 위한 부분
        aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        //저 부분에 사진 추가 할부분~~
        aDialog.setNeutralButton("사진추가", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //팝업창 생성
        AlertDialog ad = aDialog.create();
        ad.show();//보여줌!
    Log.i("clis", "정보창 클릭");
    }

}