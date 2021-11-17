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
import com.cos.daangnapp.global.User;
import com.cos.daangnapp.location.model.LocationReqDto;
import com.cos.daangnapp.main.MainActivity;
import com.cos.daangnapp.study.adapter.StudyListAdapter;
import com.cos.daangnapp.study.model.StudyListRespDto;
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
    private String interest;        // 관심분야
    private String lineup;          // 정렬방법
    private String phoneNumber;     // 사용자 핸드폰
//    private LocationReqDto locationReqDto;
//    private Spinner spinner;
    private ArrayList<StudyListRespDto> studyRespDtos = new ArrayList<>();
    private retrofitURL retrofitURL;
    //private ImageView btnSearch,IvSearch,IvFilter,IvNotice;

    private static double Ulatitude;
    private static double Ulongitude;

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
                    startActivity(intent);     // intent 타입을 넣어야함  !!
                    StudyListActivity.this.finish();
                });

        getStudyList(phoneNumber, interest, lineup);

    }
    public void init() {
        CreateStudyBtn = findViewById(R.id.btn_study_create);
        //이전 Activity에서 phoneNumber값 받아오기. 정렬에서 거리 계산을 위해 현재 사용자가 어떤 사용자인지 알아야해서!
        Intent intent = getIntent();
//        phoneNumber = intent.getStringExtra("phoneNumber");
//        locationReqDto = intent.getParcelableExtra("location");

        phoneNumber = ((User)getApplication()).getPhoneNumber();
    }

    public void getStudyList(String phoneNumber,String interest, String lineup){
        Call<CMRespDto<List<StudyListRespDto>>> call = studyListService.getStudyList(phoneNumber,interest,lineup);

        Ulatitude = ((User)getApplication()).getLatitude();  // 사용자 위도
        Ulongitude = ((User)getApplication()).getLongitude();// 사용자 경도

        call.enqueue(new Callback<CMRespDto<List<StudyListRespDto>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<StudyListRespDto>>> call, Response<CMRespDto<List<StudyListRespDto>>> response) {
                try {
                    CMRespDto<List<StudyListRespDto>> cmRespDto = response.body();
                    List<StudyListRespDto> studies = cmRespDto.getData();
                    Log.d(TAG, "studies: " +studies);
                    studylistAdapter = new StudyListAdapter(studies,activity,Ulatitude,Ulongitude); // 사용자의 위도,경도
                    studyList.setAdapter(studylistAdapter);
                } catch (Exception e) {
                    Log.d(TAG, "null");
                }
            }
            @Override
            public void onFailure(Call<CMRespDto<List<StudyListRespDto>>> call, Throwable t) {
                Log.d(TAG, "onFailure: 게시물목록보기 실패 !!");
            }
        });
    }
}
