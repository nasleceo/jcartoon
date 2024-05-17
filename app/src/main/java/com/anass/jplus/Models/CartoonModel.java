package com.anass.jplus.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CartoonModel  implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("poster")
    @Expose
    private String poster;
    @SerializedName("year")
    @Expose
    private Integer year;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("gener")
    @Expose
    private String gener;
    @SerializedName("voteavrage")
    @Expose
    private float voteavrage;
    @SerializedName("whereistartcomics")
    @Expose
    private String whereistartcomics;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("story")
    @Expose
    private String story;
    @SerializedName("pin")
    @Expose
    private Integer pin;
    @SerializedName("tmdb_id")
    @Expose
    private String tmdbId;

    public String getCountentType(){

        if (place.contains("movie")){
            return "movie";
        } else if (place.contains("tv")){
            return "tv";
        }else {
            return "comic";
        }

    }

    public int getCountentTypeint(){

        if (place.contains("movie")){
            return 1;
        } else if (place.contains("tv")){
            return 2;
        }else {
            return 3;
        }

    }

    public float getVoteavrage() {
        return voteavrage;
    }

    public void setVoteavrage(float voteavrage) {
        this.voteavrage = voteavrage;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getGener() {
        return gener;
    }

    public void setGener(String gener) {
        this.gener = gener;
    }

    public String getWhereistartcomics() {
        return whereistartcomics;
    }

    public void setWhereistartcomics(String whereistartcomics) {
        this.whereistartcomics = whereistartcomics;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public String getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(String tmdbId) {
        this.tmdbId = tmdbId;
    }
    

}
