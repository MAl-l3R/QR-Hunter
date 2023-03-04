package com.example.snailscurlup.ui.scan;

import static android.Manifest.permission.CAMERA;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.snailscurlup.R;
import com.google.zxing.Result;


public class ScanFragment extends Fragment {

    private CodeScanner scanner;
    boolean CameraPermission = false;
    final int CameraRequestCode = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        CodeScannerView scannerView = view.findViewById(R.id.scanner_view);

        scanner = new CodeScanner(this.getActivity(), scannerView);
        getCameraPermission();

        if (CameraPermission) {
            scanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull Result result) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            //Toast.makeText(getActivity(), result.getText(), Toast.LENGTH_LONG).show();
                            //Send data to decode fragment
                            Bundle data = new Bundle();
                            data.putString("scanData", result.getText());
                            getParentFragmentManager().setFragmentResult("dataFromQR", data);

                            // Go to decode fragment
                            Fragment fragment = new DecodeFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayout, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
                }
            });
        }
        return view;
    }

    private void getCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this.getActivity(), CAMERA) != PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this.getActivity(), new String[] {CAMERA}, CameraRequestCode);

        } else {
            CameraPermission = true;
            scanner.startPreview();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CameraRequestCode) {

            if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {

                CameraPermission = true;
                scanner.startPreview();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
