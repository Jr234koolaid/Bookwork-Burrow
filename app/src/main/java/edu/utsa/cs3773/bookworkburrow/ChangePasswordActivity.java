package edu.utsa.cs3773.bookworkburrow;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText                mPasswordEditText;
    CheckBox                mPasswordCheckBox;

    TransformationMethod    mShowPasswordMethod;
    TransformationMethod    mHidePasswordMethod;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        this.setContentView(R.layout.activity_change_password);

        mPasswordEditText = this.findViewById(R.id.change_password_edit_password);

        mPasswordCheckBox = this.findViewById(R.id.change_password_check_password);
        mPasswordCheckBox.setOnClickListener(view -> this.toggleVisibility());

        mShowPasswordMethod = PasswordTransformationMethod.getInstance();
        mHidePasswordMethod = HideReturnsTransformationMethod.getInstance();

        AppCompatButton changeButton = this.findViewById(R.id.change_password_button_change);
        changeButton.setOnClickListener(view -> this.changePassword());
    }

    private void toggleVisibility() {
        mPasswordCheckBox.setTransformationMethod(mPasswordCheckBox.isChecked() ? mShowPasswordMethod : mHidePasswordMethod);
    }

    private void changePassword() {

        String password = mPasswordEditText.getText().toString();

        FirebaseUserUtil.resetPassword(password).thenAccept(flag -> {

            Toast.makeText(this, "Your password has been reset", Toast.LENGTH_LONG).show();
            this.finish();
        });
    }

} // class ChangePasswordActivity
