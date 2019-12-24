package com.example.capstoneprototype.DataModels;

public class user_info {
    public String user_name,email,user_password,user_id;

    public user_info(String user_name, String email, String user_password, String user_id) {
        this.user_name = user_name;
        this.email = email;
        this.user_password = user_password;
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getEmail() {
        return email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
