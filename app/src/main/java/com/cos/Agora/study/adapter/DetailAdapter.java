package com.cos.Agora.study.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cos.Agora.R;
import com.cos.Agora.study.model.DetailRespDto;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder>{

    private static final String TAG = "DetailAdapter";
    private List<DetailRespDto> mUserList;
    private Context dContext;
    private long userId;

    public DetailAdapter(List<DetailRespDto> mUserList, Context dContext) {
        this.mUserList = mUserList;
//        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dContext = dContext;
//        this.userId = userId;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        LayoutInflater inflater =(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);   // main엑티비티에 연결할 객체를 생성해주는 인플레이터
        View view = inflater.inflate(R.layout.detail_member_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setItem(mUserList.get(position));
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView nickname, role, manner, association;
        private LinearLayout userProfile;
        public MyViewHolder(@NonNull View detailView) {
            super(detailView);

            nickname =detailView.findViewById(R.id.detail_nickname);
            role =detailView.findViewById(R.id.detail_role);
            manner =detailView.findViewById(R.id.detail_manner);
            association =detailView.findViewById(R.id.detail_association);
            userProfile = detailView.findViewById(R.id.layout_userprofile);

//            createDate = itemView.findViewById(R.id.study_create_date);
//            limit = itemView.findViewById(R.id.study_limit);
//            current = itemView.findViewById(R.id.study_current);
//            studyItem= itemView.findViewById(R.id.study_item);
        }

        public void setItem(DetailRespDto detailRespDto){
            try {
                nickname.setText(detailRespDto.getNickName());
                role.setText(detailRespDto.getRole()+"");
                manner.setText(detailRespDto.getManner()+"");
                association.setText(detailRespDto.getAssociation());

                // 사용자 클릭했을 때 뜨는 인텐트 -> 일단은 다시 detail 액티비티로 오도록 함
//                userProfile.setOnClickListener(v -> {
//                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
//                    intent.putExtra("userId", detailRespDto.getUserId());
//                    v.getContext().startActivity(intent);
//                });
            } catch (Exception e) {
                Log.d(TAG, "null");
            }
        }
    }
}