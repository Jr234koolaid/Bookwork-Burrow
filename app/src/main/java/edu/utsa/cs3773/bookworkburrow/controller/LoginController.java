package edu.utsa.cs3773.bookworkburrow.controller;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Database;
import edu.utsa.cs3773.bookworkburrow.view.ForgotPasswordActivity;
import edu.utsa.cs3773.bookworkburrow.view.SignupActivity;

public class LoginController implements View.OnClickListener {

    private final AppCompatActivity                 m_activity;
    private final ActivityResultLauncher<Intent>    m_forgotPasswordLauncher;
    private final ActivityResultLauncher<Intent>    m_signupLauncher;

    public LoginController(AppCompatActivity _activity) {

        m_activity = _activity;
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

            EditText usernameEditText = m_activity.findViewById(R.id.login_edit_username);
            EditText passwordEditText = m_activity.findViewById(R.id.login_edit_password);

            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (Database.getInstance().authenticate(username, password)) {
                // TODO (Juan): Intent stuff here
            } else {
                Toast.makeText(m_activity, "Invalid username or password", Toast.LENGTH_LONG).show();
            }

        } else if (viewID == R.id.login_button_forgot_password) {

            // TODO (Juan): Intent stuff here
            Intent intent = new Intent(m_activity, ForgotPasswordActivity.class);

            m_forgotPasswordLauncher.launch(intent);

        } else if (viewID == R.id.login_button_signup) {

            // TODO (Juan): Intent stuff here
            Intent intent = new Intent(m_activity, SignupActivity.class);

            m_signupLauncher.launch(intent);
        }
    }

} // class LoginController
