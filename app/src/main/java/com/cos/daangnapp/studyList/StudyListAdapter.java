package com.cos.daangnapp.studyList;

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

import com.bumptech.glide.Glide;
import com.cos.daangnapp.R;
//import com.cos.daangnapp.home.detail.DetailActivity;
import com.cos.daangnapp.studyList.model.StudyRespDto;

import java.text.DecimalFormat;
import java.util.List;

public class StudyListAdapter extends RecyclerView.Adapter<StudyListAdapter.MyViewHolder>{

    private static final String TAG = "StudyListAdapter";
    private List<StudyRespDto> mItemsList;
    private Context mContext;

    public StudyListAdapter(List<StudyRespDto> mItemsList, Context mContext) {
        this.mItemsList = mItemsList;
//        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = mContext;
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
        holder.setItem(mContext,mItemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemsList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //private ImageView photo;
        private TextView interest,studyname,distance,createDate,maxMember,curMember;
        private LinearLayout studyItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //photo= itemView.findViewById(R.id.home_iv_product_pic);
            interest =itemView.findViewById(R.id.study_interest);
            studyname =itemView.findViewById(R.id.study_name);
            distance =itemView.findViewById(R.id.study_distance);
            createDate = itemView.findViewById(R.id.study_made_date);
            maxMember = itemView.findViewById(R.id.study_maxmember);
            curMember = itemView.findViewById(R.id.study_curmember);
            studyItem= itemView.findViewById(R.id.study_item);

         /*   photo.setColorFilter(Color.parseColor("#FF3E3B3B"), PorterDuff.Mode.MULTIPLY);
            productItem.setBackgroundColor(Color.parseColor("#FF3E3B3B"));*/
        }
        public void setItem(Context mContext, StudyRespDto studyRespDto){
//            try {
//                String tmp;
//                if(postRespDto.getPrice().equals("무료나눔")){
//                    tmp ="무료나눔";
//                }else {
//                    tmp = moneyFormatToWon(Integer.parseInt(postRespDto.getPrice()));
//                }

//               Glide.with(mContext).load(postRespDto.getImages().get(0).getUri()).into(photo);
//                photo.setClipToOutline(true);
//                photo.setScaleType(ImageView.ScaleType.FIT_XY);


            try {
                interest.setText(studyRespDto.getInterest());
                studyname.setText(studyRespDto.getStudyname());
                distance.setText(studyRespDto.getDistance().toString());
//                if(tmp.equals("무료나눔")){
//                    tvPrice.setText(tmp);
//                }else{
//                    tvPrice.setText(tmp+"원");
//                }
                createDate.setText(studyRespDto.getCreatDate()+"");
                maxMember.setText(studyRespDto.getMaxMember()+"");
                curMember.setText(studyRespDto.getCurMember()+"");
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
}


