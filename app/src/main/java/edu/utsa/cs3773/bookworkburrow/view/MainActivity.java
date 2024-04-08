package edu.utsa.cs3773.bookworkburrow.view;

import static android.content.ContentValues.TAG;
import static edu.utsa.cs3773.bookworkburrow.FirebaseUtil.createUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Account;
import edu.utsa.cs3773.bookworkburrow.controller.RouteController;

public class MainActivity extends AppCompatActivity
{
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FirebaseAuth auth = FirebaseAuth.getInstance();
        //sign in
        FirebaseUser user = auth.getCurrentUser();

        //handle getting input for email and password from view
        String email = "user5@example.com";
        String password = "password123";
        createUser(email, password, this)
                .thenAccept(account -> {
                    // Handle success
                    this.account = account;
                    Log.d(TAG, "Account created with UID: " + this.account.getUID());


                    //put all other activity code in this
                })
                .exceptionally(throwable -> {
                    // Handle failure
                    Log.e(TAG, "Failed to create account", throwable);
                    //ui stuff to let the user know create user failed
                    return null;
                });

        RouteController rc = new RouteController();
        if(rc.isLoggedIn()){
            Toast.makeText(this, "Already Logged in!", Toast.LENGTH_SHORT).show();
        }else{
            Intent nextActivityIntent = new Intent(this, LoginActivity.class);
            startActivity(nextActivityIntent);
            finish();
        }
    }



}

 // class MainActivity
