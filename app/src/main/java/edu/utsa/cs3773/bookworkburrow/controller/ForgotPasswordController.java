package edu.utsa.cs3773.bookworkburrow.controller;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.AccountDatabase;
import edu.utsa.cs3773.bookworkburrow.model.ErrorDialog;
import edu.utsa.cs3773.bookworkburrow.model.Input;
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

            try {

                String email = Input.checkEmail(emailEditText);

                AccountDatabase accountDatabase = AccountDatabase.getInstance();
                accountDatabase.setContext(m_activity);

                if (accountDatabase.findAccount(email)) {

                    Intent intent = new Intent(m_activity, ResetPasswordActivity.class);
                    intent.putExtra(ResetPasswordActivity.INTENT_EMAIL, email);

                    m_resetPasswordLauncher.launch(intent);

                } else {
                    throw new IOException("Invalid email");
                }

            } catch (IOException e) {

                ErrorDialog errorDialog = ErrorDialog.getInstance();
                errorDialog.setContext(m_activity);
                errorDialog.display(e.getMessage());
            }
        }
    }

} // class ForgotPasswordController
