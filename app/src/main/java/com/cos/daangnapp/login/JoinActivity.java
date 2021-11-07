package com.cos.daangnapp.login;

import android.content.Intent;
import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cos.daangnapp.R;
import com.cos.daangnapp.login.model.JoinData;
import com.cos.daangnapp.login.model.JoinResponse;
import com.cos.daangnapp.login.network.RetrofitClient;
import com.cos.daangnapp.login.network.ServiceApi;
import com.cos.daangnapp.main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    private EditText mAssociation;
    private EditText mAge;
    private EditText mSex;
    private EditText mFavorite;
    private Button mJoinButton;
    private ServiceApi service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mAssociation = (EditText) findViewById(R.id.join_association);
        mAge = (EditText) findViewById(R.id.join_age);
        mSex = (EditText) findViewById(R.id.join_sex);
        mFavorite = (EditText) findViewById(R.id.join_favorite);
        mJoinButton = (Button) findViewById(R.id.join_button);

        service = RetrofitClient.getClient().create(ServiceApi.class);

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
        String favorite = mFavorite.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (cancel) {
            focusView.requestFocus();
        } else {
            startJoin(new JoinData(association,age,sex,favorite));
        }
    }

    private void startJoin(JoinData data) {
        service.userJoin(data).enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                JoinResponse result = response.body();
                Toast.makeText(JoinActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                JoinActivity.this.finish();
               // if (result.getCode() == 200) {
               //     finish();
               // }
            }
            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                Toast.makeText(JoinActivity.this, "회원가입 에러 발생", Toast.LENGTH_SHORT).show();
                Log.e("회원가입 에러 발생", t.getMessage());
            }
        });
    }

}
