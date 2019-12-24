package com.example.capstoneprototype.DataModels;

import android.os.Parcel;
import android.os.Parcelable;

public class productDetails implements Parcelable {
    String pname;
    String pdes;
    String pprice;
    String pcat;
    String pimageurl;
    String productId;
    String shop_id;

    public productDetails(String pname, String pdes, String pprice, String pcat, String pimageurl, String productId, String shop_id) {
        this.pname = pname;
        this.pdes = pdes;
        this.pprice = pprice;
        this.pcat = pcat;
        this.pimageurl = pimageurl;
        this.productId = productId;
        this.shop_id = shop_id;
    }



    protected productDetails(Parcel in) {
        pname = in.readString();
        pdes = in.readString();
        pprice = in.readString();
        pcat = in.readString();
        pimageurl = in.readString();
        productId = in.readString();
        shop_id = in.readString();
    }

    public static final Creator<productDetails> CREATOR = new Creator<productDetails>() {
        @Override
        public productDetails createFromParcel(Parcel in) {
            return new productDetails(in);
        }

        @Override
        public productDetails[] newArray(int size) {
            return new productDetails[size];
        }
    };

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPdes() {
        return pdes;
    }

    public void setPdes(String pdes) {
        this.pdes = pdes;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPcat() {
        return pcat;
    }

    public void setPcat(String pcat) {
        this.pcat = pcat;
    }

    public String getPimageurl() {
        return pimageurl;
    }

    public void setPimageurl(String pimageurl) {
        this.pimageurl = pimageurl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public productDetails() {
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pname);
        parcel.writeString(pdes);
        parcel.writeString(pprice);
        parcel.writeString(pcat);
        parcel.writeString(pimageurl);
        parcel.writeString(productId);
        parcel.writeString(shop_id);
    }
}
