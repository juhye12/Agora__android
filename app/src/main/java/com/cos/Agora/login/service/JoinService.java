package com.cos.Agora.login.service;

import com.cos.Agora.CMRespDto;
import com.cos.Agora.login.model.JoinReqDto;
import com.cos.Agora.login.model.JoinRespDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface JoinService {
    @PUT("user/registry")
    Call<CMRespDto<JoinRespDto>> userJoin(@Body JoinReqDto joinReqDto);
}