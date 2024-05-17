package com.anass.jplus.backend.Responses;

import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.CommentModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentsResponse {

    @SerializedName("current_page")
    private int current_page;

    @SerializedName("data")
    private List<CommentModel> comments;

    @SerializedName("total")
    private int total;

    public int getCurrent_page() {
        return current_page;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public int getTotal() {
        return total;
    }
}
