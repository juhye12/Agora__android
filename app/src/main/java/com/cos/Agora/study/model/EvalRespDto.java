package com.cos.Agora.study.model;

import lombok.Data;

@Data
public class EvalRespDto {
    private String evalType;
    private String studyTitle;

    private int studyId;
    private String interest;

    private int evaluateeId;
    private String evaluateeNickName;
}
