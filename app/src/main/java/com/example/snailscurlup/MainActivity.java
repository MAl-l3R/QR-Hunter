package com.example.snailscurlup;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.snailscurlup.databinding.ActivityMainBinding;
import com.example.snailscurlup.ui.leaderboard.LeaderboardFragment;
import com.example.snailscurlup.ui.map.MapFragment;
import com.example.snailscurlup.ui.profile.ProfileFragment;
import com.example.snailscurlup.ui.scan.ScanFragment;
import com.example.snailscurlup.ui.search.SearchFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ActivityResultLauncher<String[]> PermissionResultLauncher;
    public boolean CameraPermission = false;
    public boolean LocationPermission = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PermissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {

                if (result.get(CAMERA) != null){

                    CameraPermission = result.get(CAMERA);

                }

                if (result.get(ACCESS_FINE_LOCATION) != null){

                    LocationPermission = result.get(ACCESS_FINE_LOCATION);

                }
            }
        });

        requestPermissions();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new ProfileFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    binding.fragmentName.setText("My Profile");
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.search:
                    binding.fragmentName.setText("Search");
                    replaceFragment(new SearchFragment());
                    break;
                case R.id.scan:
                    binding.fragmentName.setText("Scan");
                    replaceFragment(new ScanFragment());
                    break;
                case R.id.map:
                    // Switch to Map activity
//                    Intent switchToMapIntent = new Intent(this, com.example.snailscurlup.MapActivity.class);
//                    startActivity(switchToMapIntent);
                    binding.fragmentName.setText("Map");
                    replaceFragment(new MapFragment());
                    break;
                case R.id.leaderboard:
                    binding.fragmentName.setText("Leaderboard");
                    replaceFragment(new LeaderboardFragment());
                    break;
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    private void requestPermissions(){
        List<String> permissionRequests = new ArrayList<String>();

        if (ContextCompat.checkSelfPermission(this, CAMERA) != PERMISSION_GRANTED){

            permissionRequests.add(CAMERA);

        }

        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED){

            permissionRequests.add(ACCESS_FINE_LOCATION);

        }

        if (!permissionRequests.isEmpty()){

            PermissionResultLauncher.launch(permissionRequests.toArray(new String[0]));

        }


    }

}
