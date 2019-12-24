package com.example.capstoneprototype.DataModels;

import android.os.Parcel;
import android.os.Parcelable;

public class shopdetails implements Parcelable {
    String name,shopname,category,email,password,city;
    double lat,longt;
    String storeId;

  String storePicUrl;

    protected shopdetails(Parcel in) {
        name = in.readString();
        shopname = in.readString();
        category = in.readString();
        email = in.readString();
        password = in.readString();
        city = in.readString();
        lat = in.readDouble();
        longt = in.readDouble();
        storeId = in.readString();
        storePicUrl = in.readString();
    }

    public static final Creator<shopdetails> CREATOR = new Creator<shopdetails>() {
        @Override
        public shopdetails createFromParcel(Parcel in) {
            return new shopdetails(in);
        }

        @Override
        public shopdetails[] newArray(int size) {
            return new shopdetails[size];
        }
    };

    public String getStorePicUrl() {
        return storePicUrl;
    }

    public void setStorePicUrl(String storePicUrl) {
        this.storePicUrl = storePicUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongt() {
        return longt;
    }

    public void setLongt(double longt) {
        this.longt = longt;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public shopdetails() {
    }

    public shopdetails(String name, String shopname, String category, String email, String password, String city, double lat, double longt, String storeId,String storePicUrl) {
        this.name = name;
        this.shopname = shopname;
        this.category = category;
        this.email = email;
        this.password = password;
        this.city = city;
        this.lat = lat;
        this.longt = longt;
        this.storeId = storeId;
        this.storePicUrl=storePicUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(shopname);
        parcel.writeString(category);
        parcel.writeString(email);
        parcel.writeString(password);
        parcel.writeString(city);
        parcel.writeDouble(lat);
        parcel.writeDouble(longt);
        parcel.writeString(storeId);
        parcel.writeString(storePicUrl);
    }
}
