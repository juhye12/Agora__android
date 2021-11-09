package com.cos.daangnapp.login.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JoinReqDto {
//    @SerializedName("userAssociation")
//    private String userAssociation;
//
//    @SerializedName("userAge")
//    private String userAge;
//
//    @SerializedName("userSex")
//    private String userSex;
//
//    @SerializedName("userFavorite")
//    private String userFavorite;

    private String association;
    private String age;
    private String Sex;
    private String interest;
}
