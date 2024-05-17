package com.anass.jplus.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CountinuModel  implements Parcelable {


    public String id;
    private String title;
    private String poster;
    private String cover;
    private String year;
    private String place;
    private String gener;
    private String tmdb_id;
    private String country;
    private String age;
    private String story;
    private long views;
    private Integer Season_id;
    private String Season_name;
    private String Season_episodes;
    private String Season_status;
    private String Season_createdAt;
    private String Season_updatedAt;
    private Integer server_id;
    private String server_lebel;
    private String server_quality;
    private String server_size;
    private String server_source;
    private String server_url;
    private String server_status;
    private String server_type;

    private String position;
    private String Current_position;
    private String full_duration;

    public CountinuModel() {
    }


    protected CountinuModel(Parcel in) {
        id = in.readString( );
        title = in.readString( );
        poster = in.readString( );
        cover = in.readString( );
        year = in.readString( );
        place = in.readString( );
        gener = in.readString( );
        tmdb_id = in.readString( );
        country = in.readString( );
        age = in.readString( );
        story = in.readString( );
        views = in.readLong( );
        if (in.readByte( ) == 0) {
            Season_id = null;
        } else {
            Season_id = in.readInt( );
        }
        Season_name = in.readString( );
        Season_episodes = in.readString( );
        Season_status = in.readString( );
        Season_createdAt = in.readString( );
        Season_updatedAt = in.readString( );
        if (in.readByte( ) == 0) {
            server_id = null;
        } else {
            server_id = in.readInt( );
        }
        server_lebel = in.readString( );
        server_quality = in.readString( );
        server_size = in.readString( );
        server_source = in.readString( );
        server_url = in.readString( );
        server_status = in.readString( );
        server_type = in.readString( );
        position = in.readString( );
        full_duration = in.readString( );
        Current_position = in.readString( );
    }

    public static final Creator<CountinuModel> CREATOR = new Creator<CountinuModel>( ) {
        @Override
        public CountinuModel createFromParcel(Parcel in) {
            return new CountinuModel(in);
        }

        @Override
        public CountinuModel[] newArray(int size) {
            return new CountinuModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getCover() {
        return cover;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCurrent_position() {
        return Current_position;
    }

    public void setCurrent_position(String current_position) {
        Current_position = current_position;
    }

    public String getFull_duration() {
        return full_duration;
    }

    public void setFull_duration(String full_duration) {
        this.full_duration = full_duration;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public String getTmdb_id() {
        return tmdb_id;
    }

    public void setTmdb_id(String tmdb_id) {
        this.tmdb_id = tmdb_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public Integer getSeason_id() {
        return Season_id;
    }

    public void setSeason_id(Integer season_id) {
        Season_id = season_id;
    }

    public String getSeason_name() {
        return Season_name;
    }

    public void setSeason_name(String season_name) {
        Season_name = season_name;
    }

    public String getSeason_episodes() {
        return Season_episodes;
    }

    public void setSeason_episodes(String season_episodes) {
        Season_episodes = season_episodes;
    }

    public String getSeason_status() {
        return Season_status;
    }

    public void setSeason_status(String season_status) {
        Season_status = season_status;
    }

    public String getSeason_createdAt() {
        return Season_createdAt;
    }

    public void setSeason_createdAt(String season_createdAt) {
        Season_createdAt = season_createdAt;
    }

    public String getSeason_updatedAt() {
        return Season_updatedAt;
    }

    public void setSeason_updatedAt(String season_updatedAt) {
        Season_updatedAt = season_updatedAt;
    }

    public Integer getServer_id() {
        return server_id;
    }

    public void setServer_id(Integer server_id) {
        this.server_id = server_id;
    }

    public String getServer_lebel() {
        return server_lebel;
    }

    public void setServer_lebel(String server_lebel) {
        this.server_lebel = server_lebel;
    }

    public String getServer_quality() {
        return server_quality;
    }

    public void setServer_quality(String server_quality) {
        this.server_quality = server_quality;
    }

    public String getServer_size() {
        return server_size;
    }

    public void setServer_size(String server_size) {
        this.server_size = server_size;
    }

    public String getServer_source() {
        return server_source;
    }

    public void setServer_source(String server_source) {
        this.server_source = server_source;
    }

    public String getServer_url() {
        return server_url;
    }

    public void setServer_url(String server_url) {
        this.server_url = server_url;
    }

    public String getServer_status() {
        return server_status;
    }

    public void setServer_status(String server_status) {
        this.server_status = server_status;
    }

    public String getServer_type() {
        return server_type;
    }

    public void setServer_type(String server_type) {
        this.server_type = server_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(poster);
        dest.writeString(cover);
        dest.writeString(year);
        dest.writeString(place);
        dest.writeString(gener);
        dest.writeString(tmdb_id);
        dest.writeString(country);
        dest.writeString(age);
        dest.writeString(story);
        dest.writeLong(views);
        if (Season_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(Season_id);
        }
        dest.writeString(Season_name);
        dest.writeString(Season_episodes);
        dest.writeString(Season_status);
        dest.writeString(Season_createdAt);
        dest.writeString(Season_updatedAt);
        if (server_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(server_id);
        }
        dest.writeString(server_lebel);
        dest.writeString(server_quality);
        dest.writeString(server_size);
        dest.writeString(server_source);
        dest.writeString(server_url);
        dest.writeString(server_status);
        dest.writeString(server_type);
        dest.writeString(position);
        dest.writeString(Current_position);
        dest.writeString(full_duration);
    }
}
