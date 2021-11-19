package com.cos.Agora.login.service;

import com.cos.Agora.CMRespDto;
import com.cos.Agora.login.model.UserRespDto;
import com.cos.Agora.login.model.UserSaveReqDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @GET("user/nickName")
    Call<CMRespDto<UserRespDto>> NickNameSearch(@Query("nickName")String nickName);

    @POST("user")
    Call<CMRespDto<UserRespDto>> save(@Body UserSaveReqDto userSaveReqDto);
}
