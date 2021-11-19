package com.cos.Agora.study.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class StudyCreateReqDto {
    private String studyName;//스터디 이름
    private String studyInterest;//스터디 관심분야
    private Integer studyFrequency;//스터디 횟수
    private Integer studyMemNum;//스터디 모집 인원
    private Double studyLongitude;
    private Double studyLatitude;
    private String studyDescription;//스터디 설명

}
