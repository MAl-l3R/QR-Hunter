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

public class StartUpActivity extends AppCompatActivity {
    Button createAccountButton;
    Button loginButton;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        AllUsers allUsers = (AllUsers) getApplicationContext();
        allUsers.init();

        sharedPreferences = this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("isLoggedIn", "false").equals("true")) {
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