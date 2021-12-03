package com.cos.Agora.study.mystudy;

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
import com.cos.Agora.study.adapter.MyStudyListAdapter;
import com.cos.Agora.study.model.EvalRespDto;
import com.cos.Agora.study.model.MyStudyListRespDto;
import com.cos.Agora.study.model.StudyListRespDto;
import com.cos.Agora.study.service.EvalService;
import com.cos.Agora.study.service.StudyService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyStudyListActivity extends AppCompatActivity {
    private static final String TAG = "MyStudyListActivity";
    private MainActivity activity;
    private RecyclerView mystudyList;
    private MyStudyListAdapter mystudylistAdapter;
    private int userId;
    private static double Ulatitude;
    private static double Ulongitude;

    private ArrayList<StudyListRespDto> mystudyListRespDto = new ArrayList<>();

    private StudyService mystudyService= retrofitURL.retrofit.create(StudyService.class);

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystudylist);

        userId = ((User)getApplication()).getUserId();

        mystudyList = findViewById(R.id.rv_mystudylist);
        LinearLayoutManager manager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        mystudyList.setLayoutManager(manager);

        getMyStudyList(userId);
    }

    public void getMyStudyList(int userId){
        Ulatitude = ((User)getApplication()).getLatitude();  // 사용자 위도
        Ulongitude = ((User)getApplication()).getLongitude();// 사용자 경도

        Call<CMRespDto<List<StudyListRespDto>>> call = mystudyService.getMyStudyList(Ulatitude,Ulongitude,userId);

        call.enqueue(new Callback<CMRespDto<List<StudyListRespDto>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<StudyListRespDto>>> call, Response<CMRespDto<List<StudyListRespDto>>> response) {
                try {
                    CMRespDto<List<StudyListRespDto>> cmRespDto = response.body();
                    List<StudyListRespDto> mystudies = cmRespDto.getData();
                    Log.d(TAG, "mystudies: "+ mystudies);
                    mystudylistAdapter = new MyStudyListAdapter(mystudies,activity,Ulatitude,Ulongitude);
                    mystudyList.setAdapter(mystudylistAdapter);
                } catch (Exception e) {
                    Log.d(TAG, "null");
                }
            }
            @Override
            public void onFailure(Call<CMRespDto<List<StudyListRespDto>>> call, Throwable t) {
                Log.d(TAG, "onFailure: 평가목록보기 실패 !!");
            }
        });
    }
}