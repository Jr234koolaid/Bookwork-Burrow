package edu.utsa.cs3773.bookworkburrow.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import edu.utsa.cs3773.bookworkburrow.FirebaseBookUtils;
import edu.utsa.cs3773.bookworkburrow.FirebaseUserUtil;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Account;
import edu.utsa.cs3773.bookworkburrow.model.Book;

public class BookSummaryActivity extends AppCompatActivity {

    private TextView bookTitle;
    private TextView addToCart;
    private TextView backToSearch;
    private TextView author;
    private ScrollView descriptionContainer;


    private Account account;
    private Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_summary);

        bookTitle = findViewById(R.id.BookTitle);
        addToCart = findViewById(R.id.addToCartButton);
        backToSearch = findViewById(R.id.back_button);
        descriptionContainer = findViewById(R.id.desc_container);
        author = findViewById(R.id.authorName);

        String bookID = getIntent().getStringExtra("bookid");
        Log.d("BookID", bookID);

        FirebaseUserUtil.getCurrUser().thenAccept(Account->{
            account = Account;
            FirebaseBookUtils.getBookByID(bookID).thenAccept(Book ->{
                book = Book;
                setBookTitle(Book.getTitle());
                setAuthor(Book.getAuthor());
                setAddToCartView(false);
                setDescriptionContainer(Book.getDescription());
                addToCart.setOnClickListener(view -> addToCart(book));
                backToSearch.setOnClickListener(view -> returnToSearch());
            });
        });


    }

    private void setBookTitle(String title){
        bookTitle.setText(title);
    }

    private void setDescriptionContainer(String description){
        TextView desc = new TextView(this);
        desc.setText(description);
        descriptionContainer.addView(desc);
    }

    private void setAuthor(String authorName){
        author.setText(authorName);
    }

    private void returnToSearch(){
        this.startActivity(new Intent(this, NavigationalActivity.class));
    }

    private void setAddToCartView(Boolean added){
        if(added) addToCart.setText("Added to Cart!");
        else addToCart.setText("Add to cart | " + book.getPrice());
    }

    private void addToCart(Book book){
        account.getCart().addBook(book);
        setAddToCartView(true);

    }
}