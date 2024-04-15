package edu.utsa.cs3773.bookworkburrow.view;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.controller.LoginController;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView signupButton;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginController loginController = new LoginController(this);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        signupButton = findViewById(R.id.signup_button);
        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            loginController.logIn(email, password).thenAccept(account -> {
                        Toast.makeText(this, "Signed in!", Toast.LENGTH_LONG).show();
                        Intent mainIntent = new Intent(this, MainActivity.class);
                        startActivity(mainIntent);
                        finish();
                    })
                    .exceptionally(throwable -> {
                        Log.e(TAG, "Failed to sign into account", throwable);
                        return null;
                    });;
        });
        signupButton.setOnClickListener(view -> {
            Intent signUpIntent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(signUpIntent);
            finish();
        });
    }

} // class LoginActivity