package com.anass.jplus.backend.Responses;

import com.anass.jplus.Models.AdsCenter;
import com.anass.jplus.Models.CartoonModel;
import com.anass.jplus.Models.UpdateModel;
import com.anass.jplus.Models.adsmodel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdsupdateResponse {

    @SerializedName("ads")
    private adsmodel ads;

    @SerializedName("update")
    private UpdateModel update;

    @SerializedName("adscenter")
    private AdsCenter adscenter;

    public adsmodel getAds() {
        return ads;
    }

    public UpdateModel getUpdate() {
        return update;
    }

    public AdsCenter getAdscenter() {
        return adscenter;
    }
}
