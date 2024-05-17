package com.anass.jplus.DB.seeepe;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SeeEpe")
public class SeeEpe {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "content_id")
    private int content_id;

    @ColumnInfo(name = "source_type")
    private String source_type;

    @ColumnInfo(name = "source_url")
    private String source_url;

    @ColumnInfo(name = "name")
    private String name;


    public SeeEpe(int id, int content_id, String source_type, String source_url, String name) {
        this.id = id;
        this.content_id = content_id;
        this.source_type = source_type;
        this.source_url = source_url;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
