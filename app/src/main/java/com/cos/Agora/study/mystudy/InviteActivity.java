package com.cos.Agora.study.mystudy;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.Agora.CMRespDto;
import com.cos.Agora.R;
import com.cos.Agora.global.User;
import com.cos.Agora.main.MainActivity;
import com.cos.Agora.calendar.CalendarActivity;
import com.cos.Agora.study.StudyApplicationActivity;
import com.cos.Agora.study.adapter.DetailAdapter;
import com.cos.Agora.study.model.ApplicationRespDto;
import com.cos.Agora.study.model.DetailRespDto;
import com.cos.Agora.study.model.EvalRespDto;
import com.cos.Agora.study.model.StudyListRespDto;
import com.cos.Agora.retrofitURL;
import com.cos.Agora.study.service.StudyService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InviteActivity extends AppCompatActivity{
    private static final String TAG = "InviteActivity";
    private Button btn_study_invite2, btn_study_noinvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        btn_study_invite2 = findViewById(R.id.btn_study_invite2);
        btn_study_noinvite = findViewById(R.id.btn_study_noinvite);

        btn_study_invite2.setOnClickListener(view -> {
            Toast inviteToast = Toast.makeText(this.getApplicationContext(), "초대 메세지를 보냈습니다.",
                    Toast.LENGTH_SHORT);
            inviteToast.show();
            btn_study_invite2.setVisibility(View.INVISIBLE);
            btn_study_noinvite.setVisibility(View.VISIBLE);
        });
    }
}
