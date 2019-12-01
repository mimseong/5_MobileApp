package com.dogpalja.mobileapplication5;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {
    public static View rootView;
    public static MapView mapView;
    public static GoogleMap mMap;
    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.activity_story_map, container, false);
        mapView = (MapView) rootView.findViewById(R.id.mapview);
      mapView.onCreate(savedInstanceState);
       mapView.getMapAsync(this);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

   @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();

    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        MapsInitializer.initialize(this.getActivity());
       CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(35.89, 128.61), 10);

        googleMap.animateCamera(cameraUpdate);
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(MapFragment.this.getActivity());
                dialog.setTitle("가봤던 곳").setMessage("핀을 추가 하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("clis", "마커 추가");
                           googleMap.addMarker(mOptions); // 마커 추가.
                                Toast.makeText(MapFragment.this.getActivity(), "추가 하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("clis", "클릭했습니다.");
                        Toast.makeText(MapFragment.this.getActivity(), "추가 하지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();
            }
        });

//        mMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) MapFragment.this.getActivity()); //정보창 클릭 리스너
//        mMap.setOnInfoWindowClickListener((GoogleMap.OnInfoWindowClickListener) MapFragment.this.getActivity()); // 마커 클릭 리스너
        ////카메라 줌부분.
     //   LatLng knu = new LatLng(35.89, 128.61);
     //   mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(knu, 10));
    //    googleMap.addMarker(new MarkerOptions()
      //          .position(new LatLng(35.141233, 126.925594))
        //        .title("루프리코리아"));

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Log.i("clis", "정보창 클릭");
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.i("clis", "마커 클릭");
        return false;
    }
}
