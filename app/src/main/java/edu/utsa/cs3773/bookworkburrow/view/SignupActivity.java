package edu.utsa.cs3773.bookworkburrow.view;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.controller.SignupController;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        this.setContentView(R.layout.activity_signup);

        SignupController signupController = new SignupController(this);

        Button signupButton = findViewById(R.id.signup_button_create_account);
        signupButton.setOnClickListener(signupController);
    }

} // class SignupActivity
