package com.example.snailscurlup.ui.scan;


import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.snailscurlup.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;


/**
 * Represent lke Single Qrcode object type, has unique hasg,name picture, and number of points based on its data
 * A single Qr code can hav emany instance of it
 */
public class QRCodeInstanceNew {

    private final AbstractQR QrCodeAbstractType;

    private final Timestamp scanQRLogTimeStamp;

    private Bitmap scanQRLogImage;

    private Context context;


    private String scanQRLogLocation;

    private User scanQRInstanceUser;

    QRCodeInstanceNew(AbstractQR scannedAbstractQRCodeType, User user, Bitmap scannedQRLogImage, Timestamp scanndQRLogTimeStamp, String ScanedQRLogLocation) {
        this.QrCodeAbstractType = scannedAbstractQRCodeType;
        this.scanQRLogImage = scannedQRLogImage;
        this.scanQRInstanceUser = user;
        this.scanQRLogTimeStamp = scanndQRLogTimeStamp;

        // call extra method handle IOException e with try and catch block
        setScanQRComponent(ScanedQRLogLocation, scannedQRLogImage);


    }


    public void setScanQRComponent(String ScanedQRLogLocation, Bitmap scannedQRLogImage) {

        try {
            // call a method that may throw an IOException
            setScanQRLogLocation(ScanedQRLogLocation);
            setScanQRLogImage(scannedQRLogImage);
        } catch (IOException e) {
            // handle the exception
            System.err.println("An error occurred: " + e.getMessage());
        }

    }


    public Timestamp getScanQRLogTimeStamp() {
        return scanQRLogTimeStamp;
    }

    public Bitmap getScanQRLogImage() {
        return scanQRLogImage;
    }


    public void setScanQRLogImage(Bitmap QRLogImage) throws IOException {
        if (scanQRLogImage == null) {
            try {
                AssetManager assetManager = context.getApplicationContext().getAssets();


                InputStream inputStream = assetManager.open("defalt_QR_logphoto");
                // Decode the input stream into a Bitmap object
                scanQRLogImage = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            this.scanQRLogImage = QRLogImage;
        }
    }


    public void setScanQRLogLocation(String QRLoglocation) {
        if (QRLoglocation == null) {
            this.scanQRLogLocation = "Not Saved";
        } else {
            this.scanQRLogLocation = QRLoglocation;
        }


    }


    // get basic iformation about QR code
    public AbstractQR getQRTypeDetail() {
        return QrCodeAbstractType;
    }


    public User getScanQRInstanceUser() {
        return scanQRInstanceUser;
    }

    public void setScanQRInstanceUser(User scanQRInstanceUser) {
        this.scanQRInstanceUser = scanQRInstanceUser;
    }

    // Class costructor uses data define other properties fo Qr code


}







