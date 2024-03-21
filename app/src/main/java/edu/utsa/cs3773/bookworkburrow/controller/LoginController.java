package edu.utsa.cs3773.bookworkburrow.controller;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.AccountDatabase;
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

            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {

                Toast.makeText(m_activity, "One or more fields are empty", Toast.LENGTH_LONG).show();
                return;
            }

            try {

                String UID = AccountDatabase.getInstance().authenticate(email, password);
                if (UID != null) {

                    Intent intent = new Intent(m_activity, MainActivity.class);
                    intent.putExtra(MainActivity.INTENT_ACCOUNT_UID, UID);

                    m_homeLauncher.launch(intent);

                } else {
                    Toast.makeText(m_activity, "Account information is invalid", Toast.LENGTH_LONG).show();
                }

            } catch (FirebaseAuthInvalidCredentialsException | FirebaseAuthInvalidUserException e) {
                Toast.makeText(m_activity, "Invalid username or password", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Toast.makeText(m_activity, "An unexpected error has occurred", Toast.LENGTH_LONG).show();
            }

        } else if (viewID == R.id.login_button_forgot_password) {
            m_forgotPasswordLauncher.launch(new Intent(m_activity, ForgotPasswordActivity.class));

        } else if (viewID == R.id.login_button_signup) {
            m_signupLauncher.launch(new Intent(m_activity, SignupActivity.class));
        }
    }

} // class LoginController
