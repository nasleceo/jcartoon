package com.anass.jplus.API.Respons;

import com.anass.jplus.Models.UserModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {


    @SerializedName("user")
    @Expose()
    private UserModel getuser;

    @SerializedName("token")
    @Expose()
    private String Token;


    public UserModel getGetuser() {
        return getuser;
    }

    public String getToken() {
        return Token;
    }
}
