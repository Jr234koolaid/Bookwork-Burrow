package edu.utsa.cs3773.bookworkburrow.view;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;

import edu.utsa.cs3773.bookworkburrow.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        this.setContentView(R.layout.activity_forgot_password);

        AppCompatButton confirmButton = findViewById(R.id.forgot_password_button_confirm);
        confirmButton.setOnClickListener(view -> confirm());
    }

    private void confirm() {

        EditText emailEditText = this.findViewById(R.id.forgot_password_edit_email);

        String email = emailEditText.getText().toString();

        // TODO: Put into firebase utils
        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnSuccessListener(this, (task -> this.sentEmail()));
    }

    private void sentEmail() {

        // Display to user
        Toast.makeText(this, "Password Reset Email Sent", Toast.LENGTH_SHORT).show();

        // Exit activity
        this.finish();
    }

} // class ForgotPasswordActivity
