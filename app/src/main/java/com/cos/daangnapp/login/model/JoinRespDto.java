package com.cos.daangnapp.login.model;

import com.cos.daangnapp.location.model.LocationReqDto;

import lombok.Data;

@Data
public class JoinRespDto {
    private String phoneNumber;
    private String association;
    private String age;
    private String sex;
    private String interest;
    private LocationReqDto locationReqDto;
}
