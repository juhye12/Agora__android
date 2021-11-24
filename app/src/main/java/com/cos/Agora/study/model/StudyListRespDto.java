package com.cos.Agora.study.model;

import java.util.Date;

import lombok.Data;

@Data
public class StudyListRespDto {
    // server로부터 받을 데이터

    private int id;             // stduy id
    private int limit;          // 수용인원
    private double distance;    // 사용자와 스터디 거리 차이
    private Date createDate;    // 생성날짜
    private int current;        // 현재인원
    private String title;       // 스터디제목
    private String interest;    // 관심분야
}
