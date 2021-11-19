package com.cos.Agora.study.model;

import lombok.Data;

@Data
public class StudyListReqDto {
    // server로 보낼 데이터
    private String phoneNumber; // 사용자 핸드폰 번호
    private String interest;    // 관심분야
    private String lineup;
}
