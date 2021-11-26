package com.cos.Agora.study.studydetail;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.Agora.CMRespDto;
import com.cos.Agora.R;
import com.cos.Agora.main.MainActivity;
import com.cos.Agora.calendar.CalendarActivity;
import com.cos.Agora.study.StudyApplicationActivity;
import com.cos.Agora.study.adapter.DetailAdapter;
import com.cos.Agora.study.model.DetailRespDto;
import com.cos.Agora.study.model.StudyListRespDto;
import com.cos.Agora.retrofitURL;
import com.cos.Agora.study.service.StudyService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private Context mContext;

    private static final String TAG = "DetailActivity";

    private ArrayList<String> mImageList;
    private RecyclerView studyDetail;
    private DetailAdapter detailAdapter;
    private ImageButton mBack;
    private TextView tvTitle, tvCategori, tvDescrip;
    private TextView tvCurrent, tvLimit, tvCreateDate;
    private ImageButton ivCalendar, ivNotification, ivPlace;
    private int id; // studyId
    private MainActivity activity;

    private ArrayList<StudyListRespDto> studyRespDtos = new ArrayList<>();

    private retrofitURL retrofitURL;
    private StudyService studyListService= retrofitURL.retrofit.create(StudyService .class);

    private Button btn_study_join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init(); // data를 server로 보냄
        initSetting(); // id = 2

    }

    public  void init(){
        // 레이아웃에서 주석 처리한 코드들 DetailActivity에서 변수들 주석처리함 11.12
        tvTitle = findViewById(R.id.detail_title); // 스터디 제목
        tvCategori = findViewById(R.id.detail_categories); // 관심분야
        tvCurrent = findViewById(R.id.detail_current);
        tvLimit = findViewById(R.id.detail_limit);
        tvCreateDate = findViewById(R.id.detail_createDate);

        btn_study_join = findViewById(R.id.btn_study_join); // 가입신청


        ivCalendar = findViewById(R.id.iv_calendar); // 일정관리 -> 근데 해당 스터디 안의 일정을 알기가 과정이 쉽지 않을듯
        ivNotification = findViewById(R.id.iv_notification); // 공지
        ivPlace = findViewById(R.id.iv_place); // 장소

        tvDescrip = findViewById(R.id.detail_description); // 상세정보

        studyDetail = findViewById(R.id.rv_study_detail); // 사용자 리사이클러 뷰
        tvCurrent = findViewById(R.id.detail_current);
        tvLimit = findViewById(R.id.detail_limit);

        mBack = findViewById(R.id.product_information_iv_back);

        // 가입 신청 버튼을 눌렀을 때 가입 신청 레이아웃으로 넘어간다.

        btn_study_join.setOnClickListener(v -> {
            Intent intent1 = new Intent(DetailActivity.this, StudyApplicationActivity.class);
            startActivity(intent1);
        });


        // 일정 관리 이미지 버튼을 눌렀을 때 일정 관리 레이아웃으로 넘어간다.

        ivCalendar.setOnClickListener(v -> {
            Intent intent2 = new Intent(DetailActivity.this, CalendarActivity.class);
            startActivity(intent2);
        });

        // 게시물을 클릭했을 때 해당 스터디 아이디를 서버로 보내서 그곳에 속해있는 유저 아이디 및 다른 정보들을
        // 가져오게 해야 하는데 이게 맞나

    }

    public void initSetting(){

        Intent intent = getIntent();
        id = intent.getIntExtra("studyId", 1); // id를 받아야 한다. // default value로 들어옴
        String Title = intent.getStringExtra("studyTitle");
        String Category = intent.getStringExtra("studyInterest");

        int Current = intent.getIntExtra("studyCurrent", 1);
        int Limit = intent.getIntExtra("studyLimit", 1);

        tvTitle.setText(Title);
        tvCategori.setText(Category);
        tvCreateDate.setText(intent.getStringExtra("studyCreateDate"));
        tvCurrent.setText(String.valueOf(Current));
        tvLimit.setText(String.valueOf(Limit));

        // 뒤로 가기
        mBack.setImageResource(R.drawable.home_as_up);
        mBack.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);

        studyDetail = findViewById(R.id.rv_study_detail);
        LinearLayoutManager manager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        studyDetail.setLayoutManager(manager);

        for (int i = 0; i < Current; i++){
            getStudyDetail(id);
        }
    }

    // 뒤로 가기 행동할 때의 함수
    public void productInformationOnClick(View view) {
        switch (view.getId()) {
            case R.id.product_information_iv_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }


    public void getStudyDetail(int Id){
        Call<CMRespDto<List<DetailRespDto>>> call = studyListService.getStudyDetail(Id);

        call.enqueue(new Callback<CMRespDto<List<DetailRespDto>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<DetailRespDto>>> call, Response<CMRespDto<List<DetailRespDto>>> response) {
                try {
                    CMRespDto<List<DetailRespDto>> cmRespDto = response.body();
                    List<DetailRespDto> details = cmRespDto.getData();
                    Log.d(TAG, "details: " + details);
                    detailAdapter = new DetailAdapter(details, activity);
                    studyDetail.setAdapter(detailAdapter);
                } catch (Exception e) {
                    Log.d(TAG, "null");
                }
            }
            @Override
            public void onFailure(Call<CMRespDto<List<DetailRespDto>>> call, Throwable t) {
                Log.d(TAG, "onFailure: 스터디 그룹원 정보 보기 실패 !!");
            }
        });
    }



    // menu
    public void removePost(int Id){
        Call<CMRespDto<List<StudyListRespDto>>> call = studyListService.removePost(Id);
        call.enqueue(new Callback<CMRespDto<List<StudyListRespDto>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<StudyListRespDto>>> call, Response<CMRespDto<List<StudyListRespDto>>> response) {
                Log.d(TAG, "onResponse: 삭제성공");
            }
            @Override
            public void onFailure(Call<CMRespDto<List<StudyListRespDto>>> call, Throwable t) {
                Log.d(TAG, "onFailure: 삭제실패");
            }
        });
    }

}
