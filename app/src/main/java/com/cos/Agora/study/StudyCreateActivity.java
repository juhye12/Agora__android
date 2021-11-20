package com.cos.Agora.study;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cos.Agora.CMRespDto;
import com.cos.Agora.R;
import com.cos.Agora.study.model.StudyCreateReqDto;
import com.cos.Agora.study.model.StudyCreateRespDto;
import com.cos.Agora.study.service.StudyService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudyCreateActivity extends AppCompatActivity {

    private static final String TAG = "CreateStudyActivity";
    private EditText studyName;
    private Spinner s_studyInterest;
    private Spinner s_studyFrequency;

    private EditText studyMemNum;
    private EditText studyDescription;
    private Button CompleteButton;
    private Button CloseButton;
    private ImageButton PlaceButton;

    private String mstudyName;
    private String mstudyInterest;
    private int mstudyFrequency;
    private int mstudyMemNum;
    private double mstudyLongitude;
    private double mstudyLatitude;
    private String mstudydescription;
    private Boolean placeset;//장소 설정을 하고 StudyCreateActivity에 돌아온 것인지를 확인하기 위함

    private com.cos.Agora.retrofitURL retrofitURL;
    private StudyService studyCreateService = retrofitURL.retrofit.create(StudyService .class);

    String[] interestList = {"전체","어학","프로그래밍","게임","취직","주식","운동","와인","여행"};
    String[] frequencyList = {"1","2","3","4","5","6","7"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studycreate);

        Intent intent = getIntent();
        placeset = intent.getBooleanExtra("placeSet",false);//어떤 액티비티에서 넘어온 것인지 알기 위함

        init();

        //장소 설정까지 하고 온거라면 스터디 정보를 처리
        if(placeset){
            studyInfoSet();
        }

        //Study interest 선택
        s_studyInterest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mstudyInterest = s_studyInterest.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Study frequency 선택
        s_studyFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mstudyFrequency = Integer.parseInt(s_studyFrequency.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //place버튼 누를 경우 만들기
        PlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudyCreateActivity.this, PlaceSetActivity.class);//place설정하는 Activity로 이동
                //입력받은 값들을 전부 PlaceSetActivity로 넘겨주기! -> 값을 기억하고, 돌아왔을때 그대로 화면에 띄우기 위해서
                intent.putExtra("studyName",studyName.getText().toString());
                intent.putExtra("studyInterest",mstudyInterest);
                intent.putExtra("studyFrequency",mstudyFrequency);
                intent.putExtra("studyMemNum",studyMemNum.getText().toString());
                intent.putExtra("studyDescription",studyDescription.getText().toString());
                intent.putExtra("InterestChoice",s_studyInterest.getSelectedItemPosition());
                intent.putExtra("FrequencyChoice",s_studyFrequency.getSelectedItemPosition());
                startActivity(intent);
            }
        });

        CompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completeCreate();//스터디 생성하는 method
            }
        });
        CloseButton.setOnClickListener(new View.OnClickListener(){ //'닫기'누르면 다시 목록 화면으로 돌아감
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudyCreateActivity.this, StudyListActivity.class);
                startActivity(intent);
                StudyCreateActivity.this.finish();
            }
        });
    }

    private void studyInfoSet() {
        //가지고 온 값들 변수에 넣어주기
        Intent intent = getIntent();
        mstudyName = intent.getStringExtra("studyName");
        mstudyInterest = intent.getStringExtra("studyInterest");
        mstudyFrequency = intent.getIntExtra("studyFrequency",0);
        mstudyMemNum =  intent.getIntExtra("studyMemNum",0);
        mstudyLongitude = intent.getDoubleExtra("longitude",0);
        mstudyLatitude = intent.getDoubleExtra("latitude",0);
        mstudydescription = intent.getStringExtra("studyDescription");
        int cinterest = intent.getIntExtra("InterestChoice",0);
        int cfrequency = intent.getIntExtra("FrequencyChoice",0);

        //가지고 온 값들대로 화면에 표시해주기
        studyName.setText(mstudyName);
        s_studyInterest.setSelection(cinterest);
        s_studyFrequency.setSelection(cfrequency);
        studyMemNum.setText(mstudyMemNum);
        studyDescription.setText(mstudydescription);

    }

    //입력받은 값들 저장
    public void init(){
        studyName = findViewById(R.id.edit_create_name);
        studyMemNum = findViewById(R.id.edit_create_MemNum);
        studyDescription = findViewById(R.id.edit_create_description);
        CompleteButton = findViewById(R.id.btn_create_complete);
        CloseButton = findViewById(R.id.btn_create_close);
        PlaceButton = findViewById(R.id.btn_creat_place);

        //스터디 interest spinner
        s_studyInterest= findViewById(R.id.spinner_create_interest);
        ArrayAdapter<String> adapter_studyinterest = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, interestList);
        adapter_studyinterest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_studyInterest.setAdapter(adapter_studyinterest);
        s_studyInterest.setSelection(0);

        //스터디 count(모임 횟수) spinner
        s_studyFrequency = findViewById(R.id.spinner_create_frequency);
        ArrayAdapter<String> adapter_studyfrequency = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, frequencyList);
        adapter_studyfrequency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_studyFrequency.setAdapter(adapter_studyfrequency);
        s_studyFrequency.setSelection(0);
    }

    //StudyCreateActivity의 main method
    private void completeCreate() {

        //PlaceSetActivity로 부터 전달 받은 위도 경도를 저장
        Intent intent = getIntent();
        mstudyLongitude = intent.getDoubleExtra("latitude",0);
        mstudyLatitude = intent.getDoubleExtra("longitude",0);

        //Dto 객체 생성하고 startStudyCreate 실행

        startStudyCreate(new StudyCreateReqDto(mstudyName,mstudyInterest,mstudyFrequency,mstudyMemNum,mstudyLongitude,mstudyLatitude,mstudydescription));// Req객체 생성

        Intent intent2 = new Intent(StudyCreateActivity.this, StudyListActivity.class);
        startActivity(intent2);
        StudyCreateActivity.this.finish();
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
                editor.putString("studyName",studyCreateRespDto.getTitle());
                editor.putString("studyInterest",studyCreateRespDto.getInterest());
                editor.putInt("studyFrequency",studyCreateRespDto.getCount());
                editor.putInt("studyMemNum",studyCreateRespDto.getLimit());
                editor.putString("studyDescription",studyCreateRespDto.getDescription());
                editor.putFloat("studyLongitude",studyCreateRespDto.getLongitude().floatValue());//float로 변환. (editor에서 double안됨)
                editor.putFloat("studyLatitude",studyCreateRespDto.getLatitude().floatValue());
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
}
