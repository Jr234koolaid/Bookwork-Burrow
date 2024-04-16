package edu.utsa.cs3773.bookworkburrow.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.controller.LoginController;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        this.setContentView(R.layout.activity_login);

        LoginController loginController = new LoginController(this);

        AppCompatButton loginButton = this.findViewById(R.id.login_button_login);
        loginButton.setOnClickListener(loginController);

        AppCompatButton forgotPasswordButton = this.findViewById(R.id.login_button_forgot_password);
        forgotPasswordButton.setOnClickListener(loginController);

        AppCompatButton createAccountButton = this.findViewById(R.id.login_button_create_account);
        createAccountButton.setOnClickListener(loginController);
    }

} // class LoginActivity

