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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
//        mAuth = FirebaseAuth.getInstance();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String email = "user3@example.com";
        String password = "password123";
        Account account = createUser(email,password, this);
        Log.d("Account UID", account.getUid());
    }







} // class MainActivity
