package com.dogpalja.mobileapplication5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;


public class MapFragment extends Fragment implements OnMapReadyCallback{
    public static View rootView;
    public static MapView mapView;
    public static GoogleMap mMap;

    //파일입출력
    File[] files;
    File storageDir;



    //경도위도 배열
    Vector<Double> latitudeV;
    Vector<Double> longitudeV;

    public MapFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_story_map, container, false);

        //파일 불러오는 함수
        storageDir = getContext().getExternalFilesDir("PinPosition");
        files = storageDir.listFiles();


        latitudeV = new Vector<Double>(files.length);
        longitudeV = new Vector<Double>(files.length);

        try {
            readFile(getPositionFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        mapView = (MapView) rootView.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        for(int i = 0; i < latitudeV.size(); i++){
        }

        return rootView;
    }

    public void readFile(File file) throws IOException {
        BufferedReader br=new BufferedReader(new FileReader(file));
        String line=null;
        int i = 0;
        while((line=br.readLine())!=null) {
            if(i%2 == 0)
                latitudeV.addElement(Double.parseDouble(line));
            else
                longitudeV.addElement(Double.parseDouble(line));
            i++;
        }
    }

    private File getPositionFile(){
        File storageDir = getContext().getExternalFilesDir("PinPosition");
        File Position = new File(storageDir, "location.txt");
        return Position;
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
        final MarkerOptions markerOptions = new MarkerOptions();
        for(int i =0; i <latitudeV.size(); i++){
            markerOptions.position(new LatLng(latitudeV.get(i), longitudeV.get(i)));
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker));
            googleMap.addMarker(markerOptions);
        }

        //맵 위치 찍기.
        MapsInitializer.initialize(this.getActivity());
        //    getAddress(getContext(), currentLocation.getLatitude(), currentLocation.getLongitude()); 위치받아오는 getadderess
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(35.89, 128.61), 10);
        googleMap.animateCamera(cameraUpdate);
        //현재위치 구현
        ////////////////////////////////////
        mMap = googleMap;
        // 맵 터치 이벤트 구현 //
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
            }
        });

//정보창 클릭 리스너
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                startActivity(new Intent(MapFragment.this.getActivity() , Map_infocli.class));
                Log.i("clis", "정 보창 클릭");
            }
        });

//마커 클릭 리스너
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                return false;
            }
        });
    }
}