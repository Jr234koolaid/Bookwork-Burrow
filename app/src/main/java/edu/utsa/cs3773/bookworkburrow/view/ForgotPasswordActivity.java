package edu.utsa.cs3773.bookworkburrow.view;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.controller.ForgotPasswordController;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        this.setContentView(R.layout.activity_forgot_password);

        ForgotPasswordController forgotPasswordController = new ForgotPasswordController(this);

        Button continueButton = findViewById(R.id.forgot_password_button_continue);
        continueButton.setOnClickListener(forgotPasswordController);
    }

} // class ForgotPasswordActivity
