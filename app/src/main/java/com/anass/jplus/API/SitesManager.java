package com.anass.jplus.API;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.anasskikanime.models.GenersModel;


public class SitesManager {



    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;


    public static final String SITETITLE = "SITETITLE";



    @SuppressLint("CommitPrefEdits")
    public SitesManager(SharedPreferences prefs){
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public boolean saveSites(GenersModel userAuthInfo){

        editor.putString(SITETITLE, userAuthInfo.getGenertitle()).commit();

        editor.apply();

       return true;
    }


    public void deleteSite(){
        editor.remove(SITETITLE).commit();

    }

    public GenersModel getSite() {
        GenersModel userAuthInfo = new GenersModel();
        userAuthInfo.setGenertitle(prefs.getString(SITETITLE, null));

        return userAuthInfo;
    }

}
