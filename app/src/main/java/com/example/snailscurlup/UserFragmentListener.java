package com.example.snailscurlup;

import com.example.snailscurlup.model.User;

import java.util.List;

public interface UserFragmentListener {
    void onActiveUserChanged(User activeUser);
    //void onAllUsersChanged(List<User> users);
}
