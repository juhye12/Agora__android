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
    private String nickname;       // 사용자 닉네임
    private String role;    // 사용자 역할
    private int manner;    // 매너
    private int association;  // 소속

}
