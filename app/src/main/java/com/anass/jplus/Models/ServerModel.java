package com.anass.jplus.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerModel implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("lebel")
    @Expose
    private String lebel;
    @SerializedName("movie_id")
    @Expose
    private String movieId;
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
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;


    public ServerModel() {
    }

    protected ServerModel(Parcel in) {
        if (in.readByte( ) == 0) {
            id = null;
        } else {
            id = in.readInt( );
        }
        lebel = in.readString( );
        movieId = in.readString( );
        quality = in.readString( );
        size = in.readString( );
        source = in.readString( );
        url = in.readString( );
        status = in.readString( );
        type = in.readString( );
        createdAt = in.readString( );
        updatedAt = in.readString( );
    }

    public static final Creator<ServerModel> CREATOR = new Creator<ServerModel>( ) {
        @Override
        public ServerModel createFromParcel(Parcel in) {
            return new ServerModel(in);
        }

        @Override
        public ServerModel[] newArray(int size) {
            return new ServerModel[size];
        }
    };

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

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getSize() {
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(lebel);
        dest.writeString(movieId);
        dest.writeString(quality);
        dest.writeString(size);
        dest.writeString(source);
        dest.writeString(url);
        dest.writeString(status);
        dest.writeString(type);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
    }
}
