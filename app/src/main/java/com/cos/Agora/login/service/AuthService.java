package com.cos.Agora.login.service;

import com.cos.Agora.CMRespDto;
import com.cos.Agora.login.model.AuthReqDto;
import com.cos.Agora.login.model.AuthRespDto;
import com.cos.Agora.login.model.UserRespDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AuthService {

    @GET("auth")
    Call<CMRespDto<AuthRespDto>> authCodeSearch(@Query("phoneNumber")String phoneNumber);

    @POST("auth")
    Call<CMRespDto<AuthRespDto>> authCodeSend(@Body AuthReqDto authReqDto);

    @DELETE("auth")
    Call<CMRespDto> deleteCode(@Query("id")int id);

    @PUT("auth")
    Call<CMRespDto<AuthRespDto>> updateAuthCode(@Query("id")int id,@Query("authCode")String authCode);

    @GET("user/phoneNumber")
    Call<CMRespDto<UserRespDto>> UserSearch(@Query("phoneNumber")String phoneNumber);


}
