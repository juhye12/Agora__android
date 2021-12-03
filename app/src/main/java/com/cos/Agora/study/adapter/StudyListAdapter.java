package com.cos.Agora.study.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.Agora.R;
import com.cos.Agora.study.studydetail.DetailActivity;
import com.cos.Agora.study.model.StudyListRespDto;

import java.text.SimpleDateFormat;
import java.util.List;

public class StudyListAdapter extends RecyclerView.Adapter<StudyListAdapter.MyViewHolder>{

    private static final String TAG = "StudyListAdapter";
    private List<StudyListRespDto> mItemsList;
    private Context mContext;
    private static double latitude; // 사용자 위도
    private static double longitude;


    public StudyListAdapter(List<StudyListRespDto> mItemsList, Context mContext, double latitude, double longitude) {
        this.mItemsList = mItemsList;
//        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = mContext;

        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater =(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);   // main엑티비티에 연결할 객체를 생성해주는 인플레이터
        View view = inflater.inflate(R.layout.study_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setItem(mItemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //private ImageView photo;
        private TextView interest,title,distance,createDate,limit,current;
        private LinearLayout studyItem;
        private ImageView mood1, mood2, mood3, mood4, mood5;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            interest =itemView.findViewById(R.id.study_interest);
            title =itemView.findViewById(R.id.study_title);
            distance =itemView.findViewById(R.id.study_distance);
            createDate = itemView.findViewById(R.id.study_create_date);
            limit = itemView.findViewById(R.id.study_limit);
            current = itemView.findViewById(R.id.study_current);
            studyItem= itemView.findViewById(R.id.study_item);
            mood1 =itemView.findViewById(R.id.study_mood_1); // fragment에서 겹치는 부분 일단 manner_1으로 써놓음.
            mood2 =itemView.findViewById(R.id.study_mood_2); // fragment에서 겹치는 부분 일단 manner_2으로 써놓음.
            mood3 =itemView.findViewById(R.id.study_mood_3); // fragment에서 겹치는 부분 일단 manner_3으로 써놓음.
            mood4 =itemView.findViewById(R.id.study_mood_4); // fragment에서 겹치는 부분 일단 manner_4으로 써놓음.
            mood5 =itemView.findViewById(R.id.study_mood_5);

        }

        public void setItem(StudyListRespDto studyListRespDto){
            try {
                interest.setText(studyListRespDto.getInterest());
                title.setText(studyListRespDto.getTitle());

                distance.setText(new Double((studyListRespDto.getDistance()*100/100.0)/1000).toString());

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                createDate.setText(format.format(studyListRespDto.getCreateDate())+"");
                limit.setText(studyListRespDto.getLimit()+"");
                current.setText(studyListRespDto.getCurrent()+"");

                // 분위기 점수 그림으로 표현
                int moodScore = 0;
                moodScore = studyListRespDto.getMood();
                if(moodScore >= 0 && moodScore < 1){
                    mood1.setVisibility(View.VISIBLE);
                    mood2.setVisibility(View.INVISIBLE);
                    mood3.setVisibility(View.INVISIBLE);
                    mood4.setVisibility(View.INVISIBLE);
                    mood5.setVisibility(View.INVISIBLE);
                } else if(moodScore >= 1 && moodScore < 2){
                    mood1.setVisibility(View.INVISIBLE);
                    mood2.setVisibility(View.VISIBLE);
                    mood3.setVisibility(View.INVISIBLE);
                    mood4.setVisibility(View.INVISIBLE);
                    mood5.setVisibility(View.INVISIBLE);
                } else if(moodScore >= 2 && moodScore < 3){
                    mood1.setVisibility(View.INVISIBLE);
                    mood2.setVisibility(View.INVISIBLE);
                    mood3.setVisibility(View.VISIBLE);
                    mood4.setVisibility(View.INVISIBLE);
                    mood5.setVisibility(View.INVISIBLE);
                } else if(moodScore >= 3 && moodScore < 4){
                    mood1.setVisibility(View.INVISIBLE);
                    mood2.setVisibility(View.INVISIBLE);
                    mood3.setVisibility(View.INVISIBLE);
                    mood4.setVisibility(View.VISIBLE);
                    mood5.setVisibility(View.INVISIBLE);
                } else{
                    mood1.setVisibility(View.INVISIBLE);
                    mood2.setVisibility(View.INVISIBLE);
                    mood3.setVisibility(View.INVISIBLE);
                    mood4.setVisibility(View.INVISIBLE);
                    mood5.setVisibility(View.VISIBLE);
                }

                studyItem.setOnClickListener(v -> {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("studyId", studyListRespDto.getId());
                    // 이 밑의 코드부터는 추가된 코드로써 클릭했을 때의 스터디의 데이터를 전달하는 역할을 한다.
                    intent.putExtra("studyTitle", studyListRespDto.getTitle());
                    intent.putExtra("studyInterest", studyListRespDto.getInterest());
                    intent.putExtra("studyCreateDate", studyListRespDto.getCreateDate()); // Date로 보냈음
                    intent.putExtra("studyLimit", studyListRespDto.getLimit());
                    intent.putExtra("studyCurrent", studyListRespDto.getCurrent());
                    intent.putExtra("studyDistance", studyListRespDto.getDistance());


                    intent.putExtra("studylatitude", studyListRespDto.getLatitude());
                    intent.putExtra("studylongitude", studyListRespDto.getLongitude());
                    // 여기까지가 추가된 코드

                    v.getContext().startActivity(intent);
                });
            } catch (Exception e) {
                Log.d(TAG, "null");
            }
        }
    }
}