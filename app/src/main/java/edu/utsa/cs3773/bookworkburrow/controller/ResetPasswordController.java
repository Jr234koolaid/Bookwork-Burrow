package edu.utsa.cs3773.bookworkburrow.controller;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.AccountDatabase;
import edu.utsa.cs3773.bookworkburrow.model.ErrorDialog;
import edu.utsa.cs3773.bookworkburrow.model.Input;
import edu.utsa.cs3773.bookworkburrow.view.ResetPasswordActivity;

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

            try {

                String newPassword = Input.checkPassword(newPasswordEditText);
                String confirmPassword = Input.checkPassword(confirmPasswordEditText);

                if (!newPassword.equals(confirmPassword)) throw new IOException("Passwords do not match");

                Intent intent = m_activity.getIntent();

                String email = intent.getStringExtra(ResetPasswordActivity.INTENT_EMAIL);
                if (email == null) throw new IOException();

                AccountDatabase accountDatabase = AccountDatabase.getInstance();
                accountDatabase.setContext(m_activity);
                accountDatabase.updateAccount(email, newPassword);

                Toast.makeText(m_activity, "Password has been successfully reset", Toast.LENGTH_LONG).show();

                m_activity.finish();

            } catch (IOException e) {

                ErrorDialog errorDialog = ErrorDialog.getInstance();
                errorDialog.setContext(m_activity);
                errorDialog.display(e.getMessage());
            }
        }
    }

} // class ResetPasswordController
