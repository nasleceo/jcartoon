package com.anass.jplus.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EpesodModel  implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("lebel")
    @Expose
    private String lebel;
    @SerializedName("imageofepe_url")
    @Expose
    private String imageofepeUrl;
    @SerializedName("tv_id")
    @Expose
    private String tvId;
    @SerializedName("season_id")
    @Expose
    private String seasonId;
    @SerializedName("quality")
    @Expose
    private String quality;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("url_modablaj")
    @Expose
    private String urlModablaj;
    @SerializedName("processed_file")
    @Expose
    private String processedFile;
    @SerializedName("processed")
    @Expose
    private Integer processed;
    @SerializedName("processing_percentage")
    @Expose
    private String processingPercentage;
    @SerializedName("skip_available")
    @Expose
    private Integer skipAvailable;
    @SerializedName("intro_start")
    @Expose
    private String introStart;
    @SerializedName("intro_end")
    @Expose
    private String introEnd;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("video_cartoon")
    @Expose
    private CartoonModel videoCartoon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLebel() {
        return lebel;
    }

    public void setLebel(String lebel) {
        this.lebel = lebel;
    }

    public Object getImageofepeUrl() {
        return imageofepeUrl;
    }

    public void setImageofepeUrl(String imageofepeUrl) {
        this.imageofepeUrl = imageofepeUrl;
    }

    public String getTvId() {
        return tvId;
    }

    public void setTvId(String tvId) {
        this.tvId = tvId;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    public Object getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public Object getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlModablaj() {
        return urlModablaj;
    }

    public void setUrlModablaj(String urlModablaj) {
        this.urlModablaj = urlModablaj;
    }

    public Object getProcessedFile() {
        return processedFile;
    }

    public void setProcessedFile(String processedFile) {
        this.processedFile = processedFile;
    }

    public Integer getProcessed() {
        return processed;
    }

    public void setProcessed(Integer processed) {
        this.processed = processed;
    }

    public Object getProcessingPercentage() {
        return processingPercentage;
    }

    public void setProcessingPercentage(String processingPercentage) {
        this.processingPercentage = processingPercentage;
    }

    public Integer getSkipAvailable() {
        return skipAvailable;
    }

    public void setSkipAvailable(Integer skipAvailable) {
        this.skipAvailable = skipAvailable;
    }

    public String getIntroStart() {
        return introStart;
    }

    public void setIntroStart(String introStart) {
        this.introStart = introStart;
    }

    public String getIntroEnd() {
        return introEnd;
    }

    public void setIntroEnd(String introEnd) {
        this.introEnd = introEnd;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public CartoonModel getVideoCartoon() {
        return videoCartoon;
    }

    public void setVideoCartoon(CartoonModel videoCartoon) {
        this.videoCartoon = videoCartoon;
    }

}



