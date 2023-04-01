package com.example.snailscurlup.ui.startup;

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
import com.example.snailscurlup.model.AllUsers;

import java.util.concurrent.TimeUnit;


/**
 * LoginActivity
 * <p>
 * This activity allows the user to create a new account.
 * It saves the user's username, email, phone number, and device ID.
 *
 * @author MAl-l3R (orignally was Fragment created by AyanB123)
 */
public class LoginActivity extends AppCompatActivity {
    // Keys for saving the input field data
    private static final String KEY_USERNAME = "username";
    Button loginButton;
    EditText usernameField;
    private String savedUsername;


    // Shared Preferences for saving user data -> username
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // User data fields saved with Shared Preferences
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_USERNAME, savedUsername);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // Initialize allUsers list so that it can be used as it is global access to all users
        AllUsers allUsers = (AllUsers) getApplicationContext();
        allUsers.init();
        // Wait for thread to finish
        // allUsers list is initializing...
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        // Shared Preferences for saving user data
        sharedPreferences = this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        loginButton = findViewById(R.id.login_button);
        usernameField = findViewById(R.id.username_field);

        // get saved data from Shared Preferences if it exists
        if (savedInstanceState != null) {
            savedUsername = savedInstanceState.getString(KEY_USERNAME);
        }

        // set the input field data if it exists
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

                if (allUsers.getUsernamesList().contains(username)) {

                    // Save login info
                    editor.putString("newAccount", "false");
                    editor.putString("isLoggedIn", "true");
                    editor.putString("currentUsername", username);
                    editor.commit();

                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    // Go to the main app screen
                    initializeApp();

                } else {
                    usernameField.setError("Invalid username");
                }
            }
        });

    }

    // Go to the main app screen
    private void initializeApp() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}