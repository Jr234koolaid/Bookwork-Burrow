package edu.utsa.cs3773.bookworkburrow.controller;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import edu.utsa.cs3773.bookworkburrow.FirebaseUtil;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.view.NavigationalActivity;
import edu.utsa.cs3773.bookworkburrow.view.SignupActivity;

public class SignupController implements View.OnClickListener {

    private final SignupActivity mContext;

    public SignupController(SignupActivity _context) {
        mContext = _context;
    }

    @Override
    public void onClick(View _view) {

        int viewID = _view.getId();
        if (viewID == R.id.signup_button_create_account) {

            EditText emailEditText = mContext.findViewById(R.id.signup_edit_email);
            EditText firstnameEditText = mContext.findViewById(R.id.signup_edit_firstname);
            EditText lastnameEditText = mContext.findViewById(R.id.signup_edit_lastname);
            EditText passwordEditText = mContext.findViewById(R.id.signup_edit_password);

            String email = emailEditText.getText().toString();
            String firstName = firstnameEditText.getText().toString();
            String lastName = lastnameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            FirebaseUtil.createUser(email, password, mContext).thenAccept(account -> {

                // TODO: Set user info here

                mContext.startActivity(new Intent(mContext, NavigationalActivity.class));
                mContext.finish();
            });
        }
    }

} // class SignupController

//            try {
//
//                String email = Input.checkEmail(emailEditText);
//                String firstName = Input.checkName(firstnameEditText);
//                String lastName = Input.checkName(lastnameEditText);
//                String password = Input.checkPassword(passwordEditText);
//
//                AccountDatabase accountDatabase = AccountDatabase.getInstance();
//                accountDatabase.setContext(m_activity);
//
//                String UID = accountDatabase.addAccount(email, password);
//
//                Account account = new Account(UID);
//                account.setEmail(email);
//                account.setFirstName(firstName);
//                account.setLastName(lastName);
//
//                AccountStream stream = new AccountStream(account, m_activity);
//                stream.write();
//
//                Intent intent = new Intent(m_activity, MainActivity.class);
//                intent.putExtra(MainActivity.INTENT_ACCOUNT_UID, UID);
//
//                m_homeLauncher.launch(intent);
//
//            } catch (IOException e) {
//
//                ErrorDialog errorDialog = ErrorDialog.getInstance();
//                errorDialog.setContext(m_activity);
//                errorDialog.display(e.getMessage());
//            }