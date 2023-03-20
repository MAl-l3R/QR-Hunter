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
import com.example.snailscurlup.model.AllUsers;

public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    EditText usernameField;

    private static final String KEY_USERNAME = "username";

    private String savedUsername;

    private AllUsers allUsers;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_USERNAME, savedUsername);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        sharedPreferences = this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("isLoggedIn", "false").equals("true")) {
            initializeApp();
        }

        allUsers = (AllUsers) getApplicationContext();

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

                if (isUsernameValid(username)) {

                    // Save login info
                    editor.putString("newAccount", "false");
                    editor.putString("isLoggedIn", "true");
                    editor.putString("oldUsername", username);
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

    // Check if username exists in database
    private boolean isUsernameValid(String username) {
        for (int i = 0; i < allUsers.getAllUsers().size(); i++) {
            if (allUsers.getAllUsers().get(i).getUsername() != null && allUsers.getAllUsers().get(i).getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    // Go to the main app screen
    private void initializeApp() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}