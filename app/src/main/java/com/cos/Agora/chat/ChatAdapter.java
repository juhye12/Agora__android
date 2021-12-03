package com.cos.Agora.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.cos.Agora.R;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<com.cos.Agora.chat.ChatData> mDataset;
    private String myNickname;
    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {//연결한 이미지 파일 안에 들어가는 요소들은 여기다가 입력하는것임
        public TextView TextView_nickname;
        public TextView TextView_msg;
        public View rootView;

        public MyViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View
            TextView_nickname = v.findViewById(R.id.TextView_nickname);
            TextView_msg = v.findViewById(R.id.TextView_msg);
            rootView = v;

            v.setClickable(true);
            v.setEnabled(true);
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param myDataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */

    //recyclerview에서 보여줄 값을 들고 있는 원본 데이터 변수가 mDataset변수입
    public ChatAdapter(List<com.cos.Agora.chat.ChatData> myDataSet, Context context, String myNickname) {
        mDataset = myDataSet;
        this.myNickname = myNickname;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//이미지 파일을 연결
        // Create a new view, which defines the UI of the list item
        LinearLayout v= (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_chat, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        com.cos.Agora.chat.ChatData chat = mDataset.get(position);

        holder.TextView_nickname.setText(chat.getNickname());
        holder.TextView_msg.setText(chat.getMsg());

        if(chat.getNickname().equals(this.myNickname)){
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
        }
        else{
            holder.TextView_msg.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            holder.TextView_nickname.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        }
    }//ViewHolder가 반복될때마다 데이터를 세팅해주는 애임

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset==null ? 0 : mDataset.size();// 이만큼 ViewHolder가 반복됨
    }

    public com.cos.Agora.chat.ChatData getChat(int position){
        return mDataset != null ? mDataset.get(position) : null;
    }

    public void addChat(com.cos.Agora.chat.ChatData chat){
        mDataset.add(chat);
        notifyItemInserted(mDataset.size()-1);  //갱신
    }
}