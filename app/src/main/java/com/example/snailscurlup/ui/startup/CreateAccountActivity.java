package com.example.snailscurlup.ui.startup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.snailscurlup.MainActivity;
import com.example.snailscurlup.R;


public class CreateAccountActivity extends AppCompatActivity {
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String device_id, savedUsername, savedEmail, savedPhone;
    private EditText usernameField, emailField, phoneField;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_USERNAME, savedUsername);
        outState.putString(KEY_EMAIL, savedEmail);
        outState.putString(KEY_PHONE, savedPhone);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_account);

        sharedPreferences = this.getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("isLoggedIn", "false").equals("true")) {
            initializeApp();
        }

        Button createAccountButton = findViewById(R.id.create_account_button);
        usernameField = findViewById(R.id.username_field);
        emailField = findViewById(R.id.Email_field);
        phoneField = findViewById(R.id.phone_number_field);

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

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameField.getText().toString().trim();
                String email = emailField.getText().toString().trim();
                String phone = phoneField.getText().toString().trim();

                // save the input field data
                savedUsername = username;
                savedEmail = email;
                savedPhone = phone;

                if (username.isEmpty()) {
                    // Check if username has been entered
                    usernameField.setError("Username is invalid");

                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    // Check if email has been entered
                    emailField.setError("Email is invalid");

                } else if (!Patterns.PHONE.matcher(phone).matches()) {
                    // Check if phone number has been entered
                    phoneField.setError("Phone number is invalid");

                } else {
                    device_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);

                    // Save new user info
                    editor.putString("newAccount", "true");
                    editor.putString("isLoggedIn", "true");
                    editor.putString("newUsername", username);
                    editor.putString("newEmail", email);
                    editor.putString("newPhone", phone);
                    editor.putString("newDeviceID", device_id);
                    editor.commit();

                    Toast.makeText(CreateAccountActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();

                    // Go to the main app screen
                    initializeApp();

                }
            }
        });
    }

    // Go to the main app screen
    private void initializeApp() {
        startActivity(new Intent(CreateAccountActivity.this, MainActivity.class));
        finish();
    }
}