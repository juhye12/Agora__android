package com.cos.Agora.study.studycreate;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cos.Agora.R;
import com.cos.Agora.study.StudyListActivity;

public class StudyCreateActivity extends AppCompatActivity {

    private static final String TAG = "CreateStudyActivity";
    private EditText studyName;
    private Spinner s_studyInterest;
    private Spinner s_studyFrequency;

    private String studyInterest;
    private int studyFrequency;
    private EditText studyMemNum;
    private EditText studyDescription;
    private Button CloseButton;
    private Button PlaceButton;

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
                intent.putExtra("studyName",studyName.getText().toString());
                intent.putExtra("studyInterest",studyInterest);
                intent.putExtra("studyFrequency",studyFrequency);
                intent.putExtra("studyMemNum",studyMemNum.getText().toString());
                intent.putExtra("studyDescription",studyDescription.getText().toString());
                startActivity(intent);
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
        CloseButton = findViewById(R.id.btn_create_close);

        PlaceButton = findViewById(R.id.btn_set_place); //지우기 금지
    }

}
