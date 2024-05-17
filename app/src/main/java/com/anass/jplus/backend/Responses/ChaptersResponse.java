package com.anass.jplus.backend.Responses;

import com.anass.jplus.Models.ChapterModel;
import com.anass.jplus.Models.EpesodModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChaptersResponse {


    @SerializedName("data")
    private List<ChapterModel> chapterModels;

    public List<ChapterModel> getchapterModels() {
        return chapterModels;
    }



}
