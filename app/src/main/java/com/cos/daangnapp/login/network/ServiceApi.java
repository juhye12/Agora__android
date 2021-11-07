package com.cos.daangnapp.login.network;

import com.cos.daangnapp.login.model.JoinData;
import com.cos.daangnapp.login.model.JoinResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {
    @POST("/user/join")
    Call<JoinResponse> userJoin(@Body JoinData data);
}