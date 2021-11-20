package com.cos.Agora.study.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudyCreateReqDto {
    private String title;//스터디 이름
    private String interest;//스터디 관심분야
    private int count;//스터디 횟수
    private int limit;//스터디 모집 인원
    private Double longitude;
    private Double latitude;
    private String description;//스터디 설명

}
