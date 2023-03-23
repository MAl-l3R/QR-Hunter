package com.example.snailscurlup.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snailscurlup.NavigationHeaderListener;
import com.example.snailscurlup.R;
import com.example.snailscurlup.UserListListener;
import com.example.snailscurlup.controllers.AllUsers;
import com.example.snailscurlup.model.User;
import com.example.snailscurlup.ui.scan.QRGalleryAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment   {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters


    private NavigationHeaderListener NavBarHeadListener; // get reference to main activity so that we can set  Haeder In bit

    private UserListListener userListListener;
    private AllUsers allUsers;

    private User activeUser;



    private FloatingActionButton profileFloaMenuicon;

    private TextView userUsernameField, userTotalScoreField, userCodeScannedField;


    public ProfileFragment() {
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
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
            NavBarHeadListener = (NavigationHeaderListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement UserListListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        RecyclerView QRGallery = view.findViewById(R.id.QRGalleryRecyclerView);

        // retrieve active user
        activeUser = userListListener.getAllUsers().getActiveUser();



        QRGalleryAdapter qrGalleryAdapter = new QRGalleryAdapter(this.getContext(), activeUser.getScannedQrCodes());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);


        QRGallery.setLayoutManager(linearLayoutManager);
        QRGallery.setAdapter(qrGalleryAdapter);

        profileFloaMenuicon = view.findViewById(R.id.profile_floating_menuicon);
        userUsernameField = view.findViewById(R.id.username_fieldprof);
        userTotalScoreField = view.findViewById(R.id.totalscore_fieldprof);
        userCodeScannedField = view.findViewById(R.id.codesscanned_fieldprof);

        userUsernameField.setText(activeUser.getUsername());
        profileFloaMenuicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });


        // Inflate the layo


        return view;
    }

    /*m https://www.section.io/engineering-education/bottom-sheet-dialogs-using-android-studio/ */

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.popup_menu_profile);


        LinearLayout share = bottomSheetDialog.findViewById(R.id.shareLinearLayout);
        LinearLayout upload = bottomSheetDialog.findViewById(R.id.uploadLinearLayout);
        LinearLayout download = bottomSheetDialog.findViewById(R.id.download);


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Share is Clicked", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Upload is Clicked", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Download is Clicked", Toast.LENGTH_LONG).show();
                bottomSheetDialog.dismiss();
            }
        });


        bottomSheetDialog.show();
    }





}
