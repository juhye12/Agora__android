package com.cos.Agora.study.service;

import com.cos.Agora.CMRespDto;
import com.cos.Agora.study.model.EvalRespDto;
import com.cos.Agora.study.model.StudyCreateReqDto;
import com.cos.Agora.study.model.StudyCreateRespDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EvalService {

    @GET("evaluate")
    Call<CMRespDto<List<EvalRespDto>>> getEvalList(@Query("userId") int userId);

    @POST("evaluate/mood")
    Call<CMRespDto<EvalRespDto>> moodEval(@Query("studyId") int studyId,
                                          @Query("userId") int userId,
                                          double moodAvgScore );

    @POST("evaluate/manner")
    Call<CMRespDto<EvalRespDto>> mannerEval(@Query("evaluatorId") int evaluatorId,
                                            @Query("evaluateeId") int evaluateeId,
                                            @Query("studyId") int studyId,
                                            double mannerAvgScore);



}
