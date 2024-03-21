package edu.utsa.cs3773.bookworkburrow.view;

import static edu.utsa.cs3773.bookworkburrow.model.FirebaseUtil.createUser;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Account;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mAuth = FirebaseAuth.getInstance();

        String email = "user3@example.com";
        String password = "password123";
        Account account = createUser(email,password, this);
        Log.d("Account UID", account.getUID());
    }

} // class MainActivity
