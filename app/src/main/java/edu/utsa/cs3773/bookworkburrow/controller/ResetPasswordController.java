package edu.utsa.cs3773.bookworkburrow.controller;

import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.ErrorDialog;
import edu.utsa.cs3773.bookworkburrow.view.ResetPasswordActivity;

public class ResetPasswordController implements View.OnClickListener {

    private final ResetPasswordActivity mContext;

    public ResetPasswordController(ResetPasswordActivity _context) {
        mContext = _context;
    }

    @Override
    public void onClick(View _view) {

        int viewID = _view.getId();
        if (viewID == R.id.reset_password_button_reset) {

            EditText newPasswordEditText = mContext.findViewById(R.id.reset_password_edit_new_password);
            EditText confirmPasswordEditText = mContext.findViewById(R.id.reset_password_edit_confirm_password);

            String newPassword = newPasswordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (!newPassword.equals(confirmPassword)) {

                ErrorDialog errorDialog = ErrorDialog.getInstance();
                errorDialog.setContext(mContext);
                errorDialog.display("Passwords do not match");

                return;
            }

            // TODO: Check firebase stuff here
            // TODO: Put into firebase utils
            FirebaseAuth auth = FirebaseAuth.getInstance();

        }
    }

} // class ResetPasswordController

//try {
//
//        String newPassword = Input.checkPassword(newPasswordEditText);
//        String confirmPassword = Input.checkPassword(confirmPasswordEditText);
//
//        if (!newPassword.equals(confirmPassword)) throw new IOException("Passwords do not match");
//
//            Intent intent = m_activity.getIntent();
//
//            String email = intent.getStringExtra(ResetPasswordActivity.INTENT_EMAIL);
//            if (email == null) throw new IOException();
//
//            AccountDatabase accountDatabase = AccountDatabase.getInstance();
//            accountDatabase.setContext(m_activity);
//            accountDatabase.updateAccount(email, newPassword);
//
//            Toast.makeText(m_activity, "Password has been successfully reset", Toast.LENGTH_LONG).show();
//
//            m_activity.finish();
//
//} catch (IOException e) {
//
//        ErrorDialog errorDialog = ErrorDialog.getInstance();
//        errorDialog.setContext(m_activity);
//        errorDialog.display(e.getMessage());
//}