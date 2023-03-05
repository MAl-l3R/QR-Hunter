package com.example.snailscurlup.ui.scan;

import static android.Manifest.permission.CAMERA;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.snailscurlup.R;
import com.google.zxing.Result;


public class ScanFragment extends Fragment {

    private CodeScanner scanner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        CodeScannerView scannerView = view.findViewById(R.id.scanner_view);
        scanner = new CodeScanner(this.getActivity(), scannerView);

        if (ContextCompat.checkSelfPermission(getActivity(), CAMERA) == PERMISSION_GRANTED) {
            scanner.startPreview();
            scanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull Result result) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
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
        } else {
            Toast.makeText(getContext(), "You need to enable Camera permission from Settings", Toast.LENGTH_SHORT).show();
        }
        return view;
    }
}
