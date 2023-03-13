package com.example.snailscurlup.ui.scan;

import android.graphics.Bitmap;

public class QRCodeInstance
    private String location;
    private Bitmap photo;

    public QrCodeInstance(String data,Double latitude,Double longitude,boolean imageLocation) {


    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

}
