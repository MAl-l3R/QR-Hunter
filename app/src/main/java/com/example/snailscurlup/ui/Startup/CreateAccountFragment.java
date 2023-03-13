package com.example.snailscurlup.ui.Startup;


import android.content.Context;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.snailscurlup.NavigationHeaderListener;
import com.example.snailscurlup.R;
import com.example.snailscurlup.UserListListener;
import com.example.snailscurlup.model.User;
import com.example.snailscurlup.ui.profile.ProfileFragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.example.snailscurlup.ui.Startup.CreateAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateAccountFragment extends Fragment {

    // get reference to main activity so that we can set  Haeder In bit
    private NavigationHeaderListener NavBarHeadListener; // get reference to main activity so that we can set  Haeder In bit
    private UserListListener userListListener;

    private EditText usernameField, emailField, phoneField;

    // get uniqie device id of user
    private String deivce_id;

    View view;

    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";

    private String savedUsername;
    private String savedEmail;
    private String savedPhone;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateAccountFragment () {
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
    public static CreateAccountFragment newInstance(String param1, String param2) {
        CreateAccountFragment fragment = new  CreateAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UserListListener) {
            userListListener = (UserListListener) context;
            NavBarHeadListener = (NavigationHeaderListener) context;
        } else {
            throw new RuntimeException(context + " must implement UserListListener");
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_USERNAME, savedUsername);
        outState.putString(KEY_EMAIL, savedEmail);
        outState.putString(KEY_PHONE, savedPhone);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_create_account, container, false);
        // Inflate the layout for this fragment




        Button createAccountButton = view.findViewById(R.id.create_account_button);
        usernameField = view.findViewById(R.id.username_field);
        emailField = view.findViewById(R.id.Email_field);
        phoneField = view.findViewById(R.id.phonenumber_field);

        if (savedInstanceState != null) {
            savedUsername = savedInstanceState.getString(KEY_USERNAME);
            savedEmail = savedInstanceState.getString(KEY_EMAIL);
            savedPhone = savedInstanceState.getString(KEY_PHONE);
        }

        if (savedUsername != null) {
            usernameField.setText(savedUsername);
        }
        if (savedEmail != null) {
            emailField.setText(savedEmail);
        }
        if (savedPhone != null) {
            phoneField.setText(savedPhone);
        }



        createAccountButton.setOnClickListener(v -> {
            String username = usernameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String phone = phoneField.getText().toString().trim();

            // save the input field data
            savedUsername = username;

            savedEmail = email;
            savedPhone = phone;

            if (username.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (userListListener.getAllUsers().isUsernameTaken(username)) {
                Toast.makeText(getActivity(), "Username already taken", Toast.LENGTH_SHORT).show();
            } else {
                deivce_id = Secure.getString(getContext().getContentResolver(), Secure.ANDROID_ID);

                // use Interface get access all user to add user, then set active user to that user
                userListListener.getAllUsers().addUser(username, email, phone,"", deivce_id);
                User newUser = userListListener.getAllUsers().getUserByUsername(username);
                userListListener.getAllUsers().setActiveUser(newUser);

                Toast.makeText(getActivity(), "Account created successfully", Toast.LENGTH_SHORT).show();

                NavBarHeadListener.visiblityNavigation(true,"My Profile");
                Fragment fragment = new ProfileFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                getActivity().getSupportFragmentManager().popBackStackImmediate();
                fragmentTransaction.commit();

            }
        });


        return view;
    }
}