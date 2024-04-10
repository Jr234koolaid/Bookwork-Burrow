package edu.utsa.cs3773.bookworkburrow.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.FirebaseUtil;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Account;
import edu.utsa.cs3773.bookworkburrow.model.Book;

public class CartActivity extends AppCompatActivity
{

    Account account;
    LinearLayout bookContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        account = FirebaseUtil.getCurrUser();
        bookContainer = findViewById(R.id.booksAddedContainer);

        //dummy data for account
        account.getCart().addBook(new Book());


    }

    //TODO: dynamically load in books in cart
    public void loadBookViews(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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
                intent = new Intent(this, MainActivity.class);
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
} // class CartActivity
