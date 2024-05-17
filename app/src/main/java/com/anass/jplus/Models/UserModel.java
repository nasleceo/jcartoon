package com.anass.jplus.Models;

import android.os.Parcel;
import android.os.Parcelable;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserModel   implements Serializable
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("Subscription")
    @Expose
    private int subscription;
    @SerializedName("profil")
    @Expose
    private String profil;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("userspecial_name")
    @Expose
    private String userspecialName;
    @SerializedName("isverified")
    @Expose
    private int isverified;
    @SerializedName("isadmin")
    @Expose
    private int isadmin;
    @SerializedName("whatcando")
    @Expose
    private String whatcando;
    @SerializedName("account_type")
    @Expose
    private String accountType;
    @SerializedName("noads")
    @Expose
    private int noads;
    @SerializedName("ads_date_start")
    @Expose
    private String adsDateStart;
    @SerializedName("ads_date_end")
    @Expose
    private String adsDateEnd;
    @SerializedName("banned")
    @Expose
    private int banned;
    @SerializedName("insta_url")
    @Expose
    private String instaUrl;
    @SerializedName("face_url")
    @Expose
    private String faceUrl;
    @SerializedName("twitter_url")
    @Expose
    private String twitterUrl;
    @SerializedName("profile_desc")
    @Expose
    private String profileDesc;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("email_verified_at")
    @Expose
    private String emailVerifiedAt;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("rooms_number")
    @Expose
    private int roomsNumber;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubscription() {
        return subscription;
    }

    public void setSubscription(int subscription) {
        this.subscription = subscription;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserspecialName() {
        return userspecialName;
    }

    public void setUserspecialName(String userspecialName) {
        this.userspecialName = userspecialName;
    }

    public int getIsverified() {
        return isverified;
    }

    public void setIsverified(int isverified) {
        this.isverified = isverified;
    }

    public int getIsadmin() {
        return isadmin;
    }

    public void setIsadmin(int isadmin) {
        this.isadmin = isadmin;
    }

    public String getWhatcando() {
        return whatcando;
    }

    public void setWhatcando(String whatcando) {
        this.whatcando = whatcando;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getNoads() {
        return noads;
    }

    public void setNoads(int noads) {
        this.noads = noads;
    }

    public String getAdsDateStart() {
        return adsDateStart;
    }

    public void setAdsDateStart(String adsDateStart) {
        this.adsDateStart = adsDateStart;
    }

    public String getAdsDateEnd() {
        return adsDateEnd;
    }

    public void setAdsDateEnd(String adsDateEnd) {
        this.adsDateEnd = adsDateEnd;
    }

    public int getBanned() {
        return banned;
    }

    public void setBanned(int banned) {
        this.banned = banned;
    }

    public String getInstaUrl() {
        return instaUrl;
    }

    public void setInstaUrl(String instaUrl) {
        this.instaUrl = instaUrl;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getProfileDesc() {
        return profileDesc;
    }

    public void setProfileDesc(String profileDesc) {
        this.profileDesc = profileDesc;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(String emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getRoomsNumber() {
        return roomsNumber;
    }

    public void setRoomsNumber(int roomsNumber) {
        this.roomsNumber = roomsNumber;
    }


}

