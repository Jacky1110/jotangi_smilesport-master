package com.lafresh.smile2.Repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Merchant implements Parcelable {
    public static final String TAG = Merchant.class.getSimpleName();
    public static final String STATUS_READ = "read";
    public static final String STATUS_DELETE = "delete";

    @SerializedName("store_id")
    private int id = 0;
    private String picture_url = "";
    private String store_name = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    protected Merchant(Parcel in) {
        id = in.readInt();
        picture_url = in.readString();
        store_name = in.readString();
    }

    public static final Creator<Merchant> CREATOR = new Creator<Merchant>() {
        @Override
        public Merchant createFromParcel(Parcel in) {
            return new Merchant(in);
        }

        @Override
        public Merchant[] newArray(int size) {
            return new Merchant[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(picture_url);
        dest.writeString(store_name);
    }
}

