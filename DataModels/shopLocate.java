package com.example.capstoneprototype.DataModels;

public class shopLocate {
    double lat,lontt;
    String title;

    public shopLocate() {
    }

    public shopLocate(double lat, double lontt, String title) {
        this.lat = lat;
        this.lontt = lontt;
        this.title = title;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLontt() {
        return lontt;
    }

    public void setLontt(double lontt) {
        this.lontt = lontt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
