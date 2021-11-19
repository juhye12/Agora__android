package com.cos.Agora.study;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.cos.Agora.CMRespDto;
import com.cos.Agora.R;
import com.cos.Agora.global.User;
import com.cos.Agora.main.MainActivity;
import com.cos.Agora.study.adapter.StudyListAdapter;
import com.cos.Agora.study.model.StudyListRespDto;
import com.cos.Agora.retrofitURL;
import com.cos.Agora.study.service.StudyService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private Context mContext;
    private static final String TAG = "DetailActivity";
    //rivate ViewPagerAdapter viewPagerAdapter;
    //private GridViewAdapter gridViewadapter;
    private ArrayList<String> mImageList;
    private int postId;
    private ImageButton mBack, mShare, mMore;
    private ViewPager photoList;
    private CircleIndicator circleIndicator;
    private ImageView profile;
    private TextView tvNickName,tvAddress,tvTitle,tvTime,tvContent,tvCategori,tvFavorite,tvViewCount,tvAnotherNick,tvPrice;
    private retrofitURL retrofitURL;
    private StudyService studyListService= retrofitURL.retrofit.create(StudyService.class);
    private StudyService getStudyDetail= retrofitURL.retrofit.create(StudyService.class);
    //private DetailService detailService= retrofitURL.retrofit.create(DetailService .class);
    // 프로필 부분 필요 없을 것 같아서 주석 처리함
    //private ProfileService profileService= retrofitURL.retrofit.create(ProfileService .class);
    private RecyclerView rvProduct;
    private LinearLayout reportAndProduct,layout_userprofile;
    private Button detail_btn_chat;
    private ImageButton btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
        initSetting();
    }

    public  void init(){
        // 레이아웃에서 주석 처리한 코드들 DetailActivity에서 변수들 주석처리함 11.12
        // rvProduct =findViewById(R.id.rv_product);
        profile = findViewById(R.id.detail_iv_profile);
        tvNickName = findViewById(R.id.detail_nickname);
        tvAddress = findViewById(R.id.detail_tv_address);
        // tvTitle = findViewById(R.id.detail_tv_title);
        tvContent=findViewById(R.id.detail_tv_content);
        //tvCategori=findViewById(R.id.detail_tv_categories);
        tvTime = findViewById(R.id.detail_tv_time);
        tvFavorite = findViewById(R.id.detail_tv_favorite);
        tvViewCount = findViewById(R.id.detail_tv_viewCount);
        //tvAnotherNick = findViewById(R.id.detail_tv_nickname_another);
        tvPrice = findViewById(R.id.detail_tv_price);
        mBack = findViewById(R.id.product_information_iv_back);
        mShare = findViewById(R.id.product_information_iv_share);
        mMore = findViewById(R.id.product_information_iv_more);

        //reportAndProduct = findViewById(R.id.reportAndProduct);
        layout_userprofile = findViewById(R.id.layout_userprofile);
        detail_btn_chat = findViewById(R.id.detail_btn_chat);

        btnFavorite = findViewById(R.id.btn_favorite);
    }

    public void initSetting(){

        Intent intent = getIntent();
        postId = intent.getIntExtra("postId", 1);
        getpost(postId);

        mBack.setImageResource(R.drawable.home_as_up);
        mBack.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        mShare.setImageResource(R.drawable.ic_share_outline_24);
        mShare.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        mMore.setImageResource(R.drawable.icon_ads_more);
        mMore.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);


        btnFavorite.setOnClickListener(v -> {
            btnFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
        });
    }

    public void productInformationOnClick(View view) {
        switch (view.getId()) {
            case R.id.product_information_iv_back:
                onBackPressed();
                break;
            case R.id.product_information_iv_share:
                //share();
                break;
            case R.id.product_information_iv_more:
                break;
            default:
                break;
        }
    }


    public void getpost(int id){
        Call<CMRespDto<PostRespDto>> call = detailService.getpost(id);
        call.enqueue(new Callback<CMRespDto<PostRespDto>>() {
            @Override
            public void onResponse(Call<CMRespDto<PostRespDto>> call, Response<CMRespDto<PostRespDto>> response) {
                CMRespDto<PostRespDto> cmRespDto = response.body();
                PostRespDto posts = cmRespDto.getData();
                String tmp;

//                if(posts.getPrice().equals("무료나눔")){
//                    tmp ="무료나눔";
//                }else{
//                    tmp = moneyFormatToWon(Integer.parseInt(posts.getPrice()));
//                }

                // 이미지 관련 코드는 주석처리함 11.11 00:08
//                mImageList = new ArrayList();
//
//                for(int i=0; i<posts.getImages().size(); i++) {
//                    mImageList.add(posts.getImages().get(i).getUri());
//                }

                // 게시물 정보를 봤을 때 이미지를 옆으로 넘기면서 볼 수 있도록 하는 코드
//                photoList = findViewById(R.id.detail_viewPager);
//                Log.d(TAG, "mImageList: "+mImageList);
//                viewPagerAdapter = new ViewPagerAdapter(DetailActivity.this,mImageList);
//                photoList.setAdapter(viewPagerAdapter);

                // 이미지 넘기는 화면 밑의 동그라미로 이미지 넘길 때마다 동그라미 색이 바뀌는 코드
//                circleIndicator= findViewById(R.id.detail_indicator);
//                viewPagerAdapter.notifyDataSetChanged();
//                circleIndicator.setViewPager(photoList);


//                if(posts.getUser().getPhoto() !=null){
//                    Glide.with(DetailActivity.this).load(posts.getUser().getPhoto()).into(profile);
//                    profile.setClipToOutline(true);
//                    profile.setBackground(new ShapeDrawable(new OvalShape()));
//                    profile.setScaleType(ImageView.ScaleType.FIT_XY);
//                  /*  Uri uri = Uri.parse(posts.getUser().getPhoto());
//                    profile.setImageURI(uri);
//                    profile.setBackground(new ShapeDrawable(new OvalShape()));
//                    profile.setClipToOutline(true);
//                    profile.setScaleType(ImageView.ScaleType.FIT_XY);*/
//                }

                tvNickName.setText(posts.getUser().getNickName());
                tvAddress.setText(posts.getDong());
                tvTitle.setText(posts.getTitle());
                tvTime.setText(posts.getCreateDate().toString());
                tvFavorite.setText(posts.getFavorite()+"");
                tvCategori.setText(posts.getCategory());
                tvContent.setText(posts.getContent());
                tvViewCount.setText(posts.getCount()+"");
                tvAnotherNick.setText(posts.getUser().getNickName());

                if(tmp.equals("무료나눔")){
                    tvPrice.setText(tmp);
                }else{
                    tvPrice.setText(tmp+"원");
                }

                GridLayoutManager gridLayoutManager = new GridLayoutManager(DetailActivity.this,2);
                rvProduct.setLayoutManager(gridLayoutManager);
                gridViewadapter = new GridViewAdapter(posts.getUser().getPosts(),DetailActivity.this);
                rvProduct.setAdapter(gridViewadapter);

                SharedPreferences pref =getSharedPreferences("pref", Context.MODE_PRIVATE);
                int userId = pref.getInt("userId",0);

                // 자신이 올린 게시물이면 "게시글 신고하기 및 ~~님의 판매상품"이 보이지 않게 하는 코드
//                if(userId == posts.getUser().getId()){
//                    reportAndProduct.setVisibility(View.INVISIBLE);
//                }else{
//                    reportAndProduct.setVisibility(View.VISIBLE);
//                }

            /*    if(userId ==posts.getUser().getId()){
                    detail_btn_chat.setEnabled(false);
                }else{
                    detail_btn_chat.setEnabled(true);
                }*/
                mMore.setOnClickListener(v -> {
                    if(userId ==posts.getUser().getId()){
                        PopupMenu popup = new PopupMenu(DetailActivity.this ,mMore );
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getItemId() == R.id.menu1){
                                    Log.d(TAG, "onMenuItemClick: 수정페이지이동 ");
                                    Intent intent = new Intent(DetailActivity.this, UpdateActivity.class);
                                    intent.putExtra("postId", posts.getId());
                                    intent.putExtra("title", posts.getTitle());
                                    intent.putExtra("category", posts.getCategory());
                                    intent.putExtra("content", posts.getContent());
                                    intent.putExtra("price", posts.getPrice());
                                    intent.putExtra("mImageList", mImageList);
                                    startActivity(intent);
                                    DetailActivity.this.finish();
                                }else if (item.getItemId() == R.id.menu2){
                                    AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                                    builder.setTitle("게시물 삭제").setMessage("정말 게시물을 삭제 하시겠습니까?");
                                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            Log.d(TAG, "onMenuItemClick: 삭제완료");
                                            removePost(postId);
                                            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);     // intent 타입을 넣어야함  !!
                                            DetailActivity.this.finish();
                                        }
                                    });
                                    builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });
                                    builder.show();

                                }
                                return false;
                            }
                        });
                        MenuInflater inf = popup.getMenuInflater();
                        inf.inflate(R.menu.updatemenu, popup.getMenu());
                        popup.show();
                    }else{
                        detail_btn_chat.setEnabled(true);
                        PopupMenu popup = new PopupMenu(DetailActivity.this ,mMore );
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getItemId() == R.id.menu3){
                                    Toast.makeText(getApplicationContext(),"준비중입니다.",Toast.LENGTH_LONG).show();
                                }else if (item.getItemId() == R.id.menu4){
                                    Toast.makeText(getApplicationContext(),"준비중입니다.",Toast.LENGTH_LONG).show();
                                }
                                return false;
                            }
                        });
                        MenuInflater inf = popup.getMenuInflater();
                        inf.inflate(R.menu.hidemenu, popup.getMenu());
                        popup.show();
                    }
                });


                layout_userprofile.setOnClickListener(v -> {
                    Intent intent = new Intent(DetailActivity.this, UserProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("userId",posts.getUser().getId());
                    startActivity(intent);
                });

                // 이후 채팅 액티비티에서 필요할 것 같은데 일단 주석 처리함
//                detail_btn_chat.setOnClickListener(v -> {
//                    Intent intent = new Intent(DetailActivity.this, ChatActivity.class);
//                    intent.putExtra("userId",userId);
//                    intent.putExtra("postId",posts.getId());
//                    intent.putExtra("nickName",posts.getUser().getNickName());
//                    intent.putExtra("uri",posts.getUser().getPhoto());
//                    startActivity(intent);
//                });

            }
            @Override
            public void onFailure(Call<CMRespDto<PostRespDto>> call, Throwable t) {

            }
        });
    }

    public void removePost(int id){
        Call<CMRespDto> call = detailService.removePost(id);
        call.enqueue(new Callback<CMRespDto>() {
            @Override
            public void onResponse(Call<CMRespDto> call, Response<CMRespDto> response) {
                Log.d(TAG, "onResponse: 삭제성공");
            }
            @Override
            public void onFailure(Call<CMRespDto> call, Throwable t) {
                Log.d(TAG, "onFailure: 삭제실패");
            }
        });
    }

    public static String moneyFormatToWon(int inputMoney) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        return decimalFormat.format(inputMoney);
    }

}
