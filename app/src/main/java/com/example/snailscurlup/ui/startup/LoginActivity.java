//package com.example.snailscurlup.ui.startup;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.example.snailscurlup.R;
//import com.example.snailscurlup.UserListListener;
//import com.example.snailscurlup.model.User;
//import com.example.snailscurlup.ui.profile.ProfileFragment;
//
//public class LoginActivity extends AppCompatActivity {
//    private UserListListener userListListener;
//    View view;
//
//    Button loginButton;
//    EditText usernameField;
//
//    private static final String KEY_USERNAME = "username";
//
//    private String savedUsername;
//
//
//
//    public LoginActivity() {
//        // Required empty public constructor
//    }
//
////    @Override
////    public void onAttach(@NonNull Context context) {
////        super.onAttach(context);
////        if (context instanceof UserListListener) {
////            userListListener = (UserListListener) context;
////            NavBarHeadListener = (NavigationHeaderListener) context;
////        } else {
////            throw new RuntimeException(context + " must implement UserListListener, or NavBarHeadlIstner");
////        }
////    }
//
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString(KEY_USERNAME, savedUsername);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // get reference to main activity so that we can set  Haeder In bit
//        NavigationHeaderListener NavBarHeadListener = (NavigationHeaderListener) getActivity();
//
//        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.fragment_login, container, false);
//
//        loginButton = view.findViewById(R.id.login_account_button);
//        usernameField = view.findViewById(R.id.username_field);
//
//        if (savedInstanceState != null) {
//            savedUsername = savedInstanceState.getString(KEY_USERNAME);
//        }
//
//        if (savedUsername != null) {
//            usernameField.setText(savedUsername);
//        }
//
//
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String username = usernameField.getText().toString();
//
//                // save the input field data
//                savedUsername = username;
//
//                if (username.isEmpty()) {
//                    usernameField.setError("Please enter a username");
//                }
//                if (userListListener.getAllUsers().isUserValid(username)) {
//
//                    User loginuser = userListListener.getAllUsers().getUserByUsername(username);
//                    userListListener.getAllUsers().setActiveUser(loginuser);
//
//                    // start main activity
//                    NavBarHeadListener.visiblityNavigation(true,"My Profile");
//                    Fragment fragment = new ProfileFragment();
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.frameLayout, fragment);
//                    getActivity().getSupportFragmentManager().popBackStackImmediate();
//                    fragmentTransaction.commit();
//                } else {
//                    Toast.makeText(getActivity(), "Invalid username or password", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        return view;
//
//    }
//    // checked shared pref if device is already been signed into
//    private boolean hasDeviceID() {
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//        String deviceID = sharedPreferences.getString("deviceID", null);
//        return deviceID != null;
//    }
//}