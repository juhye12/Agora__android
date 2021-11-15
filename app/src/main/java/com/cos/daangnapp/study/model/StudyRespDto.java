package com.cos.daangnapp.study.model;

import java.util.Date;

import lombok.Data;

@Data
public class StudyRespDto {
    private Integer id; // id
//    private String phoneNumber;
    private String interest;
    private String studyname;
    private Double distance;
    private Date creatDate;
    private Integer maxMember;
    private Integer curMember;
    private String lineup;
}
