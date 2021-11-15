package com.cos.daangnapp.login;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.R;
import com.cos.daangnapp.location.model.LocationReqDto;
import com.cos.daangnapp.login.model.JoinReqDto;
import com.cos.daangnapp.login.model.JoinRespDto;
import com.cos.daangnapp.login.network.JoinService;
import com.cos.daangnapp.study.StudyListActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    private static final String TAG = "JoinActivity";
    private EditText mAssociation;
    private EditText mAge;
    private EditText mSex;
    private EditText mInterest;
    private Button mJoinButton;
    private String phoneNumber; // 이전 activity에서 작성된 phoneNumber를 가져옴
    private LocationReqDto locationReqDto;

    private com.cos.daangnapp.retrofitURL retrofitURL;
    private JoinService joinService = retrofitURL.retrofit.create(JoinService .class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        init();

//        mAssociation = (EditText) findViewById(R.id.join_association);
//        mAge = (EditText) findViewById(R.id.join_age);
//        mSex = (EditText) findViewById(R.id.join_sex);
//        mInterest = (EditText) findViewById(R.id.join_interest);
//        mJoinButton = (Button) findViewById(R.id.join_button);

        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptJoin();
            }
        });
    }

    public void init(){
        mAssociation = findViewById(R.id.join_association);
        mAge= findViewById(R.id.join_age);
        mSex = findViewById(R.id.join_sex);
        mInterest = findViewById(R.id.join_interest);
        mJoinButton = findViewById(R.id.join_button);

        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("phoneNumber");
        locationReqDto = intent.getParcelableExtra("location");
    }

    //JoinActivity의 main method
    private void attemptJoin() { // 입력된 데이터를 저장하고 MainActivity로 넘겨야 함
        String association = mAssociation.getText().toString();
        String age = mAge.getText().toString();
        String sex = mSex.getText().toString();
        String interest = mInterest.getText().toString();

        startJoin(new JoinReqDto(phoneNumber,association,age,sex,interest,locationReqDto));// Req객체 생성
        
        Intent intent = new Intent(JoinActivity.this, StudyListActivity.class);
        intent.putExtra("phoneNumber",phoneNumber);
        intent.putExtra("location", (Parcelable) locationReqDto);
        startActivity(intent);
        JoinActivity.this.finish();

//        boolean cancel = false;
//        View focusView = null;
//
////        if (cancel) {
////            focusView.requestFocus();
////        } else {
////            startJoin(new JoinReqDto(phoneNumber,association,age,sex,interest));
////
////        }
    }

    private void startJoin(JoinReqDto joinReqDto) {
        Call<CMRespDto<JoinRespDto>> call = joinService.userJoin(joinReqDto);
        call.enqueue(new Callback<CMRespDto<JoinRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<JoinRespDto>> call, Response<CMRespDto<JoinRespDto>> response) {
                CMRespDto<JoinRespDto> cmRespDto = response.body();
                JoinRespDto joinRespDto = cmRespDto.getData();
                Log.d(TAG, "onResponse: save성공!!!");
                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("phoneNumber",joinRespDto.getPhoneNumber());
                editor.putString("association",joinRespDto.getAssociation());
                editor.putString("age",joinRespDto.getAge());
                editor.putString("sex",joinRespDto.getSex());
                editor.putString("interest",joinRespDto.getInterest());
                editor.commit();
            }
            @Override
            public void onFailure(Call<CMRespDto<JoinRespDto>> call, Throwable t) {
//                Toast.makeText(JoinActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "회원가입 에러 발생");
//                Log.e("회원가입 에러 발생", t.getMessage());
            }
        });
    }
}
