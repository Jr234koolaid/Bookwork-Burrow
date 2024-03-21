package edu.utsa.cs3773.bookworkburrow.controller;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Account;
import edu.utsa.cs3773.bookworkburrow.model.AccountDatabase;
import edu.utsa.cs3773.bookworkburrow.model.AccountStream;
import edu.utsa.cs3773.bookworkburrow.view.MainActivity;

public class SignupController implements View.OnClickListener {

    private final AppCompatActivity                 m_activity;
    private final ActivityResultLauncher<Intent>    m_homeLauncher;

    public SignupController(AppCompatActivity _activity) {

        m_activity = _activity;
        m_homeLauncher = m_activity.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {}
        );
    }

    @Override
    public void onClick(View _view) {

        int viewID = _view.getId();
        if (viewID == R.id.signup_button_signup) {

            EditText emailEditText = m_activity.findViewById(R.id.signup_edit_email);
            EditText firstnameEditText = m_activity.findViewById(R.id.signup_edit_firstname);
            EditText lastnameEditText = m_activity.findViewById(R.id.signup_edit_lastname);
            EditText passwordEditText = m_activity.findViewById(R.id.signup_edit_password);

            String email = emailEditText.getText().toString();
            String firstName = firstnameEditText.getText().toString();
            String lastName = lastnameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()) {

                Toast.makeText(m_activity, "One or more fields are empty", Toast.LENGTH_LONG).show();
                return;
            }

            try {

                String UID = AccountDatabase.getInstance().add(email, password);
                if (UID != null) {

                    Account account = new Account(UID);
                    account.setEmail(email);
                    account.setFirstName(firstName);
                    account.setLastName(lastName);

                    AccountStream stream = new AccountStream(account, m_activity);
                    stream.write();

                    Intent intent = new Intent(m_activity, MainActivity.class);
                    intent.putExtra(MainActivity.INTENT_ACCOUNT_UID, UID);

                    m_homeLauncher.launch(intent);

                } else {
                    Toast.makeText(m_activity, "Account information is invalid", Toast.LENGTH_LONG).show();
                }

            } catch (FirebaseAuthWeakPasswordException e) {
                Toast.makeText(m_activity, e.getReason(), Toast.LENGTH_LONG).show();

            } catch (FirebaseAuthInvalidCredentialsException e) {
                Toast.makeText(m_activity, "Email is not suitable", Toast.LENGTH_LONG).show();

            } catch (FirebaseAuthUserCollisionException e) {
                Toast.makeText(m_activity, "Email already in use", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Toast.makeText(m_activity, "An unexpected error has occurred", Toast.LENGTH_LONG).show();
            }
        }
    }

} // class SignupController
