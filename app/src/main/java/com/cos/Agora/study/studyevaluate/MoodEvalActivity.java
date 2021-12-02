package com.cos.Agora.study.studyevaluate;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.Agora.CMRespDto;
import com.cos.Agora.R;
import com.cos.Agora.global.User;
import com.cos.Agora.login.model.JoinRespDto;
import com.cos.Agora.main.MainActivity;
import com.cos.Agora.retrofitURL;
import com.cos.Agora.study.StudyListActivity;
import com.cos.Agora.study.adapter.EvalAdapter;
import com.cos.Agora.study.model.EvalRespDto;
import com.cos.Agora.study.service.EvalService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoodEvalActivity extends AppCompatActivity {
    private static final String TAG = "MoodEvalActivity";

    private TextView interest;
    private TextView studyTitle;
    private int studyId;

    private CheckBox check1;
    private CheckBox check2;
    private CheckBox check3;
    private CheckBox check4;
    private CheckBox check5;

    private Button summitButton;

    private EvalService evalService= retrofitURL.retrofit.create(EvalService.class);

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moodeval);

        init();

        summitButton.setOnClickListener(v -> {

            //mood 점수 보내기
            sendMoodAvg(studyId);

            Intent intent = new Intent(MoodEvalActivity.this, EvalListActivity.class);
            startActivity(intent);
            MoodEvalActivity.this.finish();
        });

    }

    public void init(){
        interest = findViewById(R.id.text_interest);
        studyTitle = findViewById(R.id.text_studyTitle);
        check1 = findViewById(R.id.check1);
        check2 = findViewById(R.id.check2);
        check3 = findViewById(R.id.check3);
        check4 = findViewById(R.id.check4);
        check5 = findViewById(R.id.check5);

        summitButton = findViewById(R.id.summit_mood);

        //이전 Activity에서 값 넘겨 받고, 화면에 나타내기
        Intent intent = getIntent();
        String interest1 = intent.getStringExtra("interest");
        interest.setText(interest1);

        //스터디 별 평가 점수 보내기 위해 필요
        studyId = intent.getIntExtra("studyId",0);

        String studyTitle1 = intent.getStringExtra("title");
        studyTitle.setText(studyTitle1);
    }

    //MoodAvg를 보내는 함수
    public void sendMoodAvg(int studyId){
        double moodAvg;
        moodAvg = calMoodAvg();
        int userId = ((User)getApplication()).getUserId();

        Call<CMRespDto<EvalRespDto>> call = evalService.moodEval(studyId,userId,moodAvg);
        call.enqueue(new Callback<CMRespDto<EvalRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<EvalRespDto>> call, Response<CMRespDto<EvalRespDto>> response) {
                CMRespDto<EvalRespDto> cmRespDto = response.body();
                EvalRespDto EvalRespDto = cmRespDto.getData();
                Log.d(TAG, "onResponse: 평가 완료");
            }
            @Override
            public void onFailure(Call<CMRespDto<EvalRespDto>> call, Throwable t) {
                Log.d(TAG, "평가 에러 발생");
            }
        });
    }

    //moodAvg계산하는 함수
    public double calMoodAvg(){
        int totalScore = 0;
        double moodAvg;

        if(check1.isChecked())
        {
            totalScore=+5;
        }
        if(check2.isChecked())
        {
            totalScore=+5;
        }
        if(check3.isChecked())
        {
            totalScore=+5;
        }
        if(check4.isChecked())
        {
            totalScore=+5;
        }
        if(check5.isChecked())
        {
            totalScore=+5;
        }

        //나눗셈 오류 처리 위해
        if(totalScore == 0)
        {
            moodAvg = 0;
        }
        else
        {
            moodAvg = totalScore/5;
        }

        return moodAvg;
    }

}