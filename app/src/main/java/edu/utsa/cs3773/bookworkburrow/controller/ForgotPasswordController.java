package edu.utsa.cs3773.bookworkburrow.controller;

import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.ErrorDialog;
import edu.utsa.cs3773.bookworkburrow.view.ForgotPasswordActivity;

public class ForgotPasswordController implements View.OnClickListener {

    private final ForgotPasswordActivity mContext;

    public ForgotPasswordController(ForgotPasswordActivity _context) {
        mContext = _context;
    }

    @Override
    public void onClick(View _view) {

        int viewID = _view.getId();
        if (viewID == R.id.forgot_password_button_confirm) {

            EditText emailEditText = mContext.findViewById(R.id.forgot_password_edit_email);

            String email = emailEditText.getText().toString();

            // TODO: Check firebase stuff here
            // TODO: Put into firebase utils
            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(mContext, task -> {

                if (task.isSuccessful()) {

                    // TODO: Change?
                    new AlertDialog.Builder(mContext)
                            .setPositiveButton("OK", (dialog, i) -> {
                                mContext.finish();
                            })
                            .setTitle("Success!")
                            .setMessage("A link to reset your password has been set")
                            .show();

                } else {

                    ErrorDialog errorDialog = ErrorDialog.getInstance();
                    errorDialog.setContext(mContext);
                    errorDialog.display("Could not find email");
                }
            });
        }
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