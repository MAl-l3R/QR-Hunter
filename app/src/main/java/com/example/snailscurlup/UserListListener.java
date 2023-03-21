package com.example.snailscurlup;




import com.example.snailscurlup.controllers.AllUsersController;
import com.example.snailscurlup.model.User;

public interface UserListListener {
    User getActiveUser();
    AllUsersController getAllUsers();
}
