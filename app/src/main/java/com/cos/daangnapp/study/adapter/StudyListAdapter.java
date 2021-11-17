package com.cos.daangnapp.study.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.daangnapp.R;
//import com.cos.daangnapp.home.detail.DetailActivity;
import com.cos.daangnapp.study.DetailActivity;
import com.cos.daangnapp.study.model.StudyListRespDto;

import java.text.DecimalFormat;
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
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            interest =itemView.findViewById(R.id.study_interest);
            title =itemView.findViewById(R.id.study_title);
            distance =itemView.findViewById(R.id.study_distance);
            createDate = itemView.findViewById(R.id.study_create_date);
            limit = itemView.findViewById(R.id.study_limit);
            current = itemView.findViewById(R.id.study_current);
            studyItem= itemView.findViewById(R.id.study_item);

        }
        public void setItem(StudyListRespDto studyRespDto){
            try {
                interest.setText(studyRespDto.getInterest());
                title.setText(studyRespDto.getTitle());

                distance.setText(distance(latitude,
                                          longitude,
                                          studyRespDto.getLatitude(),
                                          studyRespDto.getLongitude()));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                createDate.setText(format.format(studyRespDto.getCreateDate())+"");
                limit.setText(studyRespDto.getLimit()+"");
                current.setText(studyRespDto.getCurrent()+"");
                studyItem.setOnClickListener(v -> {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("studyId", studyRespDto.getId());
                    v.getContext().startActivity(intent);
                });
            } catch (Exception e) {
                Log.d(TAG, "null");
            }
        }

        public static String moneyFormatToWon(int inputMoney) {
            DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            return decimalFormat.format(inputMoney);
        }
    }

    // 사용되는 위도와 경도는 WGS84 type
    public static String distance(double lat1, double long1, double lat2, double long2){
        double theta = long1 - long2;
        double dist = Math.sin(deg2rad(lat1))*Math.sin(deg2rad(lat2)) +
                Math.cos(deg2rad(lat1))*Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        dist = dist * 1.609344; // 최종
        dist = Math.round(dist * 10)/10.0; // 소수점 첫째자리까지만 나타냄

        if(dist<0.2) dist = 0.2; // 너무 가까운 거리면 200미터 안에 있다고 가정

        return new Double(dist).toString();
    }

    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}


