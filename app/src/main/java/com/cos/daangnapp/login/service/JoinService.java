package com.cos.daangnapp.login.network;

import com.cos.daangnapp.CMRespDto;
import com.cos.daangnapp.login.model.JoinReqDto;
import com.cos.daangnapp.login.model.JoinRespDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface JoinService {
    @PUT("user/registry")
    Call<CMRespDto<JoinRespDto>> userJoin(@Body JoinReqDto joinReqDto);
}