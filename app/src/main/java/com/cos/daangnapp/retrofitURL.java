package com.cos.daangnapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public interface retrofitURL {
    public static final Retrofit retrofit = new Retrofit.Builder()
<<<<<<< HEAD
            .baseUrl("http://3.37.59.44:8080")
=======
//            .baseUrl("http://192.168.35.55:8080/")
            .baseUrl("http://192.168.36.4:8080/")
>>>>>>> 523b9ee3292b369ef957f47e9590eb3613205c7e
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
