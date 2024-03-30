package edu.utsa.cs3773.bookworkburrow.controller;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Account;
import edu.utsa.cs3773.bookworkburrow.model.AccountDatabase;
import edu.utsa.cs3773.bookworkburrow.model.AccountStream;
import edu.utsa.cs3773.bookworkburrow.model.ErrorDialog;
import edu.utsa.cs3773.bookworkburrow.model.InputChecker;
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
        if (viewID == R.id.signup_button_create_account) {

            EditText emailEditText = m_activity.findViewById(R.id.signup_edit_email);
            EditText firstnameEditText = m_activity.findViewById(R.id.signup_edit_firstname);
            EditText lastnameEditText = m_activity.findViewById(R.id.signup_edit_lastname);
            EditText passwordEditText = m_activity.findViewById(R.id.signup_edit_password);

            try {

                String email = InputChecker.checkEmail(emailEditText);
                String firstName = InputChecker.checkName(firstnameEditText);
                String lastName = InputChecker.checkName(lastnameEditText);
                String password = InputChecker.checkPassword(passwordEditText);

                AccountDatabase accountDatabase = AccountDatabase.getInstance();
                accountDatabase.setContext(m_activity);

                String UID = accountDatabase.addAccount(email, password);

                Account account = new Account(UID);
                account.setEmail(email);
                account.setFirstName(firstName);
                account.setLastName(lastName);

                AccountStream stream = new AccountStream(account, m_activity);
                stream.write();

                Intent intent = new Intent(m_activity, MainActivity.class);
                intent.putExtra(MainActivity.INTENT_ACCOUNT_UID, UID);

                m_homeLauncher.launch(intent);

            } catch (IOException e) {

                ErrorDialog errorDialog = ErrorDialog.getInstance();
                errorDialog.setContext(m_activity);
                errorDialog.display(e.getMessage());
            }
        }
    }

} // class SignupController
