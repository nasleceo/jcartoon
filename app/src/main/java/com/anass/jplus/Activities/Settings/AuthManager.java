package com.anass.jplus.Activities.Settings;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.anass.jplus.Models.UserModel;


public class AuthManager {


    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;


    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String USERSPECIALNAME = "USERSPECIALNAME";
    public static final String ID = "ID";
    public static final String Subscription = "Subscription";
    public static final String profil = "profil";
    public static final String isadmin = "isadmin";
    public static final String account_type = "account_type";
    public static final String noads = "noads";
    public static final String ads_date_start = "ads_date_start";
    public static final String ads_date_end = "ads_date_end";
    public static final String banned = "banned";
    public static final String insta_url = "insta_url";
    public static final String face_url = "face_url";
    public static final String twitter_url = "twitter_url";
    public static final String profile_desc = "profile_desc";
    public static final String device_id = "device_id";
    public static final String country = "country";
    public static final String rooms_number = "rooms_number";
    public static final String isverified = "isverified";



    @SuppressLint("CommitPrefEdits")
    public AuthManager(SharedPreferences prefs){
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public void saveSettings(UserModel userAuthInfo){

        editor.putString(NAME, userAuthInfo.getName()).commit();
        editor.putString(EMAIL, userAuthInfo.getEmail()).commit();
        editor.putString(USERSPECIALNAME, userAuthInfo.getUserspecialName()).commit();
        editor.putInt(Subscription, userAuthInfo.getSubscription()).commit();
        editor.putString(profil, userAuthInfo.getProfil()).commit();
        editor.putInt(isadmin, userAuthInfo.getIsadmin()).commit();
        editor.putString(account_type, userAuthInfo.getAccountType()).commit();
        editor.putInt(noads, userAuthInfo.getNoads()).commit();
        editor.putString(ads_date_start, userAuthInfo.getAdsDateStart()).commit();
        editor.putString(ads_date_end, userAuthInfo.getAdsDateEnd()).commit();
        editor.putInt(banned, userAuthInfo.getBanned()).commit();
        editor.putString(insta_url, userAuthInfo.getInstaUrl()).commit();
        editor.putString(face_url, userAuthInfo.getFaceUrl()).commit();
        editor.putString(twitter_url, userAuthInfo.getTwitterUrl()).commit();
        editor.putString(profile_desc, userAuthInfo.getProfileDesc()).commit();
        editor.putString(device_id, userAuthInfo.getDeviceId()).commit();
        editor.putString(country, userAuthInfo.getCountry()).commit();
        editor.putInt(rooms_number, userAuthInfo.getRoomsNumber()).commit();
        editor.putInt(isverified, userAuthInfo.getIsverified()).commit();
        editor.putInt(ID, userAuthInfo.getId()).commit();
        editor.apply();
    }


    public void deleteAuth(){
        editor.remove(NAME).commit();
        editor.remove(EMAIL).commit();
        editor.remove(USERSPECIALNAME).commit();
        editor.remove(ID).commit();
        editor.remove(Subscription).commit();
        editor.remove(profil).commit();
        editor.remove(isadmin).commit();
        editor.remove(account_type).commit();
        editor.remove(noads).commit();
        editor.remove(ads_date_start).commit();
        editor.remove(ads_date_end).commit();
        editor.remove(banned).commit();
        editor.remove(insta_url).commit();
        editor.remove(face_url).commit();
        editor.remove(twitter_url).commit();
        editor.remove(profile_desc).commit();
        editor.remove(device_id).commit();
        editor.remove(country).commit();
        editor.remove(rooms_number).commit();
        editor.remove(isverified).commit();
    }

    public UserModel getUserInfo() {
        UserModel userAuthInfo = new UserModel();
        userAuthInfo.setName(prefs.getString(NAME, null));
        userAuthInfo.setEmail(prefs.getString(EMAIL, null));
        userAuthInfo.setUserspecialName(prefs.getString(USERSPECIALNAME, null));
        userAuthInfo.setId(prefs.getInt(ID, 0));
        userAuthInfo.setSubscription(prefs.getInt(Subscription, 0));
        userAuthInfo.setProfil(prefs.getString(profil, null));
        userAuthInfo.setIsadmin(prefs.getInt(isadmin, 0));
        userAuthInfo.setAccountType(prefs.getString(account_type, null));
        userAuthInfo.setNoads(prefs.getInt(noads, 0));
        userAuthInfo.setAdsDateStart(prefs.getString(ads_date_start, null));
        userAuthInfo.setAdsDateEnd(prefs.getString(ads_date_end, null));
        userAuthInfo.setBanned(prefs.getInt(banned, 0));
        userAuthInfo.setInstaUrl(prefs.getString(insta_url, null));
        userAuthInfo.setFaceUrl(prefs.getString(face_url, null));
        userAuthInfo.setTwitterUrl(prefs.getString(twitter_url, null));
        userAuthInfo.setProfileDesc(prefs.getString(profile_desc, null));
        userAuthInfo.setDeviceId(prefs.getString(device_id, null));
        userAuthInfo.setCountry(prefs.getString(country, null));
        userAuthInfo.setRoomsNumber(prefs.getInt(rooms_number, 0));
        userAuthInfo.setIsverified(prefs.getInt(isverified, 0));
        return userAuthInfo;
    }


}
