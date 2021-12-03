package com.cos.Agora.study.mystudy;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.cos.Agora.CMRespDto;
import com.cos.Agora.R;
import com.cos.Agora.global.User;
import com.cos.Agora.study.StudyListActivity;
import com.cos.Agora.study.model.StudyCreateReqDto;
import com.cos.Agora.study.model.StudyCreateRespDto;
import com.cos.Agora.study.service.StudyService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudyPlaceActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "StudyPlaceActivity";
    private GoogleMap googleMap;

    private double mstudyLongitude;
    private double mstudyLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studyplace);

        
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

//    private void init() {
//        Intent intent = getIntent();
//        mstudyName = intent.getStringExtra("studyName");
//        mstudyInterest = intent.getStringExtra("studyInterest");
//        mstudyFrequency = intent.getIntExtra("studyFrequency",0);
//        mstudyMemNum =  intent.getIntExtra("studyMemNum",0);
//        mstudydescription = intent.getStringExtra("studyDescription");
//    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;


        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra("latitude", 2.0);
        double longitude = intent.getDoubleExtra("longitude", 2.0);

        // 처음에 자신의 위치로 지도 띄우기
        LatLng latlng = new LatLng(latitude, longitude);
        //LatLng latlng = new LatLng(37.557667, 126.926546);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        MarkerOptions markerOptions = new MarkerOptions().position(latlng).title("현재 내 위치");
        googleMap.addMarker(markerOptions);
    }

}