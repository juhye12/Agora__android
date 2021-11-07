package com.cos.daangnapp.login.model;

public class JoinData {
    @SerializedName("userAssociation")
    private String userAssociation;

    @SerializedName("userAge")
    private String userAge;

    @SerializedName("userSex")
    private String userSex;

    @SerializedName("userFavorite")
    private String userFavorite;

    public JoinData(String userAssociation, String userAge, String userSex,String userFavorite) {
        this.userAssociation = userAssociation;
        this.userAge = userAge;
        this.userSex = userSex;
        this.userFavorite = userFavorite;
    }
}
