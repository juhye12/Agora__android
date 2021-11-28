package com.cos.Agora.study.studyevaluate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.Agora.CMRespDto;
import com.cos.Agora.R;
import com.cos.Agora.global.User;
import com.cos.Agora.main.MainActivity;
import com.cos.Agora.retrofitURL;
import com.cos.Agora.study.StudyListActivity;
import com.cos.Agora.study.adapter.EvalAdapter;
import com.cos.Agora.study.model.EvalRespDto;
import com.cos.Agora.study.service.EvalService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EvalListActivity extends AppCompatActivity {
    private static final String TAG = "EvalListActivity";
    private MainActivity activity;
    private RecyclerView evalList;
    private EvalAdapter evalAdapter;
    private int userId;
    private String evalType;
    private ImageButton backBtn;

    private ArrayList<EvalRespDto> evaleRespDtos = new ArrayList<>();

    private EvalService evalService= retrofitURL.retrofit.create(EvalService.class);

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evallist);

        userId = ((User)getApplication()).getUserId();
        //뒤로가기 버튼
        backBtn = findViewById(R.id.btn_back);
        //뒤로가기 버튼을 누를경우 studylist화면으로 돌아감
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(EvalListActivity.this, StudyListActivity.class);
            startActivity(intent);
            EvalListActivity.this.finish();
        });

        evalList = findViewById(R.id.rv_evaluatelist);
        LinearLayoutManager manager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        evalList.setLayoutManager(manager);

        getEvalList(userId);
    }

    public void getEvalList(int userId){

        Call<CMRespDto<List<EvalRespDto>>> call = evalService.getEvalList(userId);

        call.enqueue(new Callback<CMRespDto<List<EvalRespDto>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<EvalRespDto>>> call, Response<CMRespDto<List<EvalRespDto>>> response) {
                try {
                    CMRespDto<List<EvalRespDto>> cmRespDto = response.body();
                    List<EvalRespDto> evaluations = cmRespDto.getData();
                    Log.d(TAG, "evaluations: "+evaluations);
                    evalAdapter = new EvalAdapter(evaluations,activity);
                    evalList.setAdapter(evalAdapter);
                } catch (Exception e) {
                    Log.d(TAG, "null");
                }
            }
            @Override
            public void onFailure(Call<CMRespDto<List<EvalRespDto>>> call, Throwable t) {
                Log.d(TAG, "onFailure: 평가목록보기 실패 !!");
            }
        });
    }
}