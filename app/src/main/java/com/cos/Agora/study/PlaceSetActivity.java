package com.cos.Agora.study;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.cos.Agora.global.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.cos.Agora.R;

public class PlaceSetActivity extends FragmentActivity implements OnMapReadyCallback {

    private Button placeCompleteButton;
    private GoogleMap googleMap;
    private Double studyLongitude;
    private Double studyLatitude;
    private String studyName;
    private String studyInterest;
    private int studyFrequency;
    private int studyMemNum;
    private String studyDescription;
    private int choiceInterest;
    private int choiceFrequency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeset);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        studyName = intent.getStringExtra("studyName");
        studyInterest = intent.getStringExtra("studyInterest");
        studyFrequency = intent.getIntExtra("studyFrequency",0);
        studyMemNum = intent.getIntExtra("studyMemNum",0);
        studyDescription = intent.getStringExtra("studyDescription");
        choiceFrequency = intent.getIntExtra("FrequencyChoice",0);
        choiceInterest = intent.getIntExtra("InterestChoice",0);

        placeCompleteButton = findViewById(R.id.btn_placeset); // 지우지 말기!!!!

        //장소 설정 완료 버튼 누를 경우 만들기
        placeCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //place 설정했으니, 다시 studycreate Activity로 이동
                Intent intent = new Intent(PlaceSetActivity.this, StudyCreateActivity.class);

                intent.putExtra("latitude",studyLatitude);
                intent.putExtra("longitude",studyLongitude);
                intent.putExtra("placeSet",true);//장소 설정에서 스터디 생성페이지로 넘어갔음을 나타내는 용도
                intent.putExtra("studyName",studyName);
                intent.putExtra("studyInterest",studyInterest);
                intent.putExtra("studyFrequency",studyFrequency);
                intent.putExtra("studyMemNum",studyMemNum);
                intent.putExtra("studyDescription",studyDescription);
                intent.putExtra("FrequencyChoice",choiceFrequency);
                intent.putExtra("InterestChoice",choiceInterest);

                startActivity(intent);
                PlaceSetActivity.this.finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        // map 터치한 위치에 마커 표시 후, 위도 경도 알려주기
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("스터디 장소");
                Double latitude = point.latitude; // 위도
                Double longitude = point.longitude; // 경도
                // 마커의 스니펫(간단한 텍스트) 설정
                mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                // LatLng: 위도 경도 쌍을 나타냄
                mOptions.position(new LatLng(latitude, longitude));
                // 마커 추가
                googleMap.addMarker(mOptions);
                // studycreateActivity로 넘겨주기 위한 위도, 경도
                studyLongitude = longitude;
                studyLatitude = latitude;
            }
        });

        // 처음에 자신의 위치로 지도 띄우기
        LatLng latlng = new LatLng(((User)getApplication()).getLatitude(), ((User)getApplication()).getLongitude());
        //LatLng latlng = new LatLng(37.557667, 126.926546);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        MarkerOptions markerOptions = new MarkerOptions().position(latlng).title("현재 내 위치");
        googleMap.addMarker(markerOptions);

        //GPS 사용권한 줬다면, 현재위치로 지도 움직일 수 있도록!
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }
        else {
            checkLocationPermissionWithRationale();
        }
    }

    //위치 권한 허용 안했을 시 처리
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private void checkLocationPermissionWithRationale(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("위치정보")
                        .setMessage("이 앱을 사용하기 위해서는 위치정보에 접근이 필요합니다. 위치정보 접근을 허용하여 주세요.")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(PlaceSetActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        googleMap.setMyLocationEnabled(true);
                    }
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}