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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button loginButton;
    EditText usernameField;

    private static final String KEY_USERNAME = "username";

    private String savedUsername;

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

        AllUsers allUsers = (AllUsers) getApplicationContext();
        allUsers.init();

        sharedPreferences = this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

//        if (sharedPreferences.getString("isLoggedIn", "false").equals("true")) {
//            initializeApp();
//        }

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

                if (allUsers.getUsernamesList().contains(username)) {

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

    // Go to the main app screen
    private void initializeApp() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}