package com.anass.jplus.Models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Cast2Model implements Parcelable {

    public String birthday;
    public String known_for_department;
    public Object deathday;
    public int id;
    public String name;
    public int gender;
    public String biography;
    public double popularity;
    public String place_of_birth;
    public String profile_path;
    public boolean adult;
    public String imdb_id;
    public Object homepage;

    public Cast2Model(String birthday, String known_for_department, Object deathday, int id, String name, int gender, String biography, double popularity, String place_of_birth, String profile_path, boolean adult, String imdb_id, Object homepage) {
        this.birthday = birthday;
        this.known_for_department = known_for_department;
        this.deathday = deathday;
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.biography = biography;
        this.popularity = popularity;
        this.place_of_birth = place_of_birth;
        this.profile_path = profile_path;
        this.adult = adult;
        this.imdb_id = imdb_id;
        this.homepage = homepage;
    }


    protected Cast2Model(Parcel in) {
        birthday = in.readString();
        known_for_department = in.readString();
        id = in.readInt();
        name = in.readString();
        gender = in.readInt();
        biography = in.readString();
        popularity = in.readDouble();
        place_of_birth = in.readString();
        profile_path = in.readString();
        adult = in.readByte() != 0;
        imdb_id = in.readString();
    }

    public static final Creator<Cast2Model> CREATOR = new Creator<Cast2Model>() {
        @Override
        public Cast2Model createFromParcel(Parcel in) {
            return new Cast2Model(in);
        }

        @Override
        public Cast2Model[] newArray(int size) {
            return new Cast2Model[size];
        }
    };

    public String getBirthday() {
        return birthday;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public Object getDeathday() {
        return deathday;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGender() {
        return gender;
    }

    public String getBiography() {
        return biography;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public Object getHomepage() {
        return homepage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(birthday);
        dest.writeString(known_for_department);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(gender);
        dest.writeString(biography);
        dest.writeDouble(popularity);
        dest.writeString(place_of_birth);
        dest.writeString(profile_path);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(imdb_id);
    }
}
