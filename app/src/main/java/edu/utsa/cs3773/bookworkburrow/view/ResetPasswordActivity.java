package edu.utsa.cs3773.bookworkburrow.view;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.controller.ResetPasswordController;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        ResetPasswordController resetPasswordController = new ResetPasswordController(this);

        Button resetButton = findViewById(R.id.password_reset_button_reset);
        resetButton.setOnClickListener(resetPasswordController);
    }

} // class ResetPasswordActivity
