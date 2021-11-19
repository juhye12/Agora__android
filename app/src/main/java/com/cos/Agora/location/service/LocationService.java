package com.cos.Agora.location.service;

import com.cos.Agora.CMRespDto;
import com.cos.Agora.location.model.LocationReqDto;
import com.cos.Agora.location.model.LocationRespDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LocationService {

    @POST("location")
    Call<CMRespDto<List<LocationRespDto>>> getLocations(@Body LocationReqDto locationReqDto);

    @GET("location")
    Call<CMRespDto<List<LocationRespDto>>> getLocations(@Query("address") String address );

}
