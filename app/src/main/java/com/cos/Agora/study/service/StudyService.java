package com.cos.Agora.study.service;

import com.cos.Agora.CMRespDto;
import com.cos.Agora.study.model.ApplicationReqDto;
import com.cos.Agora.study.model.ApplicationRespDto;
import com.cos.Agora.study.model.DetailRespDto;
import com.cos.Agora.study.model.MyStudyListRespDto;
import com.cos.Agora.study.model.StudyCreateReqDto;
import com.cos.Agora.study.model.StudyCreateRespDto;
import com.cos.Agora.study.model.StudyListRespDto;

import java.util.List;

import retrofit2.Call;

import retrofit2.http.DELETE;

import retrofit2.http.Body;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StudyService {
    //사용자 분별위해 phoneNumber도 같이 보내주기!
//    @GET("study/list")
//    Call<CMRespDto<List<StudyListRespDto>>> getStudyList(@Query("phoneNumber") String phoneNumber,
//                                                         @Query("interest") String interest,
//                                                         @Query("lineup") String lineup);
    @GET("study/list")
    Call<CMRespDto<List<StudyListRespDto>>> getStudyList(@Query("latitude") Double latitude,
                                                         @Query("longitude") Double longitude,
                                                         @Query("interest") String interest,
                                                         @Query("lineup") String lineup);
    
    // userId가 없음
    @POST("study/create")
    Call<CMRespDto<StudyCreateRespDto>> createStudy(@Body StudyCreateReqDto studyCreateReqDto);

    // 가입신청
    @POST("study/application")
    Call<CMRespDto<ApplicationRespDto>> postApplication(@Query("userId") int userId,
                                                        @Query("studyId") int studyId);

    // 스터디 리스트 디테일과 내스터디 리스트 디테일 같게 동작
    @GET("study/detail/")
    Call<CMRespDto<List<DetailRespDto>>> getStudyDetail(@Query("studyId") int studyId);

    // 12.01 수정사항
    @GET("study/mystudy")
    Call<CMRespDto<List<MyStudyListRespDto>>> getMyStudyList(@Query("latitude") Double latitude,
                                                               @Query("longitude") Double longitude,
                                                             @Query("userId") int userId);

    @DELETE("study/list/{studyId}")
    Call <CMRespDto<List<StudyListRespDto>>> removePost(@Path("studyId") int studyId);

//    @GET("study/detail/{Id}")
//    Call<CMRespDto<List<DetailRespDto>>> getStudyDetail(@Path("id") int id);

    // 서버로 보낼 때 스터디 id를 보내줌으로써 해당 스터디 안에 있는 userId들과 나머지 정보들을 다시 받는 형식이 나으려나

}
