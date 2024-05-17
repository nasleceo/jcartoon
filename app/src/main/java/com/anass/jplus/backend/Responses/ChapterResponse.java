package com.anass.jplus.backend.Responses;

import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.ChapterModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChapterResponse {



    @SerializedName("data")
    private List<ChapterModel> chapterModels;

    public List<ChapterModel> getChapterModels() {
        return chapterModels;
    }
}
