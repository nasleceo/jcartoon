package com.anass.jplus.backend.Responses;

import com.anass.jplus.Models.CommentModel;
import com.anass.jplus.Models.UserModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuthResponse {

    @SerializedName("followings")
    private int followings;

    @SerializedName("user")
    private UserModel user;

    @SerializedName("followers")
    private int followers;


    public int getFollowings() {
        return followings;
    }

    public UserModel getUser() {
        return user;
    }

    public int getFollowers() {
        return followers;
    }
}



