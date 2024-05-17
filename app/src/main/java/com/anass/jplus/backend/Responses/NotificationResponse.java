package com.anass.jplus.backend.Responses;

import com.anass.jplus.Models.NotificationModel;
import com.anass.jplus.Models.PostModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotificationResponse {


    @SerializedName("current_page")
    private int current_page;

    @SerializedName("data")
    private List<NotificationModel> getnotifactions;

    @SerializedName("total")
    private int total;

    public int getCurrent_page() {
        return current_page;
    }

    public List<NotificationModel> getGetnotifactions() {
        return getnotifactions;
    }

    public int getTotal() {
        return total;
    }
}


