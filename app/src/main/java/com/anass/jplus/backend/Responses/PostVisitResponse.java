package com.anass.jplus.backend.Responses;

import com.anass.jplus.Models.PostModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostVisitResponse {


    @SerializedName("post")
    private PostModel post;

    @SerializedName("visit_count")
    private int visit_count;

    public PostModel getPost() {
        return post;
    }

    public int getVisit_count() {
        return visit_count;
    }
}
