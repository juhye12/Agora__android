package com.cos.daangnapp.studyList.model;

import java.util.Date;

import lombok.Data;

@Data
public class StudyReqDto {
    int id;
    String title;
    String interest;
    Date createDate;
    String limit;
}
