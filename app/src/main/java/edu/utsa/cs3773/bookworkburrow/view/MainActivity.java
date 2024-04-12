package edu.utsa.cs3773.bookworkburrow.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.FirebaseUtil;
import edu.utsa.cs3773.bookworkburrow.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        this.setContentView(R.layout.activity_main);

        if (FirebaseUtil.isLoggedIn()) {

            this.startActivity(new Intent(this, NavigationalActivity.class));
            this.finish();

        } else {

            this.startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }
    }

} // class MainActivity
