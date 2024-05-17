package com.anass.jplus.DB.resume_content;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ResumeContent")
public class ResumeContent {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "content_id")
    private int content_id;

    @ColumnInfo(name = "source_type")
    private String source_type;

    @ColumnInfo(name = "source_url")
    private String source_url;

    @ColumnInfo(name = "poster")
    private String poster;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "position")
    private long position;

    @ColumnInfo(name = "duration")
    private long duration;

    @ColumnInfo(name = "content_type")
    private String content_type;



    public ResumeContent(int id, int content_id, String source_type, String source_url, String poster, String name, int year, long position, long duration, String content_type) {
        this.id = id;
        this.content_id = content_id;
        this.source_type = source_type;
        this.source_url = source_url;
        this.poster = poster;
        this.name = name;
        this.year = year;
        this.position = position;
        this.duration = duration;
        this.content_type = content_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContent_id() {
        return content_id;
    }

    public void setContent_id(int content_id) {
        this.content_id = content_id;
    }

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
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

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }


}
