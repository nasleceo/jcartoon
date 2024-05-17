package com.anass.jplus.Models;

public class ContinuePlayingList {
    int id;
    int contentID;
    String name;
    int year;
    String poster;
    String sourceType;
    String sourceUrl;
    String type;
    String content_type;
    long position;
    long duration;

    public ContinuePlayingList(int id, int contentID, String name, int year, String poster, String sourceType,
                               String sourceUrl, String type, String content_type, long position, long duration) {
        this.id = id;
        this.contentID = contentID;
        this.name = name;
        this.year = year;
        this.poster = poster;
        this.sourceType = sourceType;
        this.sourceUrl = sourceUrl;
        this.type = type;
        this.content_type = content_type;
        this.position = position;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContentID() {
        return contentID;
    }

    public void setContentID(int contentID) {
        this.contentID = contentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}