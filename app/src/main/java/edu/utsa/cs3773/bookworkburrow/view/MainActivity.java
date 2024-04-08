package edu.utsa.cs3773.bookworkburrow.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Account;
import edu.utsa.cs3773.bookworkburrow.model.AccountStream;

// TODO (Juan): Should be renamed to HomeActivity
public class MainActivity extends AppCompatActivity {

    public static final String INTENT_ACCOUNT_UID = "INTENT_ACCOUNT_UID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = this.getIntent();

        String UID = intent.getStringExtra(INTENT_ACCOUNT_UID);
        if (UID == null) return;

        Account account = new Account(UID);
        try {

            AccountStream stream = new AccountStream(account, this);
            stream.read();

        } catch (Exception e) {
            Toast.makeText(this, "An unexpected error has occurred", Toast.LENGTH_LONG).show();
        }

//        if(FirebaseUtil.isLoggedIn()){
//            Toast.makeText(this, "Already Logged in!", Toast.LENGTH_SHORT).show();
//        }else{
//            Intent nextActivityIntent = new Intent(this, LoginActivity.class);
//            startActivity(nextActivityIntent);
//            finish();
//        }

//        mAuth = FirebaseAuth.getInstance();

        //String email = "user3@example.com";
        //String password = "password123";
        //Account account = createUser(email,password, this);
        //Log.d("Account UID", account.getUID());
    }

} // class MainActivity
