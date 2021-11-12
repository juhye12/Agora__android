package com.cos.daangnapp.studyList.model;

import com.cos.daangnapp.login.model.UserRespDto;

import java.sql.Timestamp;
import java.util.Date;

import lombok.Data;

@Data
public class StudyRespDto {
    private Integer id; // id
//    private String title; // 제목
//    private String content; // 내용
//    private String price; // 가격
//    private int favorite; // 관심수
//    private int count; //조회수
//    private UserRespDto user;
//    private String category;
//    private String gu;
//    private String dong;
//    private Timestamp createDate;
    private String interest;
    private String studyname;
    private Double distance;
    private Date madeDate;
    private Integer maxMember;
    private Integer curMember;
    private String lineup;
}
