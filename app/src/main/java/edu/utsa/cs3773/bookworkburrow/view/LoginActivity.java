package edu.utsa.cs3773.bookworkburrow.view;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.controller.LoginController;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton, signupButton;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginController loginController = new LoginController(this);

        // Initialize views
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        signupButton = findViewById(R.id.signup_button);

        // Set up the button click listener
        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            // Use the loginController to perform the login
            loginController.logIn(email, password).thenAccept(account -> {
                        // Handle success
                        Toast.makeText(this, "Signed into account with UID: " + account.getUID(), Toast.LENGTH_LONG).show();
                        //put all other activity code in this
                    })
                    .exceptionally(throwable -> {
                        // Handle failure
                        Log.e(TAG, "Failed to sign into account", throwable);
                        return null;
                    });;
        });
        signupButton.setOnClickListener(view -> {
            // Navigate to the SignUpActivity
            Intent signUpIntent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(signUpIntent);
            finish();
        });
    }

} // class LoginActivity
