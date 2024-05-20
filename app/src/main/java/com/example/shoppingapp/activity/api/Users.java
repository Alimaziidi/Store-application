package com.example.shoppingapp.activity.api;

import com.google.gson.annotations.SerializedName;

public class Users {

    private String message;
    private boolean status;

    @SerializedName("data")
    private UserData userData;

    public Users() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
