package com.example.snailscurlup.ui.startup;

import android.content.Intent;
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
import com.example.snailscurlup.UserListListener;
import com.example.snailscurlup.model.User;


public class CreateAccountActivity extends AppCompatActivity {

    // get reference to main activity so that we can set  Header In bit
    private UserListListener userListListener;

    View view;

    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";

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
                } else if (userListListener.getAllUsers().isUsernameTaken(username)) {
                    // Check if username is taken
                    usernameField.setError("Username already taken");
                } else {
                    device_id = Secure.getString(getContentResolver(), Secure.ANDROID_ID);

                    // use Interface get access all user to add user, then set active user to that user
                    userListListener.getAllUsers().addUser(username, email, phone,"", device_id);
                    User newUser = userListListener.getAllUsers().getUserByUsername(username);
                    userListListener.getAllUsers().setActiveUser(newUser);

                    Toast.makeText(CreateAccountActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();

                    // Go to the main app screen
                    Intent myIntent = new Intent(CreateAccountActivity.this, MainActivity.class);
                    CreateAccountActivity.this.startActivity(myIntent);

                }
            }
        });
    }
}