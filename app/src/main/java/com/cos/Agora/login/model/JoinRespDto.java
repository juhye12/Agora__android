package com.cos.Agora.login.model;

import lombok.Data;

@Data
public class JoinRespDto {
    private String phoneNumber;
    private String association;
    private String age;
    private String sex;
    private String interest;
//    private LocationReqDto locationReqDto;
    private Double latitude;
    private Double longitude;
}
