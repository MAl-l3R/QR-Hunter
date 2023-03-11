package com.example.snailscurlup.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;





public class ProfilePhotoModel {
    private File profilePhotoFile;

    public ProfilePhotoModel(Context context) {
        File storageDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String fileName = "user_profile_photo.jpg";
        profilePhotoFile = new File(storageDirectory, fileName);
    }

    public boolean userHasProfilePhoto() {
        return profilePhotoFile.exists();
    }

    public Uri getPhotoUri() {
        return Uri.fromFile(profilePhotoFile);
    }

    public void savePhoto(Bitmap userPhotoBitmap) throws IOException {
        OutputStream os = new FileOutputStream(profilePhotoFile);
        userPhotoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        os.flush();
        os.close();
    }

    public void deletePhoto() {
        profilePhotoFile.delete();
    }
}


