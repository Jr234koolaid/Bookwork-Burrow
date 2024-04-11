package edu.utsa.cs3773.bookworkburrow.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import edu.utsa.cs3773.bookworkburrow.FirebaseUtil;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Account;
import edu.utsa.cs3773.bookworkburrow.model.Book;

public class CartActivity extends AppCompatActivity
{

    Account account;
    LinearLayout bookContainer;
    Book[] books;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        account = FirebaseUtil.getCurrUser();
        bookContainer = findViewById(R.id.booksAddedContainer);

        //dummy data for account
        Book book0 = new Book();
        book0.setTitle("Percy Jackson and the Lightning Thief");
        book0.setAuthor("Rick Riordan");
        book0.setPrice(15.99);

        Book book1 = new Book();
        book1.setTitle("Percy Jackson and the Titan's Curse");
        book1.setAuthor("Rick Riordan");
        book1.setPrice(15.99);

        Book book2 = new Book();
        book2.setTitle("Percy Jackson and the Sea of Monsters");
        book2.setAuthor("Rick Riordan");
        book2.setPrice(15.99);

        books = new Book[]{book0, book1, book2};

        account.getCart().addBook(book0);
        account.getCart().addBook(book1);
        account.getCart().addBook(book2);
        Log.d("Account info", account.toString());
        loadBookViews();


    }

    public void loadBookViews(){
        for(Book book : account.getCart().getCartList()){
            LayoutInflater inflater = LayoutInflater.from(this);
            // Inflate the individual book layout
            LinearLayout bookCartLayout = (LinearLayout) inflater.inflate(R.layout.book_cart_layout, null, false);

            TextView bookTitle = bookCartLayout.findViewById(R.id.cart_book_title);
            TextView bookAuthor = bookCartLayout.findViewById(R.id.cart_book_author);
            TextView bookPrice = bookCartLayout.findViewById(R.id.cart_book_price);
            bookTitle.setText(book.getTitle());
            bookAuthor.setText(book.getAuthor());
            bookPrice.setText(""+book.getPrice());
            bookContainer.addView(bookCartLayout);
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
}
