package edu.utsa.cs3773.bookworkburrow.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.FirebaseUserUtil;
import edu.utsa.cs3773.bookworkburrow.R;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        this.setContentView(R.layout.activity_signup);

        Button signupButton = findViewById(R.id.signup_button_create_account);
        signupButton.setOnClickListener(view -> this.signup());
    }

    private void signup() {

        EditText emailEditText = this.findViewById(R.id.signup_edit_email);
        EditText firstnameEditText = this.findViewById(R.id.signup_edit_firstname);
        EditText lastnameEditText = this.findViewById(R.id.signup_edit_lastname);
        EditText passwordEditText = this.findViewById(R.id.signup_edit_password);

        String email = emailEditText.getText().toString();
        String firstName = firstnameEditText.getText().toString();
        String lastName = lastnameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        FirebaseUserUtil.createUser(firstName, lastName, email, password, this).thenAccept(account -> {

            this.startActivity(new Intent(this, NavigationalActivity.class));
            this.finish();
        });
    }

} // class SignupActivity
