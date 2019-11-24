package com.dogpalja.mobileapplication5;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
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

public class StoryMap extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

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
                                Log.i("cli", "마커 추가");
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
        Log.i("cli", "마커 클릭");
        return false;
    }
    //정보창 클릭 이벤트
    @Override
    public void onInfoWindowClick(Marker marker) {
    Log.i("cli", "정보창 클릭");
    }
}