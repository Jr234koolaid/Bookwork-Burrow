package edu.utsa.cs3773.bookworkburrow.controller;

import android.view.View;

import edu.utsa.cs3773.bookworkburrow.view.SignupActivity;

public class SignupController implements View.OnClickListener {

    private final SignupActivity mContext;

    public SignupController(SignupActivity _context) {
        mContext = _context;
    }

    @Override
    public void onClick(View _view) {

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