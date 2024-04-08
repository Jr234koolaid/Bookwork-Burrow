package edu.utsa.cs3773.bookworkburrow.view;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.controller.LoginController;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginController loginController = new LoginController(this);

        Button loginButton = findViewById(R.id.login_button_login);
        loginButton.setOnClickListener(loginController);

        Button forgotPasswordButton = findViewById(R.id.login_button_forgot_password);
        forgotPasswordButton.setOnClickListener(loginController);

        Button signupButton = findViewById(R.id.login_button_signup);
        signupButton.setOnClickListener(loginController);
    }

} // class LoginActivity
