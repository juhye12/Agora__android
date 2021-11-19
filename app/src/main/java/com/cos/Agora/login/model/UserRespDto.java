package com.cos.Agora.login.model;

//import com.cos.daangnapp.main.home.model.PostRespDto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class UserRespDto {
    private int id;
    private String phoneNumber;
    private String nickName;
    private String photo;
    //private List<PostRespDto> posts;
    private Timestamp createDate;
}
