package com.dogpalja.mobileapplication5;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class StoryMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        /////////////////////////////////////////
        //  Button btn = (Button) findViewById(R.id.btn); // 메뉴 버튼
        Button btn1 = (Button) findViewById(R.id.btn1); //검색 버튼
        final EditText edit = (EditText) findViewById(R.id.editText);

        //자동검색 버튼 만들기.
        btn1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                String str=edit.getText().toString();
                List<Address> addressList = null;

                try {//edittext에 입력한 텍스트를 지오 코딩을 이용해서 변환하는 부분.
                    addressList = geocoder.getFromLocationName(str, 10);
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                System.out.println(addressList.get(0).toString());
                String []splitStr = addressList.get(0).toString().split(","); // 콤마를 기준으로 스플릿.
                String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() -2); //주소
                System.out.println(address);

                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); //경도
                System.out.println(latitude);
                System.out.println(longitude);

                LatLng point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude)); //좌표 생성(위도, 경도)
                //마커 생성.
                MarkerOptions mOptions2 = new MarkerOptions();
                mOptions2.title("search result");
                mOptions2.snippet(address);
                mOptions2.position(point);
                //마커 추가
                mMap.addMarker(mOptions2);
                //해당 좌표로 화면 줌
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));
            }
        });
        //////////////////////////////////// 자동검색 https://m.blog.naver.com/qbxlvnf11/221183308547 << 여기 참조
    /*
    //버튼으로 메뉴 팝업창 만들기.
    btn.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v) {
    PopupMenu popup = new PopupMenu(getApplicationContext(), v);

    // getMenuInflater().inflate(R.menu.menu, popup.getMenu());
    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
    @Override
    public boolean onMenuItemClick(MenuItem item) {
    switch (item.getItemId()){
    // case R.id.m1, 2, 3을 추가 하면 된다. 이때 intent 하면 될거같당..;
    // 하고나면 break;
    //default 도 break;
    }
    return false;
    }
    });
    popup.show(); //팝업으로 메뉴 보이게 함.
    }
    });
    */
    }
    ///////// 버튼으로 메뉴 팝업창 만들기.

    /*
    ///////////////////////////////////////////////
    // 메뉴 활성화 시키기.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu, menu);
    return true;
    }
    //메뉴 아이템 선택시 옵션
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    Toast toast = Toast.makeText(getApplicationContext(),"", Toast.LENGTH_LONG);

    switch(item.getItemId())
    {
    case R.id.menu1:
    toast.setText("Select 화면전환");
    break;
    case R.id.menu2:
    toast.setText("Select 라이브러리");
    break;
    case R.id.menu3:
    toast.setText("Select 환경설정");
    break;
    }

    toast.show();

    return super.onOptionsItemSelected(item);
    }
    ////////////////////////////////////////
    */

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);
        // 맵 터치 이벤트 구현 //
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                final MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("대표사진보기");
                // Double latitude = point.latitude; // 위도
                // Double longitude = point.longitude; // 경도
                // 마커의 스니펫(간단한 텍스트) 설정
                // mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                // LatLng: 위도 경도 쌍을 나타냄
                mOptions.position(new LatLng(point.latitude, point.longitude));
                // 마커(핀) 추가 , 맵 클릭시 확인창 띄우기
                AlertDialog.Builder dialog = new AlertDialog.Builder(StoryMap.this);
                dialog.setTitle("가봤던 곳").setMessage("핀을 추가 하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                googleMap.addMarker(mOptions); // 마커 추가.
                                Toast.makeText(StoryMap.this, "추가 하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(StoryMap.this, "추가 하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();

            }
            // 정보창 클릭 리스너 (클릭하면 넘어가게끔 만들자.)
            GoogleMap.OnInfoWindowClickListener infoWindowClickListener;
            {
                infoWindowClickListener  = new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        String markerId = marker.getId();
                        Toast.makeText(StoryMap.this, "정보창 클릭 Marker ID : ", Toast.LENGTH_SHORT).show();
                    }
                };
                ///////////////////////////
            }
        });

        ////////////////////
        // 마커클릭리스너는 snippet정보가 있으면 중복되어 이벤트처리가 안되는거같다.
        ////////////////////구글맵 마커 클릭 리스너
    /* GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
    @Override
    public boolean onMarkerClick(Marker marker) {
    String text = "[마커 클릭 이벤트] latitude ="
    + marker.getPosition().latitude + ", longitude ="
    + marker.getPosition().longitude;
    Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();

    return false;
    }
    };
    */

        ////카메라 줌부분.
        LatLng knu = new LatLng(35.89, 128.61);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(knu, 10));
    } //mapready 마지막 부분.

}