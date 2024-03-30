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
import edu.utsa.cs3773.bookworkburrow.model.InputChecker;
import edu.utsa.cs3773.bookworkburrow.view.ForgotPasswordActivity;
import edu.utsa.cs3773.bookworkburrow.view.MainActivity;
import edu.utsa.cs3773.bookworkburrow.view.SignupActivity;

public class LoginController implements View.OnClickListener {

    private final AppCompatActivity                 m_activity;
    private final ActivityResultLauncher<Intent>    m_homeLauncher;
    private final ActivityResultLauncher<Intent>    m_forgotPasswordLauncher;
    private final ActivityResultLauncher<Intent>    m_signupLauncher;

    public LoginController(AppCompatActivity _activity) {

        m_activity = _activity;
        m_homeLauncher = m_activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {}
        );
        m_forgotPasswordLauncher = m_activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {}
        );
        m_signupLauncher = m_activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {}
        );
    }

    @Override
    public void onClick(View _view) {

        int viewID = _view.getId();
        if (viewID == R.id.login_button_login) {

            EditText emailEditText = m_activity.findViewById(R.id.login_edit_email);
            EditText passwordEditText = m_activity.findViewById(R.id.login_edit_password);

            try {

                String email = InputChecker.checkEmail(emailEditText);
                String password = InputChecker.checkPassword(passwordEditText);

                AccountDatabase accountDatabase = AccountDatabase.getInstance();
                accountDatabase.setContext(m_activity);

                String UID = accountDatabase.authenticate(email, password);

                Intent intent = new Intent(m_activity, MainActivity.class);
                intent.putExtra(MainActivity.INTENT_ACCOUNT_UID, UID);

                m_homeLauncher.launch(intent);

            } catch (IOException e) {

                ErrorDialog errorDialog = ErrorDialog.getInstance();
                errorDialog.setContext(m_activity);
                errorDialog.display(e.getMessage());
            }

        } else if (viewID == R.id.login_button_forgot_password) {
            m_forgotPasswordLauncher.launch(new Intent(m_activity, ForgotPasswordActivity.class));

        } else if (viewID == R.id.login_button_signup) {
            m_signupLauncher.launch(new Intent(m_activity, SignupActivity.class));
        }
    }

} // class LoginController
