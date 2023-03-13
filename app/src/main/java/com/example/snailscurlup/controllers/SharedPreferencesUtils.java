package com.example.snailscurlup.controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.snailscurlup.model.User;

import java.util.ArrayList;
import java.util.List;

/*https://www.geeksforgeeks.org/shared-preferences-in-android-with-examples */
public class SharedPreferencesUtils {
    private static final String SHARED_PREFERENCES_NAME = "com.example.snailscurlup.preferences";
    private static final String ACTIVE_USER_KEY = "active_user_key";
    private static final String USER_PREFIX = "user_";

    private SharedPreferencesUtils() {
        // private constructor to prevent instantiation
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void addUser(Context context, User user) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String userKey = USER_PREFIX + user.getUsername();
        editor.putString(userKey + "_username", user.getUsername());
        editor.putString(userKey + "_email", user.getEmail());
        editor.putString(userKey + "_phone_number", user.getPhoneNumber());
        editor.putString(userKey + "_profile_photo_path", user.getProfilePhotoPath());
        editor.putString(userKey + "_device_id", user.getDevice_id());
        editor.apply();
    }

    public static void updateUser(Context context, User user) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String userKey = USER_PREFIX + user.getUsername();
        editor.putString(userKey + "_email", user.getEmail());
        editor.putString(userKey + "_phone_number", user.getPhoneNumber());
        editor.putString(userKey + "_profile_photo_path", user.getProfilePhotoPath());
        editor.putString(userKey + "_device_id", user.getDevice_id());
        editor.apply();
    }

    public static void deleteUser(Context context, User user) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String userKey = USER_PREFIX + user.getUsername();
        editor.remove(userKey + "_username");
        editor.remove(userKey + "_email");
        editor.remove(userKey + "_phone_number");
        editor.remove(userKey + "_profile_photo_path");
        editor.remove(userKey + "_device_id");
        editor.apply();
    }

    public static List<User> getAllUsers(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        List<User> users = new ArrayList<>();
        for (String key : sharedPreferences.getAll().keySet()) {
            if (key.startsWith(USER_PREFIX)) {
                String username = sharedPreferences.getString(key + "_username", null);
                String email = sharedPreferences.getString(key + "_email", null);
                String phoneNumber = sharedPreferences.getString(key + "_phone_number", null);
                String profilePhotoPath = sharedPreferences.getString(key + "_profile_photo_path", null);
                String deviceId = sharedPreferences.getString(key + "_device_id", null);
                User user = new User(username, email, phoneNumber, profilePhotoPath, deviceId);
                users.add(user);
            }
        }
        return users;
    }

    public static boolean isUsernameTaken(Context context, String username) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        for (String key : sharedPreferences.getAll().keySet()) {
            if (key.startsWith(USER_PREFIX)) {
                String storedUsername = sharedPreferences.getString(key + "_username", null);
                if (storedUsername != null && storedUsername.equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static User getUserByUsername(Context context, String username) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String userKey = USER_PREFIX + username;
        String storedUsername = sharedPreferences.getString(userKey + "_username", null);
        if (storedUsername != null) {
            String email = sharedPreferences.getString(userKey + "_email", null);
            String phoneNumber = sharedPreferences.getString(userKey + "_phone_number", null);
            String profilePhotoPath = sharedPreferences.getString(userKey + "_profile_photo_path", null);
            String deviceId = sharedPreferences.getString(userKey + "_device_id", null);
            User user = new User(storedUsername, email, phoneNumber, profilePhotoPath, deviceId);
            return user;
        } else {
            return null;
        }
    }

    public static void setActiveUser(Context context, User user) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACTIVE_USER_KEY, user.getUsername());
        editor.apply();
    }

    public static User getActiveUser(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String username = sharedPreferences.getString(ACTIVE_USER_KEY, null);
        if (username != null) {
            return getUserByUsername(context, username);
        } else {
            return null;
        }
    }

    public static void clearActiveUser(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ACTIVE_USER_KEY);
        editor.apply();
    }


}