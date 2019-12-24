package com.example.capstoneprototype.DataModels;

import java.util.ArrayList;

public class order_data {

    ArrayList<productDetails> pdata;
    String Totalp;

    public String getAddress() {
        return address;
    }

    public order_data() {
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public order_data(ArrayList<productDetails> pdata, String totalp, String address) {
        this.pdata = pdata;
        Totalp = totalp;
        this.address = address;
    }

    String address;

    public ArrayList<productDetails> getPdata() {
        return pdata;
    }

    public void setPdata(ArrayList<productDetails> pdata) {
        this.pdata = pdata;
    }

    public String getTotalp() {
        return Totalp;
    }

    public void setTotalp(String totalp) {
        Totalp = totalp;
    }

    public order_data(ArrayList<productDetails> pdata, String totalp) {
        this.pdata = pdata;
        Totalp = totalp;
    }
}
