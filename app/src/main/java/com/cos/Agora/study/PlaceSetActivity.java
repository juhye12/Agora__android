package com.cos.Agora.study;

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

public class PlaceSetActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "PlaceSetActivity";
    private GoogleMap googleMap;
    private Button CompleteButton;

    private String mstudyName;
    private String mstudyInterest;
    private int mstudyFrequency;
    private int mstudyMemNum;
    private double mstudyLongitude;
    private double mstudyLatitude;
    private String mstudydescription;
    
    private com.cos.Agora.retrofitURL retrofitURL;
    private StudyService studyCreateService = retrofitURL.retrofit.create(StudyService .class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeset);

        init();
        
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        CompleteButton = findViewById(R.id.btn_create_complete);

        CompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                completeCreate();//스터디 생성하는 method
            }
        });
    }

    private void init() {
        Intent intent = getIntent();
        mstudyName = intent.getStringExtra("studyName");
        mstudyInterest = intent.getStringExtra("studyInterest");
        mstudyFrequency = intent.getIntExtra("studyFrequency",0);
        mstudyMemNum =  intent.getIntExtra("studyMemNum",0);
        mstudydescription = intent.getStringExtra("studyDescription");
    }

    private void completeCreate() {
        //Dto 객체 생성하고 startStudyCreate 실행
        startStudyCreate(new StudyCreateReqDto(mstudyName,mstudyInterest,mstudyFrequency,mstudyMemNum,mstudyLongitude,mstudyLatitude,mstudydescription));// Req객체 생성

        Intent intent = new Intent(PlaceSetActivity.this, StudyListActivity.class);
        startActivity(intent);
        PlaceSetActivity.this.finish();
    }

    private void startStudyCreate(StudyCreateReqDto studyCreateReqDto) {
        Call<CMRespDto<StudyCreateRespDto>> call = studyCreateService.createStudy(studyCreateReqDto);
        call.enqueue(new Callback<CMRespDto<StudyCreateRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<StudyCreateRespDto>> call, Response<CMRespDto<StudyCreateRespDto>> response) {
                CMRespDto<StudyCreateRespDto> cmRespDto = response.body();
                StudyCreateRespDto studyCreateRespDto = cmRespDto.getData();
                Log.d(TAG, "onResponse: 스터디 생성 성공!!!");

                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("userId", studyCreateRespDto.getId());
                editor.putString("studyName",studyCreateRespDto.getStudyName());
                editor.putString("studyInterest",studyCreateRespDto.getStudyInterest());
                editor.putInt("studyFrequency",studyCreateRespDto.getStudyFrequency());
                editor.putInt("studyMemNum",studyCreateRespDto.getStudyMemNum());
                editor.putString("studyDescription",studyCreateRespDto.getStudyDescription());
                editor.putFloat("studyLongitude",studyCreateRespDto.getStudyLongitude().floatValue());//float로 변환. (editor에서 double안됨)
                editor.putFloat("studyLatitude",studyCreateRespDto.getStudyLatitude().floatValue());
                editor.commit();
            }
            @Override
            public void onFailure(Call<CMRespDto<StudyCreateRespDto>> call, Throwable t) {
//                Toast.makeText(StudyCreateActivity.this, "스터디 생성 에러 발생", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "스터디 생성 에러 발생");
//                Log.e("스터디 생성 에러 발생", t.getMessage());
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
                
                //스터디 위도 경도 설정
                mstudyLongitude = longitude;
                mstudyLatitude = latitude;
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