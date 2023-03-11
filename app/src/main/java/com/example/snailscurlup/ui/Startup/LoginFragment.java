package com.example.snailscurlup.ui.Startup;

import android.content.Context;
import android.os.Bundle;
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
 * Use the {@link com.example.snailscurlup.ui.Startup.StartUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    private UserListListener userListListener;
    private NavigationHeaderListener NavBarHeadListener;

    View view;

    private  Button loginAccountButton;
    private EditText usernameField;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String KEY_USERNAME = "username";

    private String savedUsername;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment () {
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


    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new  LoginFragment();
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
            throw new RuntimeException(context + " must implement UserListListener, or NavBarHeadlIstner");
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_USERNAME, savedUsername);

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
        // get reference to main activity so that we can set  Haeder In bit
        NavigationHeaderListener NavBarHeadListener = (NavigationHeaderListener) getActivity();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);

        loginAccountButton = view.findViewById(R.id.login_account_button);
        usernameField = view.findViewById(R.id.username_field);

        if (savedInstanceState != null) {
            savedUsername = savedInstanceState.getString(KEY_USERNAME);
        }

        if (savedUsername != null) {
            usernameField.setText(savedUsername);
        }


        loginAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString();

                // save the input field data
                savedUsername = username;

                if (username.isEmpty()) {
                    usernameField.setError("Please enter a username");
                }
                if (userListListener.getAllUsers().isUserValid(username)) {

                    User loginuser = userListListener.getAllUsers().getUserByUsername(username);
                    userListListener.getAllUsers().setActiveUser(loginuser);

                    // start main activity
                    NavBarHeadListener.visiblityNavigation(true,"My Profile");
                    Fragment fragment = new ProfileFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                    fragmentTransaction.commit();
                } else {
                    Toast.makeText(getActivity(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }
}