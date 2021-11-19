package com.cos.Agora.study.service;

import com.cos.Agora.CMRespDto;
import com.cos.Agora.study.model.StudyCreateReqDto;
import com.cos.Agora.study.model.StudyCreateRespDto;
import com.cos.Agora.study.model.StudyListRespDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface StudyService {
    //사용자 분별위해 phoneNumber도 같이 보내주기!
    @GET("study/list")
    Call<CMRespDto<List<StudyListRespDto>>> getStudyList(@Query("phoneNumber") String phoneNumber,
                                                         @Query("interest") String interest,
                                                         @Query("lineup") String lineup);
    @POST("study/create")
    Call<CMRespDto<StudyCreateRespDto>> createStudy(StudyCreateReqDto studyCreateReqDto);

    @GET("study/detail")
    Call<CMRespDto<List<StudyListRespDto>>> getStudyDetail(@Query("id") int id);




}
