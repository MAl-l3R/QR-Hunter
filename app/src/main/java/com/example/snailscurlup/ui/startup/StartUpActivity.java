package com.example.snailscurlup.ui.startup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.snailscurlup.MainActivity;
import com.example.snailscurlup.R;
import com.example.snailscurlup.model.AllUsers;
import com.example.snailscurlup.model.User;

import java.util.concurrent.TimeUnit;


/**
 * StartUpActivity
 * <p>
 * This activity allows the user to Create account or login to the app.
 *
 * @author MAl-l3R (orignally was Fragment created by AyanB123)
 */
public class StartUpActivity extends AppCompatActivity {
    Button createAccountButton;
    Button loginButton;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

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


        sharedPreferences = this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();


        if (sharedPreferences.getString("currentUsername", null) != null) {
            allUsers.userWanted(sharedPreferences.getString("currentUsername", null));
        }

        // Check if user is logged in if so start the main app screen
        if (sharedPreferences.getString("isLoggedIn", "false").equals("true")) {
            // Set active user
            User activeUser = allUsers.getWantedUser();
            allUsers.setActiveUser(activeUser);
            // Go to the main app screen
            Intent myIntent = new Intent(StartUpActivity.this, MainActivity.class);
            StartUpActivity.this.startActivity(myIntent);
        }

        loginButton = findViewById(R.id.login_button);
        createAccountButton = findViewById(R.id.create_account_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(StartUpActivity.this, LoginActivity.class);
                StartUpActivity.this.startActivity(myIntent);
            }
        });

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(StartUpActivity.this, CreateAccountActivity.class);
                StartUpActivity.this.startActivity(myIntent);
            }
        });

    }
}