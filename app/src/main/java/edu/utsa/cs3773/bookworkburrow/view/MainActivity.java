package edu.utsa.cs3773.bookworkburrow.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
        RouteController rc = new RouteController();
        if(rc.isLoggedIn()){
            Toast.makeText(this, "Already Logged in!", Toast.LENGTH_SHORT).show();
        }else{
            Intent nextActivityIntent = new Intent(this, LoginActivity.class);
            startActivity(nextActivityIntent);
            finish();
        }
    }
} // class MainActivity
