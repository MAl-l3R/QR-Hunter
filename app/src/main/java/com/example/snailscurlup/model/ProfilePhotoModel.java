package com.example.snailscurlup.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * ProfilePhotoModel
 *
 * Represents the profile photo for a user.
 *
 * @author AyanB123
 */

public class ProfilePhotoModel {
    private File profilePhotoFile;

    public ProfilePhotoModel(Context context) {
        File storageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String fileName = "user_profile_photo.jpg";
        profilePhotoFile = new File(storageDirectory, fileName);
    }

    /**
     * Confirms whether or not current user has a profile photo.
     * @return Whether user has profile pic (True) or not (False).
     */
    public boolean userHasProfilePhoto() {
        return profilePhotoFile.exists();
    }

    /**
     * Gets the URI of a profile picture.
     * @return The URI of a profile picture.
     */
    public Uri getPhotoUri() {
        return Uri.fromFile(profilePhotoFile);
    }

    /**
     * Takes an image and saves that as the user's profile picture.
     * @param userPhotoBitmap - A file object that references a photo to use
     * @throws IOException - For when image file to be used does not exist
     */
    public void savePhoto(Bitmap userPhotoBitmap) throws IOException {
        OutputStream os = new FileOutputStream(profilePhotoFile);
        userPhotoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        os.flush();
        os.close();
    }

    /**
     * Deletes the current user's profile picture.
     */
    public void deletePhoto() {
        profilePhotoFile.delete();
    }
}


