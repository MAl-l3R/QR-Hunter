package com.example.snailscurlup.ui.map;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

// import com.example.snailscurlup.databinding.ActivityMapsBinding;
import com.example.snailscurlup.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private GoogleMap qrmap;

    FusedLocationProviderClient fusedLocationProviderClient;
    // private ActivityMapsBinding binding;
    double userLongitude = 0;  // Set initial values so that onMapReady() doesn't crash
    double userLatitude = 0;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void fetchCurrentUserLocation() {
        /**
         * Fetches the coordinates of the user's device and stores them in
         * longitude/latitude variables for later use.
         * @params None
         * @returns None
         */
        // Get user location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());

        if (ContextCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {

            fusedLocationProviderClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null) {

                                try {
                                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    // Double latitude = addressList.get(0).getLatitude();
                                    // Double longitude = addressList.get(0).getLongitude();
                                    userLatitude = addressList.get(0).getLatitude();
                                    userLongitude = addressList.get(0).getLongitude();
                                    String address = addressList.get(0).getAddressLine(0);

                                    // TODO: UPLOAD LOCATION TO FIREBASE

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.fetchCurrentUserLocation();
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        /**
         * Called when the Google Map is ready. Sets up the map for the user by
         * populating it with markers to represent QR codes.
         * @param googleMap: The GoogleMap object that represents the map
         *                 widget. Used as a canvas for all the markers.
         */
        // TODO: Seems like markers/camera interactions are not quite working at the moment
        // TODO: DEFINITELY INVESTIGATE LATER
        qrmap = googleMap;
        // Get user position to center the map there
        this.fetchCurrentUserLocation();  // Ensure location is up to date
        LatLng userPosition = new LatLng(this.userLatitude, this.userLongitude);
        // TODO: Load QR code geolocations from database and populate map with them
        // TODO: Also remove this temp marker (based on John Scott Library)
        LatLng temp = new LatLng(53.52181218838866, -113.52282471289865);
        qrmap.addMarker(new MarkerOptions().position(temp).title("John Scott Library"));

        qrmap.moveCamera(CameraUpdateFactory.newLatLng(userPosition));
    }
}