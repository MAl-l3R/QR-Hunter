package com.example.snailscurlup.ui.scan;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.app.Activity.RESULT_OK;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import com.example.snailscurlup.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class DecodeFragment extends Fragment {

    View view;
    final int CameraRequestCode = 2;
    TextView photoStatus, geolocationStatus;
    NamesOfQR names = new NamesOfQR();
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_scan_decode, container, false);
        photoStatus = view.findViewById(R.id.photo_status);
        geolocationStatus = view.findViewById(R.id.geolocation_status);

        // Receive the data from the QR code
        getParentFragmentManager().setFragmentResultListener("dataFromQR", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                // Retrieve data from QR code
                String data = result.getString("scanData");

                // Get data's hash value using SHA-256
                String hash = getHash256(data);

                // Get and set image
                ImageView imageView = view.findViewById(R.id.QR_image);
                // since hash value can be very large, we need to use BigInteger instead of int
                BigInteger seed = new BigInteger(hash, 16);
                String URL = "https://picsum.photos/seed/" + String.valueOf(seed) + "/270";
                Picasso.get()
                        .load(URL)
                        .into(imageView);

                // Get and set name
                long seed2 = Long.parseLong(hash.substring(0, 15), 16);
                Random rand = new Random(seed2);
                int adjIndex = rand.nextInt(names.adjectives.length);
                String name = names.adjectives[adjIndex];
                TextView nameView = view.findViewById(R.id.QR_name);
                nameView.setText(name);

                // Get and set points
                int pointsInt = calculateScore(hash);
                TextView pointsView = view.findViewById(R.id.QR_points);
                String points = String.valueOf(pointsInt) + " points";
                pointsView.setText(points);
            }
        });

        // Photo Button
        Button addPhotoButton = view.findViewById(R.id.photo_button);
        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CameraRequestCode);
            }
        });


        // Geolocation Button
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
        Button addGeoButton = view.findViewById(R.id.geolocation_button);
        addGeoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {

                    fusedLocationProviderClient.getLastLocation()
                            .addOnSuccessListener(new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {

                                    if (location != null) {

                                        try {
                                            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                                            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                            Double latitude = addressList.get(0).getLatitude();
                                            Double longitude = addressList.get(0).getLongitude();
                                            String address = addressList.get(0).getAddressLine(0);

                                            // TODO: UPLOAD LOCATION TO FIREBASE



                                            geolocationStatus.setText("Added Successfully!");
                                        } catch (IOException e) {
                                            System.out.println("Exception occurred with location");
                                        }
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getContext(), "You need to enable Location permission from Settings", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Save Button
        Button saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: INSERT FIREBASE CODE


                // Go back to scan fragment
                Fragment fragment = new ScanFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CameraRequestCode && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            photoStatus.setText("Added Successfully!");

            // TODO: UPLOAD PHOTO TO FIREBASE

        }
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

