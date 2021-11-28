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

    // userId가 없음
    @POST("evaluate/mood")
    Call<CMRespDto<EvalRespDto>> moodEval(@Query("studyId") int studyId, double moodAvgScore );

    // studyId가 없음
    // @Query("evaluatorId") int evaluatorId 이 부분은 위의 userId에서 처리되기 때문에 필요없지 않나 생각됩니다!
    @POST("evaluate/manner")
    Call<CMRespDto<EvalRespDto>> mannerEval(@Query("evaluatorId") int evaluatorId, @Query("evaluateeId") int evaluateeId, double mannerAvgScore);


}
