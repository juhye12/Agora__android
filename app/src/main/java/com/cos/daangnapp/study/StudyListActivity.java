package com.cos.daangnapp.study;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.R;
import com.cos.daangnapp.location.model.LocationReqDto;
import com.cos.daangnapp.main.MainActivity;
import com.cos.daangnapp.study.adapter.StudyListAdapter;
import com.cos.daangnapp.study.model.StudyRespDto;
import com.cos.daangnapp.retrofitURL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudyListActivity extends AppCompatActivity {
    private static final String TAG = "StudyList";
    private  MainActivity activity;
    private RecyclerView studyList;
    private StudyListAdapter studylistAdapter;
    private Spinner spinner_interest;
    private Spinner spinner_lineup;
    private String interest;
    private String lineup;
    private String phoneNumber;
    private LocationReqDto locationReqDto;
//    private Spinner spinner;
    private ArrayList<StudyRespDto> studyRespDtos = new ArrayList<>();
    private retrofitURL retrofitURL;
    //private ImageView btnSearch,IvSearch,IvFilter,IvNotice;
    private EditText etSearch;
    private StudyListService studyListService= retrofitURL.retrofit.create(StudyListService.class);
    private Button CreateStudyBtn;


    String[] interestList = {"전체","어학","프로그래밍","게임","취직","주식","운동","와인","여행"};
    String[] lineupList = {"최신순","거리순","추천순"};

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studylist);

        init();

        //interest별 필터링
        spinner_interest = findViewById(R.id.spinner_interest);
        ArrayAdapter<String> adapter_interest = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, interestList);
        adapter_interest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_interest.setAdapter(adapter_interest);
        spinner_interest.setSelection(0);

        spinner_interest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                interest = spinner_interest.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //정렬 부분
        spinner_lineup = findViewById(R.id.spinner_lineup);

        ArrayAdapter<String> adapter_lineup = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lineupList);
        adapter_lineup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_lineup.setAdapter(adapter_lineup);
        spinner_lineup.setSelection(0);

        spinner_lineup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lineup = spinner_lineup.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        studyList = findViewById(R.id.rv_studylist);
        LinearLayoutManager manager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        studyList.setLayoutManager(manager);

        //스터디 생성 버튼 클릭 시
        CreateStudyBtn.setOnClickListener(v -> {
                    Intent intent = new Intent(StudyListActivity.this, CreateStudyActivity.class);
                    //다음 activity로 값 넘기기 위함
                    intent.putExtra("phoneNumber", phoneNumber);
                    intent.putExtra("location", (Parcelable) locationReqDto);

                    startActivity(intent);     // intent 타입을 넣어야함  !!
                    StudyListActivity.this.finish();
                });

        getStudies(phoneNumber,interest, lineup);

    }
    public void init() {
        CreateStudyBtn = findViewById(R.id.btn_study_create);
        //이전 Activity에서 phoneNumber값 받아오기. 정렬에서 거리 계산을 위해 현재 사용자가 어떤 사용자인지 알아야해서!
        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");
        locationReqDto = intent.getParcelableExtra("location");
    }

    public void getStudies(String phoneNumber,String interest, String lineup){
        Call<CMRespDto<List<StudyRespDto>>> call = studyListService.getstudies(phoneNumber,interest,lineup);
        call.enqueue(new Callback<CMRespDto<List<StudyRespDto>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<StudyRespDto>>> call, Response<CMRespDto<List<StudyRespDto>>> response) {
                try {
                    CMRespDto<List<StudyRespDto>> cmRespDto = response.body();
                    List<StudyRespDto> studies = cmRespDto.getData();
                    Log.d(TAG, "studies: " +studies);
                    studylistAdapter = new StudyListAdapter(studies,activity);
                    studyList.setAdapter(studylistAdapter);
                } catch (Exception e) {
                    Log.d(TAG, "null");
                }
            }
            @Override
            public void onFailure(Call<CMRespDto<List<StudyRespDto>>> call, Throwable t) {
                Log.d(TAG, "onFailure: 게시물목록보기 실패 !!");
            }
        });
    }
}
