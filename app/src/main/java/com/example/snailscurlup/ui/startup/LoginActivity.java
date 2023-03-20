package com.example.snailscurlup.ui.startup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.snailscurlup.MainActivity;
import com.example.snailscurlup.R;
import com.example.snailscurlup.UserListListener;
import com.example.snailscurlup.model.User;

public class LoginActivity extends AppCompatActivity {
    private UserListListener userListListener;

    Button loginButton;
    EditText usernameField;

    private static final String KEY_USERNAME = "username";

    private String savedUsername;


//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        if (context instanceof UserListListener) {
//            userListListener = (UserListListener) context;
//        } else {
//            throw new RuntimeException(context + " must implement UserListListener, or NavBarHeadlIstner");
//        }
//    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_USERNAME, savedUsername);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_button);
        usernameField = findViewById(R.id.username_field);

        if (savedInstanceState != null) {
            savedUsername = savedInstanceState.getString(KEY_USERNAME);
        }

        if (savedUsername != null) {
            usernameField.setText(savedUsername);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameField.getText().toString();

                // save the input field data
                savedUsername = username;

                if (username.isEmpty()) {
                    // Check if username has been entered
                    usernameField.setError("Please enter a username");
                }

                if (userListListener.getAllUsers().isUserValid(username)) {

                    User loggedInUser = userListListener.getAllUsers().getUserByUsername(username);
                    userListListener.getAllUsers().setActiveUser(loggedInUser);

                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    // Go to the main app screen
                    Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(myIntent);

                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // checked shared pref if device is already been signed into
    private boolean hasDeviceID() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String deviceID = sharedPreferences.getString("deviceID", null);
        return deviceID != null;
    }
}