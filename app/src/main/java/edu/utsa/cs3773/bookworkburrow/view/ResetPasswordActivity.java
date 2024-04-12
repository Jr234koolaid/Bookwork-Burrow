package edu.utsa.cs3773.bookworkburrow.view;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.controller.ResetPasswordController;

public class ResetPasswordActivity extends AppCompatActivity {

    public static final String INTENT_EMAIL = "INTENT_EMAIL";

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        this.setContentView(R.layout.activity_reset_password);

        ResetPasswordController resetPasswordController = new ResetPasswordController(this);

        Button resetButton = findViewById(R.id.reset_password_button_reset);
        resetButton.setOnClickListener(resetPasswordController);
    }

} // class ResetPasswordActivity
