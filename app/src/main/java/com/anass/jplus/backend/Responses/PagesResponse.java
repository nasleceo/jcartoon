package com.anass.jplus.backend.Responses;

import com.anass.jplus.Models.ChapterModel;
import com.anass.jplus.Models.PagesModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PagesResponse {

    @SerializedName("data")
    private List<PagesModel> chapterModels;

    public List<PagesModel> getChapterModels() {
        return chapterModels;
    }
}
