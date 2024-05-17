package com.anass.jplus.Models;

public class Cast {
    private String id;
    private String name;
    private String poster;
    private String cast_id;
    private String posterlogo;
    private String chrachter_id;
    private String type;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPoster() {
        return poster;
    }

    public String getCast_id() {
        return cast_id;
    }

    public String getPosterlogo() {
        return posterlogo;
    }

    public String getChrachter_id() {
        return chrachter_id;
    }

    public String getType() {
        return type;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setCast_id(String cast_id) {
        this.cast_id = cast_id;
    }

    public void setPosterlogo(String posterlogo) {
        this.posterlogo = posterlogo;
    }

    public void setChrachter_id(String chrachter_id) {
        this.chrachter_id = chrachter_id;
    }

    public void setType(String type) {
        this.type = type;
    }
}