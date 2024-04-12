package edu.utsa.cs3773.bookworkburrow.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.FirebaseUtil;
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
        if(FirebaseUtil.isLoggedIn()){
            Toast.makeText(this, "Already Logged in!", Toast.LENGTH_SHORT).show();
        }else{
            Intent nextActivityIntent = new Intent(this, LoginActivity.class);
            startActivity(nextActivityIntent);
            finish();
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void onNavItemClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.nav_bookshelf:
                //TODO: set to bookshelf activity
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Log.d("Nav bar clicked", "Account");
                break;
            case R.id.nav_search:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Log.d("Nav bar clicked", "Transactions");
                break;
            case R.id.nav_cart:
                intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                Log.d("Nav bar clicked", "Budget");
                break;
            default:
                Log.d("Nav bar clicked", "Transfer");
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
} // class MainActivity