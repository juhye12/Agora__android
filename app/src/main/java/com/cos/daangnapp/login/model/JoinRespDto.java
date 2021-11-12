package com.cos.daangnapp.login.model;

import lombok.Data;

@Data
public class JoinRespDto {
//    @SerializedName("code")
//    private int code;
//
//    @SerializedName("message")
//    private String message;
    private String phoneNumber;
    private String association;
    private String age;
    private String sex;
    private String interest;
}
