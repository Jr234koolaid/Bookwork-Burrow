// app\java\view\MainActivity.java

package edu.utsa.cs3773.bookworkburrow.view;

import static android.content.ContentValues.TAG;

import static edu.utsa.cs3773.bookworkburrow.model.FirebaseUtil.createUser;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.concurrent.Executor;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.view.model.Account;

public class MainActivity extends AppCompatActivity
{
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        //handle getting input for email and password from view
        String email = "user5@example.com";
        String password = "password123";
        createUser(email, password, this)
                .thenAccept(account -> {
                    // Handle success
                    this.account = account;
                    Log.d(TAG, "Account created with UID: " + this.account.getUid());
                    //put all other activity code in this
                })
                .exceptionally(throwable -> {
                    // Handle failure
                    Log.e(TAG, "Failed to create account", throwable);
                    return null;
                });
    }







} // class MainActivity
