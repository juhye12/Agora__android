package com.cos.daangnapp.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.R;
import com.cos.daangnapp.login.model.JoinReqDto;
import com.cos.daangnapp.login.model.JoinRespDto;
import com.cos.daangnapp.login.model.UserRespDto;
import com.cos.daangnapp.login.network.RetrofitClient;
import com.cos.daangnapp.login.network.JoinService;
import com.cos.daangnapp.login.service.UserService;
import com.cos.daangnapp.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    private static final String TAG = "JoinActivity";
    private EditText mAssociation;
    private EditText mAge;
    private EditText mSex;
    private EditText mFavorite;
    private Button mJoinButton;
    private com.cos.daangnapp.retrofitURL retrofitURL;
    private JoinService joinService = retrofitURL.retrofit.create(JoinService .class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mAssociation = (EditText) findViewById(R.id.join_association);
        mAge = (EditText) findViewById(R.id.join_age);
        mSex = (EditText) findViewById(R.id.join_sex);
        mFavorite = (EditText) findViewById(R.id.join_favorite);
        mJoinButton = (Button) findViewById(R.id.join_button);

        mJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptJoin();
            }
        });
    }

    private void attemptJoin() {

        String association = mAssociation.getText().toString();
        String age = mAge.getText().toString();
        String sex = mSex.getText().toString();
        String interest = mFavorite.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (cancel) {
            focusView.requestFocus();
        } else {
            startJoin(new JoinReqDto(association,age,sex,interest));
        }
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
                editor.putString("association",joinRespDto.getAssociation());
                editor.putString("age",joinRespDto.getAge());
                editor.putString("sex",joinRespDto.getSex());
                editor.putString("interest",joinRespDto.getInterest());
                editor.commit();
            }

            @Override
            public void onFailure(Call<CMRespDto<JoinRespDto>> call, Throwable t) {
                Toast.makeText(JoinActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
            }
        });
    }


}
