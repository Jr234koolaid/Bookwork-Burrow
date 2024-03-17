package edu.utsa.cs3773.bookworkburrow.controller;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.view.ResetPasswordActivity;

public class ForgotPasswordController implements View.OnClickListener {

    private final AppCompatActivity                 m_activity;
    private final ActivityResultLauncher<Intent>    m_resetPasswordLauncher;

    public ForgotPasswordController(AppCompatActivity _activity) {
        m_activity = _activity;
        m_resetPasswordLauncher = m_activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> { m_activity.finish(); }
        );
    }

    @Override
    public void onClick(View _view) {

        int viewID = _view.getId();
        if (viewID == R.id.forgot_password_button_continue) {

            EditText emailEditText = m_activity.findViewById(R.id.forgot_password_edit_email);

            String email = emailEditText.getText().toString();

            // TODO (Juan): Validate
            if (true) {

                // TODO (Juan): Intent stuff here
                Intent intent = new Intent(m_activity, ResetPasswordActivity.class);

                m_resetPasswordLauncher.launch(intent);

            } else  {
                Toast.makeText(m_activity, "Invalid email", Toast.LENGTH_LONG).show();
            }
        }
    }

} // class ForgotPasswordController
