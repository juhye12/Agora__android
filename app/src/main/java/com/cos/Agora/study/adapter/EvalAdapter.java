package com.cos.Agora.study.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.Agora.R;
import com.cos.Agora.study.model.EvalRespDto;
import com.cos.Agora.study.model.StudyListRespDto;
import com.cos.Agora.study.studydetail.DetailActivity;
import com.cos.Agora.study.studyevaluate.EvalListActivity;
import com.cos.Agora.study.studyevaluate.MannerEvalActivity;
import com.cos.Agora.study.studyevaluate.MoodEvalActivity;

import java.text.SimpleDateFormat;
import java.util.List;

public class EvalAdapter extends RecyclerView.Adapter<EvalAdapter.MyViewHolder>{

    private static final String TAG = "EvalAdapter";
    private List<EvalRespDto> mItemsList;
    private Context mContext;

    public EvalAdapter(List<EvalRespDto> mItemsList, Context mContext) {
        this.mItemsList = mItemsList;
//        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = mContext;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater =(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);   // main엑티비티에 연결할 객체를 생성해주는 인플레이터
        View view = inflater.inflate(R.layout.eval_item,parent,false);
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
        private TextView type,interest_title,title_nick;
        private LinearLayout evalItem;
        private Button EvaluateBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        //여기부터 수정
            EvaluateBtn = itemView.findViewById(R.id.btn_eval);//평가하기 버튼
            type =itemView.findViewById(R.id.eval_type);//평가 유형
            interest_title = itemView.findViewById(R.id.eval_interest_title);//interest or 스터디명
            title_nick =itemView.findViewById(R.id.eval_title_nick);//스터디명 or Nickname
            evalItem= itemView.findViewById(R.id.eval_item);
        }

        public void setItem(EvalRespDto evalRespDto){
            try {
                    type.setText(evalRespDto.getEvalType());

                    //mood 평가라면
                    if((evalRespDto.getEvalType()).equals("mood")){
                        interest_title.setText(evalRespDto.getInterest());//[interest]studytitle
                        title_nick.setText(evalRespDto.getStudyTitle());
                        //평가하기 버튼 클릭 시
                        EvaluateBtn.setOnClickListener(v -> {
                            Intent intent = new Intent(v.getContext(), MoodEvalActivity.class);
                            //MoodEvalActivity로 studyId, interest, title 넘겨주기
                            intent.putExtra("studyId", evalRespDto.getStudyId());
                            intent.putExtra("interest", evalRespDto.getInterest());
                            intent.putExtra("title", evalRespDto.getStudyTitle());
                            v.getContext().startActivity(intent);
                        });
                    }

                    //manner평가라면
                    else
                    {
                        interest_title.setText(evalRespDto.getStudyTitle());//[studytitle]evaluateeNickname;
                        title_nick.setText(evalRespDto.getEvaluateeNickName());
                        //평가하기 버튼 클릭 시
                        EvaluateBtn.setOnClickListener(v -> {
                            Intent intent = new Intent(v.getContext(), MannerEvalActivity.class);
                            //MannerEvalActivity로 evaluateeId, title, nickName 넘겨주기
                            intent.putExtra("evaluateeId", evalRespDto.getEvaluateeId());
                            intent.putExtra("title", evalRespDto.getStudyTitle());
                            intent.putExtra("evaluateeNickName", evalRespDto.getEvaluateeNickName());
                            v.getContext().startActivity(intent);
                        });
                    }

            } catch (Exception e) {
                Log.d(TAG, "null");
            }
        }
    }
}