package com.example.snailscurlup.ui.scan;

import androidx.annotation.NonNull;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * Represent lke Single Qrcode object type, has unique hasg,name picture, and number of points based on its data
 * A single Qr code can hav emany instance of it
 */
public class AbstractQR {

    NamesOfQR names = new NamesOfQR();
    private String hash;


    private String URL;
    private String name;
    private int pointsInt;


    // Class costructor uses data define other properties fo Qr code


    public AbstractQR(String data) {
        setHash(data);
        setURL();
        setName();
        setPointsInt();


    }

    public AbstractQR(String hash, String name, Integer Points, String URL) {
        this.hash = hash;
        this.name = name;
        this.pointsInt = Points;
    }


    // Setters and Getter
    public String setHash(String data) {
        this.hash = getHash256(data);
        return this.hash;
    }

    // Getter and Setter for hash
    public String getHash() {
        return hash;
    }

    public void setURL() {
        BigInteger seed = new BigInteger(this.hash, 16);
        String NewURL = "https://picsum.photos/seed/" + seed + "/270";
        this.URL = NewURL;
    }


    // Getter and Setter for URL
    public String getURL() {
        return URL;
    }


    // Getter and Setter for name
    public String getName() {
        return name;
    }

    public void setName() {
        long seed2 = Long.parseLong(this.hash.substring(0, 15), 16);
        Random rand = new Random(seed2);
        int adjIndex = rand.nextInt(names.adjectives.length);
        String newname = names.adjectives[adjIndex];
        this.name = newname;
    }

    // Getter and Setter for pointsInt
    public int getPointsInt() {
        return this.pointsInt;
    }

    public void setPointsInt() {
        // Get and set points
        int newpointsInt = calculateScore(this.hash);
        this.pointsInt = newpointsInt;
    }


    // our hasing function to convert data into a hash
    public String getHash256(@NonNull String data) {
        String hash256;
        try {
            // From https://www.baeldung.com/sha-256-hashing-java
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hashHex = new StringBuilder(2 * hashBytes.length);
            for (int i = 0; i < hashBytes.length; i++) {
                String hex = Integer.toHexString(0xff & hashBytes[i]);
                if (hex.length() == 1) {
                    hashHex.append('0');
                }
                hashHex.append(hex);
            }
            hash256 = hashHex.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Use SHA-256 message digest algorithm");
            hash256 = Integer.toHexString(data.hashCode());
        }
        return hash256;
    }


    // method used set points of a Qr code based on its hash
    public int calculateScore(@NonNull String hex) {
        int score = 0;
        Map<Integer, Integer> digitCount = new HashMap<>();

        for (int i = 0; i < hex.length(); i++) {
            String hexValue = String.valueOf(hex.charAt(i));
            int digit = Integer.parseInt(hexValue, 16);
            // If this is a new digit, add it to the map with a count of 1
            if (!digitCount.containsKey(digit)) {
                digitCount.put(digit, 1);
            }
            // Otherwise, increment the count for this digit
            else {
                digitCount.put(digit, digitCount.get(digit) + 1);
            }
        }


        for (int i = 0; i < digitCount.size(); i++) {
            int digit = (int) digitCount.keySet().toArray()[i];
            int count = digitCount.get(digit);

            if (count >= 2) {
                int repeatScore = (int) Math.pow(2, count - 1);
                score += repeatScore;
            }
        }
        return score;
    }


}

