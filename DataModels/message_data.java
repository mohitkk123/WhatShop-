package com.example.capstoneprototype.DataModels;

public class message_data {
    String name,message;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public message_data(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public message_data() {
    }
}
