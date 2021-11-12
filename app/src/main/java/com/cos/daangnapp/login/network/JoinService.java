package com.cos.daangnapp.login.network;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.login.model.JoinReqDto;
import com.cos.daangnapp.login.model.JoinRespDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JoinService {
    @POST("user/registry")
    Call<CMRespDto<JoinRespDto>> userJoin(@Body JoinReqDto joinReqDto);
}