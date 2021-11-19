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
    private String studyInterest;
    private Integer studyFrequency;
    private EditText studyMemNum;
    private EditText studyDescription;
    private Button CompleteButton;
    private Button CloseButton;
    private ImageButton PlaceButton;
    private Double studyLongitude;
    private Double studyLatitude;

    private com.cos.Agora.retrofitURL retrofitURL;
    private StudyService studyCreateService = retrofitURL.retrofit.create(StudyService .class);

    String[] interestList = {"전체","어학","프로그래밍","게임","취직","주식","운동","와인","여행"};
    String[] frequencyList = {"1","2","3","4","5","6","7"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studycreate);

        init();

        //Study interest 선택
        s_studyInterest= findViewById(R.id.spinner_create_interest);
        ArrayAdapter<String> adapter_studyinterest = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, interestList);
        adapter_studyinterest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_studyInterest.setAdapter(adapter_studyinterest);
        s_studyInterest.setSelection(0);

        s_studyInterest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                studyInterest = s_studyInterest.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Study frequency 선택
        s_studyFrequency = findViewById(R.id.spinner_create_frequency);
        ArrayAdapter<String> adapter_studyfrequency = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, frequencyList);
        adapter_studyfrequency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s_studyFrequency.setAdapter(adapter_studyfrequency);
        s_studyFrequency.setSelection(0);

        s_studyFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                studyFrequency = Integer.parseInt(s_studyFrequency.getSelectedItem().toString());
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

    //입력받은 값들 저장
    public void init(){
        studyName = findViewById(R.id.edit_create_name);
        studyMemNum = findViewById(R.id.edit_create_MemNum);
        studyDescription = findViewById(R.id.edit_create_description);
        CompleteButton = findViewById(R.id.btn_create_complete);
        CloseButton = findViewById(R.id.btn_create_close);
        PlaceButton = findViewById(R.id.btn_creat_place);
    }


    //StudyCreateActivity의 main method
    private void completeCreate() {
        String studyname = studyName.getText().toString();
        String studydescription = studyDescription.getText().toString();
        Integer studymemnum = Integer.parseInt(studyMemNum.getText().toString());

        //Dto 객체 생성하고 startStudyCreate 실행
        startStudyCreate(new StudyCreateReqDto(studyname,studyInterest,studyFrequency,studymemnum,studyLongitude,studyLatitude,studydescription));// Req객체 생성

        Intent intent = new Intent(StudyCreateActivity.this, StudyListActivity.class);
        startActivity(intent);
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
}
