package com.example.snailscurlup.ui.scan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snailscurlup.R;
import com.example.snailscurlup.UserListListener;
import com.example.snailscurlup.controllers.AllUsersController;
import com.example.snailscurlup.model.AllUsers;
import com.example.snailscurlup.model.User;
import com.example.snailscurlup.ui.startup.StartUpActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QRCodeInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QRCodeInfoFragment extends Fragment   {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters



    private UserListListener userListListener;

    AllUsers allUsers;
    User activeUser;




    private TextView QRCodeName, QRCodePoints;

    private ImageView QRCodeImage;


    private AbstractQR  selectedAbstractQR;


    public QRCodeInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onResume() {
        super.onResume();
        activeUser = userListListener.getAllUsers().getActiveUser();


    }
    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPause();
        activeUser = userListListener.getAllUsers().getActiveUser();
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QRCodeInfoFragment newInstance(String param1, String param2) {
        QRCodeInfoFragment fragment = new QRCodeInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UserListListener) {
            userListListener = (UserListListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement UserListListener");
        }
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qr_infopage, container, false);



        allUsers = (AllUsers) getActivity().getApplicationContext();
        allUsers.init();

        // Only wait if active user is null at the moment
        if (allUsers.getActiveUser() == null) {
            // Wait for thread to finish
            // allUsers list is initializing...
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // retrieve active user
        if (allUsers.getActiveUser() != null) {
            activeUser = allUsers.getActiveUser();
        } else {
            activeUser = new User();
        }


        getSelectedAbstractQR();

        /**** commented out try for new AbstractQr implementation
        QrGalleryAdapter qrGalleryAdapter = new QrGalleryAdapter(this.getContext(), activeUser.getScannedQrCodes());
        ******/

        /****** new AbstractQr implementation ****/



        QRCodeImage = view.findViewById(R.id.qrcode_image);
        QRCodeName = view.findViewById(R.id.qrcode_name);
        QRCodePoints = view.findViewById(R.id.qrcode_points);


        QRCodeName.setText(selectedAbstractQR.getName());
        QRCodePoints.setText(String.valueOf(selectedAbstractQR.getPointsInt().toString()));
        Picasso.get()
                .load(selectedAbstractQR.getURL())
                .into(QRCodeImage);





        return view;
    }

    private void getSelectedAbstractQR() {
        String QRhash;
        String QRURL;
        String QRname;
        Integer QRpointsInt;

        Bundle bundle = getArguments();
        if (bundle != null) {
            QRhash = bundle.getString("hash");
            QRURL = bundle.getString("url");
            QRname= bundle.getString("name");
            QRpointsInt= bundle.getInt("points");

            selectedAbstractQR = new AbstractQR(QRhash,QRname, QRpointsInt,QRURL);
        }




    }

    /*m https://www.section.io/engineering-education/bottom-sheet-dialogs-using-android-studio/ */












    }





