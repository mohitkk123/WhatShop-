package com.example.capstoneprototype.DataModels;

public class shop_order_set {

    productDetails p;
    String name,address;

    public shop_order_set() {
    }

    public productDetails getP() {
        return p;
    }

    public void setP(productDetails p) {
        this.p = p;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public shop_order_set(productDetails p, String name, String address) {
        this.p = p;
        this.name = name;
        this.address = address;
    }
}
