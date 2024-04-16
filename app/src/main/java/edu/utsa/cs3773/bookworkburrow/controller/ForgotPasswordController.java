package edu.utsa.cs3773.bookworkburrow.controller;

import android.view.View;

import edu.utsa.cs3773.bookworkburrow.view.ForgotPasswordActivity;

public class ForgotPasswordController implements View.OnClickListener {

    private final ForgotPasswordActivity mContext;

    public ForgotPasswordController(ForgotPasswordActivity _context) {
        mContext = _context;
    }

    @Override
    public void onClick(View _view) {

    }

} // class ForgotPasswordController

//try {
//
//        String email = Input.checkEmail(emailEditText);
//
//        AccountDatabase accountDatabase = AccountDatabase.getInstance();
//        accountDatabase.setContext(m_activity);
//
//        if (accountDatabase.findAccount(email)) {
//
//            Intent intent = new Intent(m_activity, ResetPasswordActivity.class);
//            intent.putExtra(ResetPasswordActivity.INTENT_EMAIL, email);
//
//            m_resetPasswordLauncher.launch(intent);
//
//        } else {
//            throw new IOException("Invalid email");
//        }
//
//} catch (IOException e) {
//
//        ErrorDialog errorDialog = ErrorDialog.getInstance();
//        errorDialog.setContext(m_activity);
//        errorDialog.display(e.getMessage());
//}