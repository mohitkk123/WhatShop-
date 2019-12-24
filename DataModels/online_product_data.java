package com.example.capstoneprototype.DataModels;

import android.os.Parcel;
import android.os.Parcelable;

public class online_product_data implements Parcelable {
    String title,price,pic,link,mrp,mrp_off;

    public online_product_data() {
    }

    protected online_product_data(Parcel in) {
        title = in.readString();
        price = in.readString();
        pic = in.readString();
        link = in.readString();
        mrp = in.readString();
        mrp_off = in.readString();
    }

    public static final Creator<online_product_data> CREATOR = new Creator<online_product_data>() {
        @Override
        public online_product_data createFromParcel(Parcel in) {
            return new online_product_data(in);
        }

        @Override
        public online_product_data[] newArray(int size) {
            return new online_product_data[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getMrp_off() {
        return mrp_off;
    }

    public void setMrp_off(String mrp_off) {
        this.mrp_off = mrp_off;
    }

    public online_product_data(String title, String price, String pic, String link, String mrp, String mrp_off) {
        this.title = title;
        this.price = price;
        this.pic = pic;
        this.link = link;
        this.mrp = mrp;
        this.mrp_off = mrp_off;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(price);
        parcel.writeString(pic);
        parcel.writeString(link);
        parcel.writeString(mrp);
        parcel.writeString(mrp_off);
    }
}
