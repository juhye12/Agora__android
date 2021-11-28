package com.cos.Agora.study.model;

import androidx.annotation.Nullable;

import lombok.Data;

@Data
public class EvalRespDto {
    private String evalType;
    @Nullable
    private String studyTitle;

    private int studyId;
    private String interest;

    @Nullable
    private int evaluateeId;
    @Nullable
    private String evaluateeNickName;
}
