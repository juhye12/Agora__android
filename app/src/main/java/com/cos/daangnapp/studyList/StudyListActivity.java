package com.cos.daangnapp.studyList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.R;
import com.cos.daangnapp.location.LocationActivity;
import com.cos.daangnapp.main.MainActivity;
import com.cos.daangnapp.studyList.model.StudyRespDto;
import com.cos.daangnapp.retrofitURL;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudyListActivity extends AppCompatActivity {
    private static final String TAG = "StudyList";
    private  MainActivity activity;
    private RecyclerView studyList;
    private StudyListAdapter studylistAdapter;
    private Spinner spinner_interest;
    private Spinner spinner_lineup;
    private String interest;
    private String lineup;
//    private Spinner spinner;
    private ArrayList<StudyRespDto> studyRespDtos = new ArrayList<>();
    private retrofitURL retrofitURL;
    //private ImageView btnSearch,IvSearch,IvFilter,IvNotice;
    private EditText etSearch;
    private StudyListService studyListService= retrofitURL.retrofit.create(StudyListService.class);

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        activity = (MainActivity) getActivity();
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        activity = null;
//    }

    String[] interestList = {"전체","어학","프로그래밍","게임","취직","주식","운동","와인","여행"};
    String[] lineupList = {"최신순","거리순","추천순"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studylist);

//interest별 필터링
        spinner_interest = findViewById(R.id.spinner_interest);
        ArrayAdapter<String> adapter_interest = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, interestList);
        adapter_interest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_interest.setAdapter(adapter_interest);
        spinner_interest.setSelection(0);

        spinner_interest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                interest = spinner_interest.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//정렬 부분
        spinner_lineup = findViewById(R.id.spinner_lineup);
        ArrayAdapter<String> adapter_lineup = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, lineupList);
        adapter_interest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_lineup.setAdapter(adapter_lineup);
        spinner_lineup.setSelection(0);

        spinner_lineup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lineup = spinner_lineup.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_studylist, container, false);

//        SharedPreferences pref =getActivity().getSharedPreferences("pref",Context.MODE_PRIVATE);
//        String dong = pref.getString("dong",null);
//        String gu = pref.getString("gu", null);

//        String[] LocationList = {
//                dong,"다른 동네 선택"
//        };
//        spinner =view.findViewById(R.id.toolbar_tv_dong);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, LocationList);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setSelection(0);
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(position ==1) {
//                    Intent intent = new Intent(activity, LocationActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    activity.finish();
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        studyList = findViewById(R.id.rv_studylist);
        LinearLayoutManager manager = new LinearLayoutManager(activity, RecyclerView.VERTICAL, false);
        studyList.setLayoutManager(manager);
        getStudies(interest, lineup);

        //btnSearch = view.findViewById(R.id.btn_search);
        //IvFilter = view.findViewById(R.id.iv_filter);
        //IvNotice = view.findViewById(R.id.iv_notice);


        //IvSearch = view.findViewById(R.id.iv_search);
//    etSearch = view.findViewById(R.id.et_post_search);
//
//
//    etSearch.addTextChangedListener(new TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//            searchPosts(gu,s.toString());
//        }
//    });

        //IvSearch.setVisibility(View.INVISIBLE);
        //etSearch .setVisibility(View.INVISIBLE);

//    btnSearch.setOnClickListener(v -> {
//        btnSearch.setVisibility(view.INVISIBLE);
//        IvFilter.setVisibility(view.INVISIBLE);
//        IvNotice.setVisibility(view.INVISIBLE);
//
//        IvSearch.setVisibility(view.VISIBLE);
//        etSearch.setVisibility(view.VISIBLE);
//    });
    }

    public void getStudies(String interest, String lineup){
        Call<CMRespDto<List<StudyRespDto>>> call = studyListService.getstudies(interest,lineup);
        call.enqueue(new Callback<CMRespDto<List<StudyRespDto>>>() {
            @Override
            public void onResponse(Call<CMRespDto<List<StudyRespDto>>> call, Response<CMRespDto<List<StudyRespDto>>> response) {
                try {
                    CMRespDto<List<StudyRespDto>> cmRespDto = response.body();
                    List<StudyRespDto> studies = cmRespDto.getData();
                    Log.d(TAG, "studies: " +studies);
                    studylistAdapter = new StudyListAdapter(studies,activity);
                    studyList.setAdapter(studylistAdapter);
                } catch (Exception e) {
                    Log.d(TAG, "null");
                }
            }
            @Override
            public void onFailure(Call<CMRespDto<List<StudyRespDto>>> call, Throwable t) {
                Log.d(TAG, "onFailure: 게시물목록보기 실패 !!");
            }
        });
    }
}
