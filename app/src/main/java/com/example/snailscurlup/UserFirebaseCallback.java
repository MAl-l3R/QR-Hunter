package com.example.snailscurlup;

import com.example.snailscurlup.model.User;

/*https://stackoverflow.com/questions/50041645/how-to-use-callback-interface-correctly-to-get-a-list-of-values-from-ondatachang */

public interface  UserFirebaseCallback {
    void onUserReceived(User user);
    void onUserNotFound();
    void onGetUserError(Exception exception);
}