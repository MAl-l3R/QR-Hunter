package com.example.snailscurlup.ui.scan;

import static android.app.PendingIntent.getActivity;

import android.graphics.Bitmap;
import android.location.Address;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.concurrent.TimeUnit;


import com.example.snailscurlup.MainActivity;
import com.example.snailscurlup.controllers.AllUsersController;
import com.example.snailscurlup.model.AllUsers;

import com.example.snailscurlup.model.User;



public class  QRCodeDynamicManager {


    /*  Reference for Thread Safe Design Pattern
        Link: https://www.digitalocean.com/community/tutorials/thread-safety-in-java-singleton-classes
        Author: Article posdted by User Named By Pankaj
     */

    NamesOfQR names = new NamesOfQR();
    AllUsers allUsers;

    private static QRCodeDynamicManager instance;
    private AllUsersController userList;


    // assume string i
    private HashMap<String, Object[]> abstractQRCodedata;


    private static final Object lock = new Object();


    private QRCodeDynamicManager() {
        abstractQRCodedata = new HashMap<String, Object[]>();

    }

    public static QRCodeDynamicManager getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new QRCodeDynamicManager();
            }
            return instance;
        }
    }



    public QRCodeInstanceNew addUserScanQRCode(String data, User user, Bitmap scannedQRLogImage, Timestamp scanndQRLogTimeStamp, List<Address> addressList) throws IOException {
        String ScannedQRCodeHash = getHash256(data);
        QRCodeInstanceNew newaQRInstance;



        // if the QR code not exist already create new QRAbstract object for it
        if (abstractQRCodedata.isEmpty() || abstractQRCodedata.containsKey(ScannedQRCodeHash)) {
            AbstractQR newabstractQRType = new AbstractQR(data);
            newaQRInstance = new QRCodeInstanceNew(newabstractQRType,user, scannedQRLogImage, scanndQRLogTimeStamp,addressList.get(0).getAddressLine(0));

            ArrayList<QRCodeInstanceNew> QRTypeAllInstances = new ArrayList<QRCodeInstanceNew>();
            QRTypeAllInstances.add(newaQRInstance);

            abstractQRCodedata.put(ScannedQRCodeHash, new Object[] {newabstractQRType, QRTypeAllInstances});




        } else {
            Object[] QrCodeobject= abstractQRCodedata.get(ScannedQRCodeHash);

            newaQRInstance = new QRCodeInstanceNew((AbstractQR) QrCodeobject[0],user, scannedQRLogImage, scanndQRLogTimeStamp,addressList.get(0).getAddressLine(0));

            ArrayList<QRCodeInstanceNew> QRTypeAllInstances = (ArrayList<QRCodeInstanceNew>) QrCodeobject[1];
            QRTypeAllInstances.add(newaQRInstance);
            abstractQRCodedata.put(ScannedQRCodeHash, new Object[] {QrCodeobject[0], QRTypeAllInstances});



        }

        return  newaQRInstance;

    }

    public QRCodeInstanceNew testaddUserScanQRCode(AbstractQR QrType, User user, Bitmap scannedQRLogImage, Timestamp scanndQRLogTimeStamp, List<Address> addressList) throws IOException {
        String ScannedQRCodeHash = QrType.getHash();
        QRCodeInstanceNew newaQRInstance;



        // if the QR code not exist already create new QRAbstract object for it
        if (abstractQRCodedata.isEmpty() || abstractQRCodedata.containsKey(ScannedQRCodeHash)) {
            AbstractQR newabstractQRType = QrType;
            newaQRInstance = new QRCodeInstanceNew(newabstractQRType,user, scannedQRLogImage, scanndQRLogTimeStamp,addressList.get(0).getAddressLine(0));

            ArrayList<QRCodeInstanceNew> QRTypeAllInstances = new ArrayList<QRCodeInstanceNew>();
            QRTypeAllInstances.add(newaQRInstance);

            abstractQRCodedata.put(ScannedQRCodeHash, new Object[] {newabstractQRType, QRTypeAllInstances});




        } else {
            Object[] QrCodeobject= abstractQRCodedata.get(ScannedQRCodeHash);

            newaQRInstance = new QRCodeInstanceNew((AbstractQR) QrCodeobject[0],user, scannedQRLogImage, scanndQRLogTimeStamp,addressList.get(0).getAddressLine(0));

            ArrayList<QRCodeInstanceNew> QRTypeAllInstances = (ArrayList<QRCodeInstanceNew>) QrCodeobject[1];
            QRTypeAllInstances.add(newaQRInstance);
            abstractQRCodedata.put(ScannedQRCodeHash, new Object[] {QrCodeobject[0], QRTypeAllInstances});



        }

        return  newaQRInstance;

    }

    public String getHash256(@NonNull String data) {
        String hash256;
        try {
            // From https://www.baeldung.com/sha-256-hashing-java
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hashHex = new StringBuilder(2 * hashBytes.length);
            for (int i = 0; i < hashBytes.length; i++) {
                String hex = Integer.toHexString(0xff & hashBytes[i]);
                if(hex.length() == 1) {
                    hashHex.append('0');
                }
                hashHex.append(hex);
            }
            hash256 = hashHex.toString();
        }
        catch (NoSuchAlgorithmException e) {
            System.err.println("Use SHA-256 message digest algorithm");
            hash256 = Integer.toHexString(data.hashCode());
        }
        return hash256;
    }





}
