package com.anass.jplus.backend.Responses;

import com.anass.jplus.Models.CommentModel;
import com.anass.jplus.Models.ReplayModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReplayResponse {

    @SerializedName("current_page")
    private int current_page;

    @SerializedName("data")
    private List<ReplayModel> replayModels;

    @SerializedName("total")
    private int total;

    public int getCurrent_page() {
        return current_page;
    }

    public List<ReplayModel> getReplayModels() {
        return replayModels;
    }

    public int getTotal() {
        return total;
    }
}
