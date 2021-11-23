package com.cos.Agora.study.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class DetailRespDto {
    private long id;            // id
    private String title;       // 스터디제목
    private String interest;    // 관심분야
    private Date createDate;    // 생성날짜
    private int limit;          // 수용인원
    private double latitude;    // 사용자 위도
    private double longitude;    // 사용자 경도
    private double distance;
    private int current;        // 현재인원
    private String studyDescription;//스터디 설명
}
