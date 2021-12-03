package com.cos.Agora.study;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.Agora.CMRespDto;
import com.cos.Agora.R;
import com.cos.Agora.global.User;
import com.cos.Agora.main.MainActivity;
import com.cos.Agora.study.adapter.StudyListAdapter;
import com.cos.Agora.study.alarm.AlarmActivity;
import com.cos.Agora.study.model.StudyListRespDto;
import com.cos.Agora.retrofitURL;
import com.cos.Agora.study.mystudy.MyStudyListActivity;
import com.cos.Agora.study.profile.ProfileActivity;
import com.cos.Agora.study.service.StudyService;
import com.cos.Agora.study.studycreate.StudyCreateActivity;
import com.cos.Agora.study.studydetail.DetailActivity;
import com.cos.Agora.study.studyevaluate.EvalListActivity;
import com.cos.Agora.study.studyevaluate.MannerEvalActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudyListActivity extends AppCompatActivity {
    private static final String TAG = "StudyList";
    private MainActivity activity;
    private RecyclerView studyList;
    private StudyListAdapter studylistAdapter;
    private Spinner spinner_interest;
    private Spinner spinner_lineup;
    private ImageButton ibstudylist, ibalarm, ibmystudy, ibassess, ibprofile;
    private String interest;        // 관심분야
    private String lineup;          // 정렬방법
    private String phoneNumber;     // 사용자 핸드폰
//    private ArrayList<StudyListRespDto> studyRespDtos = new ArrayList<>();

    private static double Ulatitude;
    private static double Ulongitude;

    private StudyService studyListService= retrofitURL.retrofit.create(StudyService.class);
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
        //정렬 부분
        spinner_lineup = findViewById(R.id.spinner_lineup);

        ArrayAdapter<String> adapter_lineup = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lineupList);
        adapter_lineup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_lineup.setAdapter(adapter_lineup);
        spinner_lineup.setSelection(0);

        //필터링 선택시
        spinner_interest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                interest = spinner_interest.getSelectedItem().toString();
                getStudyList(phoneNumber, interest, lineup);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //정렬 선택시
        spinner_lineup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lineup = spinner_lineup.getSelectedItem().toString();
                getStudyList(phoneNumber, interest, lineup);
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
            Intent intent = new Intent(StudyListActivity.this, StudyCreateActivity.class);
            startActivity(intent);     // intent 타입을 넣어야함  !!
            //StudyListActivity.this.finish(); //생성하고 돌아왔을때도 스터디 목록은 그대로여야하니까 finish하면 안됨
        });

        getStudyList(phoneNumber, interest, lineup);
    }

    public void init() {
        CreateStudyBtn = findViewById(R.id.btn_study_create);

        ibstudylist = findViewById(R.id.ib_studylist);
        ibalarm = findViewById(R.id.ib_alarm);
        ibmystudy = findViewById(R.id.ib_mystudy);
        ibassess = findViewById(R.id.ib_assess);
        ibprofile = findViewById(R.id.ib_profile);

        ibstudylist.setOnClickListener(v -> {
            Intent intent1 = new Intent(StudyListActivity.this, StudyListActivity.class);
            startActivity(intent1);
        });

        // 알람 레이아웃 연결시킬 것
        ibalarm.setOnClickListener(v -> {
            Intent intent1 = new Intent(StudyListActivity.this, AlarmActivity.class);
            startActivity(intent1);
        });

        // 내 스터디 리스트를 보여줄 수 있는 액티비티를 만들어야 한다.
        ibmystudy.setOnClickListener(v -> {
            Intent intent1 = new Intent(StudyListActivity.this, MyStudyListActivity.class);
            startActivity(intent1);
        });

        ibassess.setOnClickListener(v -> {
            Intent intent1 = new Intent(StudyListActivity.this, EvalListActivity.class);
            startActivity(intent1);
            StudyListActivity.this.finish();
        });

        // 프로필 레이아웃 연결시킬 것
        ibprofile.setOnClickListener(v -> {
            Intent intent1 = new Intent(StudyListActivity.this, ProfileActivity.class);
            startActivity(intent1);
        });

        phoneNumber = ((User)getApplication()).getPhoneNumber();
    }

    public void getStudyList(String phoneNumber,String interest, String lineup){
        Ulatitude = ((User)getApplication()).getLatitude();  // 사용자 위도
        Ulongitude = ((User)getApplication()).getLongitude();// 사용자 경도

        Call<CMRespDto<List<StudyListRespDto>>> call = studyListService.getStudyList(Ulatitude,Ulongitude,interest,lineup);

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