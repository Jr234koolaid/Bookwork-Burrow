package edu.utsa.cs3773.bookworkburrow.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import edu.utsa.cs3773.bookworkburrow.FirebaseUserUtil;
import edu.utsa.cs3773.bookworkburrow.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        this.setContentView(R.layout.activity_login);

        AppCompatButton loginButton = this.findViewById(R.id.login_button_login);
        loginButton.setOnClickListener(view -> this.login());

        AppCompatButton forgotPasswordButton = this.findViewById(R.id.login_button_forgot_password);
        forgotPasswordButton.setOnClickListener(view -> this.startActivity(new Intent(this, ForgotPasswordActivity.class)));

        AppCompatButton createAccountButton = this.findViewById(R.id.login_button_create_account);
        createAccountButton.setOnClickListener(view -> this.startActivity(new Intent(this, SignupActivity.class)));
    }

    private void login() {

        EditText emailEditText = this.findViewById(R.id.login_edit_email);
        EditText passwordEditText = this.findViewById(R.id.login_edit_password);

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        FirebaseUserUtil.loginWithUsernamePassword(email, password, this).thenAccept(account -> {

            this.startActivity(new Intent(this, NavigationalActivity.class));
            this.finish();
        });
    }

} // class LoginActivity

