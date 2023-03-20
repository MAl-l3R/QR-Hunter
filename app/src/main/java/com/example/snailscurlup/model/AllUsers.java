package com.example.snailscurlup.model;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class AllUsers extends Application {
    private List<User> users = new ArrayList<User>();

    public List<User> getAllUsers() {
        return users;
    }

    public void setAllUsers(List<User> users) {
        this.users = users;
    }
}
