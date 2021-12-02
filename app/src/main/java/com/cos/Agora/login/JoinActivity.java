package com.cos.Agora.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.cos.Agora.CMRespDto;
import com.cos.Agora.R;
import com.cos.Agora.global.User;
import com.cos.Agora.login.model.JoinReqDto;
import com.cos.Agora.login.model.JoinRespDto;
import com.cos.Agora.login.service.JoinService;
import com.cos.Agora.study.StudyListActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {

    private static final String TAG = "JoinActivity";
    private EditText mAssociation;
    private EditText mAge;
    private EditText mSex;
    private Spinner sInterest;
    private Spinner xInterest;
    private String sex;
    private String interest;
    private Button mJoinButton;
    private String phoneNumber; // 이전 activity에서 작성된 phoneNumber를 가져옴
//    private LocationReqDto locationReqDto;
    private Double latitude;
    private Double longitude;

    String[] interestList = {"전체","어학","프로그래밍","게임","취직","주식","운동","와인","여행"};
    String[] sexList = {"남", "여"};

    private com.cos.Agora.retrofitURL retrofitURL;
    private JoinService joinService = retrofitURL.retrofit.create(JoinService .class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        init();

        // join부분 관심분야 필터링
        ArrayAdapter<String> adapter_sinterest = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, interestList);
        adapter_sinterest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sInterest.setAdapter(adapter_sinterest);
        sInterest.setSelection(0);

        sInterest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                interest = sInterest.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // join부분 성별 필터링
        ArrayAdapter<String> adapter_xinterest = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sexList);
        adapter_xinterest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        xInterest.setAdapter(adapter_xinterest);
        xInterest.setSelection(0);

        xInterest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex = xInterest.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        sInterest = findViewById(R.id.join_interest);
        xInterest = findViewById(R.id.join_sex);
        mJoinButton = findViewById(R.id.join_button);

        // 밑은 글로벌 변수로 사용자의 핸드폰번호, 위도, 경도를 담고 있음
        phoneNumber = ((User)getApplication()).getPhoneNumber();
        latitude = ((User)getApplication()).getLatitude();
        longitude = ((User)getApplication()).getLongitude();
    }

    //JoinActivity의 main method
    private void attemptJoin() { // 입력된 데이터를 저장하고 StudyListActivity로 넘겨야 함
        String association = mAssociation.getText().toString();
        int age = Integer.parseInt(mAge.getText().toString());

        startJoin(new JoinReqDto(phoneNumber,association,age,sex,interest,latitude,longitude));// Req객체 생성
        
        Intent intent = new Intent(JoinActivity.this, StudyListActivity.class);
        intent.putExtra("phoneNumber",phoneNumber);
//        intent.putExtra("location", (Parcelable) locationReqDto);
        intent.putExtra("latitude",latitude);
        intent.putExtra("longitude",longitude);
        startActivity(intent);
        JoinActivity.this.finish();
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
                editor.putInt("age",joinRespDto.getAge());
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
