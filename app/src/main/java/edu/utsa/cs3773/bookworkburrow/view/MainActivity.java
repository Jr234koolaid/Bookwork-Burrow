package edu.utsa.cs3773.bookworkburrow.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.FirebaseUtil;
import edu.utsa.cs3773.bookworkburrow.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (FirebaseUtil.isLoggedIn()) {

            Intent navigationalIntent = new Intent(this, NavigationalActivity.class);
            this.startActivity(navigationalIntent);

        } else {

            Intent loginIntent = new Intent(this, LoginActivity.class);
            this.startActivity(loginIntent);
        }

        this.finish();
    }

} // class MainActivity
