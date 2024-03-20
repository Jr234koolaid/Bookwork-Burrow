package edu.utsa.cs3773.bookworkburrow.controller;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.R;

public class ResetPasswordController implements View.OnClickListener {

    private final AppCompatActivity m_activity;

    public ResetPasswordController(AppCompatActivity _activity) {
        m_activity = _activity;
    }

    @Override
    public void onClick(View _view) {

        int viewID = _view.getId();
        if (viewID == R.id.password_reset_button_reset) {

            EditText newPasswordEditText = m_activity.findViewById(R.id.password_reset_edit_new_password);
            EditText confirmPasswordEditText = m_activity.findViewById(R.id.password_reset_edit_confirm_password);

            String newPassword = newPasswordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {

                Toast.makeText(m_activity, "One or more fields are empty", Toast.LENGTH_LONG).show();
                return;
            }

            if (confirmPassword.equals(newPassword)) {

                // TODO (Juan): Have to get information from intent
                // TODO (Juan): Enter new password to database

                Toast.makeText(m_activity, "Password has been successfully reset", Toast.LENGTH_LONG).show();

                m_activity.finish();

            } else {
                Toast.makeText(m_activity, "Passwords do not match", Toast.LENGTH_LONG).show();
            }
        }
    }

} // class ResetPasswordController
