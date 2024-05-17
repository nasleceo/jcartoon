package com.anass.jplus.API.Anime;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class DramaModel implements Parcelable {

    int id;
    String title;
    String img;
    String status;
    String type;
    String story;
    String pageinfolink;


    public DramaModel() {

    }

    // Getter Methods


    public int getId() {
        return id;
    }

    public String getPageinfolink() {
        return pageinfolink;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPageinfolink(String pageinfolink) {
        this.pageinfolink = pageinfolink;
    }

    protected DramaModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        img = in.readString();
        status = in.readString();
        type = in.readString();
        story = in.readString();
        pageinfolink = in.readString();
    }

    public static final Creator<DramaModel> CREATOR = new Creator<DramaModel>() {
        @Override
        public DramaModel createFromParcel(Parcel in) {
            return new DramaModel(in);
        }

        @Override
        public DramaModel[] newArray(int size) {
            return new DramaModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getPageLink() {
        return pageinfolink;
    }

    public String getImg() {
        return img;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public String getStory() {
        return story;
    }

    // Setter Methods

    public void setTitle( String title ) {
        this.title = title;
    }

    public void setImg( String img ) {
        this.img = img;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public void setStory( String story ) {
        this.story = story;
    }

    public void setPageLink( String link ) {
        this.pageinfolink = link;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(img);
        dest.writeString(status);
        dest.writeString(type);
        dest.writeString(story);
        dest.writeString(pageinfolink);
    }
}
