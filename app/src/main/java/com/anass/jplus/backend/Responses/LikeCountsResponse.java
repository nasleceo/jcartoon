package com.anass.jplus.backend.Responses;

import com.google.gson.annotations.SerializedName;

public class LikeCountsResponse {
    @SerializedName("cast1count")
    private int cast1Count;

    @SerializedName("cast2count")
    private int cast2Count;

    public int getCast1Count() {
        return cast1Count;
    }

    public int getCast2Count() {
        return cast2Count;
    }
}