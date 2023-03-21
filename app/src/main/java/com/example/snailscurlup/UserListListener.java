package com.example.snailscurlup;




import com.example.snailscurlup.controllers.AllUsers;
import com.example.snailscurlup.model.User;

public interface UserListListener {
    User getActiveUser();
    AllUsers getAllUsers();
}
