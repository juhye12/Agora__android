package com.cos.Agora.study.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class DetailRespDto {
    private long userId;            // 사용자 id
    private String nickName;        // 사용자 닉네임
    private int role;               // 사용자 역할 1:그룹장, 0:그룹원
    private int manner;             // 매너
    private String association;     // 소속
    private double latitude;
    private double longitude;

}
