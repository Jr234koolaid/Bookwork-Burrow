package edu.utsa.cs3773.bookworkburrow.controller;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import edu.utsa.cs3773.bookworkburrow.FirebaseUtil;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.view.ForgotPasswordActivity;
import edu.utsa.cs3773.bookworkburrow.view.LoginActivity;
import edu.utsa.cs3773.bookworkburrow.view.NavigationalActivity;
import edu.utsa.cs3773.bookworkburrow.view.SignupActivity;

public class LoginController implements View.OnClickListener {

    private final LoginActivity mContext;

    public LoginController(LoginActivity _context) {
        mContext = _context;
    }

    @Override
    public void onClick(View _view) {

        int viewID = _view.getId();
        if (viewID == R.id.login_button_login) {

            EditText emailEditText = mContext.findViewById(R.id.login_edit_email);
            EditText passwordEditText = mContext.findViewById(R.id.login_edit_password);

            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            FirebaseUtil.loginWithUsernamePassword(email, password, mContext).thenAccept(account -> {

                mContext.startActivity(new Intent(mContext, NavigationalActivity.class));
                mContext.finish();
            });

        } else if (viewID == R.id.login_button_forgot_password) {
            mContext.startActivity(new Intent(mContext, ForgotPasswordActivity.class));

        } else if (viewID == R.id.login_button_signup) {
            mContext.startActivity(new Intent(mContext, SignupActivity.class));
        }
    }

} // class LoginController
