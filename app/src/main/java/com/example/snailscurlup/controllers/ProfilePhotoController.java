package com.example.snailscurlup.controllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.example.snailscurlup.model.ProfilePhotoModel;

import java.io.IOException;

public class ProfilePhotoController {
    private ProfilePhotoModel ProfPhotoObj;

    public ProfilePhotoController(Context context) {
        ProfPhotoObj = new ProfilePhotoModel(context);
    }

    public boolean hasPhoto() {
        return ProfPhotoObj.userHasProfilePhoto();
    }

    public Uri getPhotoUri() {
        return ProfPhotoObj.getPhotoUri();
    }

    public void savePhoto(Bitmap photoBitmap) throws IOException {
        ProfPhotoObj.savePhoto(photoBitmap);
    }

    public void deletePhoto() throws IOException {
        ProfPhotoObj.deletePhoto();
    }
}

