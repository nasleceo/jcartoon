package com.anass.jplus.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Latest_APK_Version_Name")
    @Expose
    private String latestAPKVersionName;
    @SerializedName("Latest_APK_Version_Code")
    @Expose
    private String latestAPKVersionCode;
    @SerializedName("APK_File_URL")
    @Expose
    private String aPKFileURL;
    @SerializedName("Whats_new_on_latest_APK")
    @Expose
    private String whatsNewOnLatestAPK;
    @SerializedName("Update_Skipable")
    @Expose
    private Integer updateSkipable;
    @SerializedName("Update_Type")
    @Expose
    private Integer updateType;
    @SerializedName("googleplayAppUpdateType")
    @Expose
    private Integer googleplayAppUpdateType;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("Contact_Email")
    @Expose
    private String contactEmail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLatestAPKVersionName() {
        return latestAPKVersionName;
    }

    public void setLatestAPKVersionName(String latestAPKVersionName) {
        this.latestAPKVersionName = latestAPKVersionName;
    }

    public String getLatestAPKVersionCode() {
        return latestAPKVersionCode;
    }

    public void setLatestAPKVersionCode(String latestAPKVersionCode) {
        this.latestAPKVersionCode = latestAPKVersionCode;
    }

    public String getaPKFileURL() {
        return aPKFileURL;
    }

    public void setaPKFileURL(String aPKFileURL) {
        this.aPKFileURL = aPKFileURL;
    }

    public String getWhatsNewOnLatestAPK() {
        return whatsNewOnLatestAPK;
    }

    public void setWhatsNewOnLatestAPK(String whatsNewOnLatestAPK) {
        this.whatsNewOnLatestAPK = whatsNewOnLatestAPK;
    }

    public Integer getUpdateSkipable() {
        return updateSkipable;
    }

    public void setUpdateSkipable(Integer updateSkipable) {
        this.updateSkipable = updateSkipable;
    }

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
    }

    public Integer getGoogleplayAppUpdateType() {
        return googleplayAppUpdateType;
    }

    public void setGoogleplayAppUpdateType(Integer googleplayAppUpdateType) {
        this.googleplayAppUpdateType = googleplayAppUpdateType;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
