package com.cos.Agora;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface retrofitURL {
    public static final Retrofit retrofit = new Retrofit.Builder()

            .baseUrl("http://192.168.35.133:8080/") // 우리집
//            .baseUrl("http://192.168.36.71:8080/") // 스터디카페
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
