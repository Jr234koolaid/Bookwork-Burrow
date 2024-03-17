package edu.utsa.cs3773.bookworkburrow.controller;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Database;

public class SignupController implements View.OnClickListener{

    private final AppCompatActivity m_activity;

    public SignupController(AppCompatActivity _activity) {
        m_activity = _activity;
    }

    @Override
    public void onClick(View _view) {

        int viewID = _view.getId();
        if (viewID == R.id.signup_button_signup) {

            EditText emailEditText = m_activity.findViewById(R.id.signup_edit_email);
            EditText usernameEditText = m_activity.findViewById(R.id.signup_edit_username);
            EditText passwordEditText = m_activity.findViewById(R.id.signup_edit_password);

            String email = emailEditText.getText().toString();
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            // TODO: (Juan): Add account to database
            if (Database.getInstance().add("Nothing", "Nothing")) {
                // TODO (Juan): Intent stuff here
            } else {
                Toast.makeText(m_activity, "Email or username are already in use", Toast.LENGTH_LONG).show();
            }
        }
    }

} // class SignupController
