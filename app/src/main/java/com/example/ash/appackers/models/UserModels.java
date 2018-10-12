package com.example.ash.appackers.models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModels implements Parcelable{

    private String email;
    private String user_id;
    private String username;
    private String avatar;

    public UserModels(String email, String user_id, String username, String avatar) {
        this.email = email;
        this.user_id = user_id;
        this.username = username;
        this.avatar = avatar;
    }

    public UserModels() {

    }

    protected UserModels(Parcel in) {
        email = in.readString();
        user_id = in.readString();
        username = in.readString();
        avatar = in.readString();
    }

    public static final Creator<UserModels> CREATOR = new Creator<UserModels>() {
        @Override
        public UserModels createFromParcel(Parcel in) {
            return new UserModels(in);
        }

        @Override
        public UserModels[] newArray(int size) {
            return new UserModels[size];
        }
    };

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public static Creator<UserModels> getCREATOR() {
        return CREATOR;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserModels{" +
                "email='" + email + '\'' +
                ", user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(user_id);
        dest.writeString(username);
        dest.writeString(avatar);
    }
}

