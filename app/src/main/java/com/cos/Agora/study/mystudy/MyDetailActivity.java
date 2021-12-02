package com.cos.Agora.study.mystudy;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.Agora.CMRespDto;
import com.cos.Agora.R;
import com.cos.Agora.main.MainActivity;
import com.cos.Agora.calendar.CalendarActivity;
import com.cos.Agora.study.StudyApplicationActivity;
import com.cos.Agora.study.StudyListActivity;
import com.cos.Agora.study.adapter.DetailAdapter;
import com.cos.Agora.study.model.DetailRespDto;
import com.cos.Agora.study.model.StudyListRespDto;
import com.cos.Agora.retrofitURL;
import com.cos.Agora.study.service.StudyService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDetailActivity extends AppCompatActivity {

    private Context mContext;

    private static final String TAG = "MyDetailActivity";

    private ArrayList<String> mImageList;
    private RecyclerView studyDetail;
    private DetailAdapter detailAdapter;
    private ImageButton mBack;
    private TextView tvTitle, tvCategori, tvDescrip;
    private TextView tvCurrent, tvLimit, tvCreateDate;
    private ImageButton ivCalendar, ivNotification, ivPlace;
    private int id; // studyId
    private MainActivity activity;
    private FloatingActionButton chatFab;

    private ArrayList<StudyListRespDto> studyRespDtos = new ArrayList<>();

    private retrofitURL retrofitURL;
    private StudyService studyListService= retrofitURL.retrofit.create(StudyService .class);

    private Button btn_study_invite, btn_study_invite2, btn_study_noinvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystudydetail);

        init(); // data를 server로 보냄
        initSetting(); // id = 2

    }

    public  void init(){
        chatFab = findViewById(R.id.chat_fab_btn);
        tvTitle = findViewById(R.id.my_detail_title); // 스터디 제목
        tvCategori = findViewById(R.id.my_detail_categories); // 관심분야
        tvCurrent = findViewById(R.id.my_detail_current);
        tvLimit = findViewById(R.id.my_detail_limit);
        tvCreateDate = findViewById(R.id.my_detail_createDate);

        btn_study_invite = findViewById(R.id.btn_study_invite); // 초대하기
        btn_study_invite2 = findViewById(R.id.btn_study_invite2);
        btn_study_noinvite = findViewById(R.id.btn_study_noinvite);


        ivCalendar = findViewById(R.id.my_iv_calendar); // 일정관리 -> 근데 해당 스터디 안의 일정을 알기가 과정이 쉽지 않을듯
        ivNotification = findViewById(R.id.my_iv_notification); // 공지
        ivPlace = findViewById(R.id.my_iv_place); // 장소

        tvDescrip = findViewById(R.id.my_detail_description); // 상세정보

        studyDetail = findViewById(R.id.rv_study_detail); // 사용자 리사이클러 뷰
        mBack = findViewById(R.id.product_information_iv_back);

        // 채팅 플로팅 버튼을 눌렀을 때의 동작
        chatFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 일단 채팅 액티비티를 안만들었으므로 studylist로 연결시켜 놓았다.
                Intent intent = new Intent(MyDetailActivity.this, StudyListActivity.class);
            }
        });



        btn_study_invite.setOnClickListener(v -> {
            // 초대하기 버튼을 눌렀을 때 framelayout을 사용하여 사용자는 단순히 textview로 나타내고 '초대'버튼을 누르면
            // 회색 빛으로 바뀌면서 토스트창 뜨는 것으로 만들 것
            LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_invite, null);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            linearLayout.setBackgroundColor(Color.parseColor("#99000000"));

            LinearLayout.LayoutParams parent = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
            addContentView(linearLayout, parent);

            btn_study_invite2.setOnClickListener(view -> {
                Toast inviteToast = Toast.makeText(this.getApplicationContext(), "초대 메세지를 보냈습니다.",
                        Toast.LENGTH_SHORT);
                inviteToast.show();
                btn_study_invite2.setVisibility(View.INVISIBLE);
                btn_study_noinvite.setVisibility(View.VISIBLE);
            });
        });

        // 일정 관리 이미지 버튼을 눌렀을 때 일정 관리 레이아웃으로 넘어간다.
        ivCalendar.setOnClickListener(v -> {
            Intent intent2 = new Intent(MyDetailActivity.this, CalendarActivity.class);
            startActivity(intent2);
        });
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

        getMyStudyDetail(id);

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


    public void getMyStudyDetail(int Id){
        Call<CMRespDto<List<DetailRespDto>>> call = studyListService.getStudyDetail(Id);

        call.enqueue(new Callback<CMRespDto<List<DetailRespDto>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<DetailRespDto>>> call, Response<CMRespDto<List<DetailRespDto>>> response) {
                try {
                    CMRespDto<List<DetailRespDto>> cmRespDto = response.body();
                    List<DetailRespDto> mydetails = cmRespDto.getData();
                    Log.d(TAG, "mydetails: " + mydetails);
                    detailAdapter = new DetailAdapter(mydetails, activity);
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
