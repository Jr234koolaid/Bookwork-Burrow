package edu.utsa.cs3773.bookworkburrow.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import edu.utsa.cs3773.bookworkburrow.FirebaseBookUtils;
import edu.utsa.cs3773.bookworkburrow.FirebaseUserUtil;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Book;

public class OwnedBookActivity extends AppCompatActivity {

    private TextView bookTitle;
    private TextView backButton;
    private TextView author;
    private ScrollView descriptionContainer;
    private ImageView bookCover;
    private TextView readButton;
    private ImageView favoriteButton;

    private Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owned_book);

        bookTitle = findViewById(R.id.BookTitle);
        backButton = findViewById(R.id.back_button);
        descriptionContainer = findViewById(R.id.descContainer);
        author = findViewById(R.id.authorName);
        bookCover = findViewById(R.id.BookImage);
        readButton = findViewById(R.id.ReadButton);
        favoriteButton = findViewById(R.id.favoriteBook);

        String bookID = getIntent().getStringExtra("bookID");
        FirebaseBookUtils.getBookByID(bookID).thenAccept(Book ->{
            book = Book;
            setBookTitle(Book.getTitle());
            setAuthor(Book.getAuthor());
            setDescriptionContainer(Book.getDescription());
            setBookCover(book.getCoverURL().toString());
            backButton.setOnClickListener(view -> finish());
            setFavoriteButton();
            readButton.setOnClickListener(view ->{
                Intent readIntent = new Intent(this, ReadingActivity.class);
                readIntent.putExtra("bookID", bookID);
                this.startActivity(readIntent);
            });
        });
    }

    private void addToFavs() {
        FirebaseUserUtil.getCurrUser().thenAccept(account -> {
           account.addToFavorites(book.getId()).thenAccept(Boolean ->{
               favoriteButton.setImageResource(R.drawable.heart_filled);
               Toast.makeText(this, "Book Added to Favorites!", Toast.LENGTH_SHORT).show();
               Log.d("Updated Favorites", account.getFavorites().toString());
               favoriteButton.setOnClickListener(view->removeFromFavs());
           });
        });
    }

    private void removeFromFavs() {
        FirebaseUserUtil.getCurrUser().thenAccept(account -> {
            account.removeFromFavorites(book.getId()).thenAccept(Boolean ->{
                favoriteButton.setImageResource(R.drawable.heart);
                Toast.makeText(this, "Book removed from Favorites!", Toast.LENGTH_SHORT).show();
                Log.d("Updated Favorites", account.getFavorites().toString());
                favoriteButton.setOnClickListener(view->addToFavs());
            });

        });
    }

    private void setFavoriteButton(){
        FirebaseUserUtil.getCurrUser().thenAccept(account -> {
            if(account.getFavorites().contains(book.getId())){
                favoriteButton.setImageResource(R.drawable.heart_filled);
                favoriteButton.setOnClickListener(view->removeFromFavs());
            }
            else favoriteButton.setOnClickListener(view ->addToFavs());
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

    private void setBookCover(String coverUrl){
        Glide.with(this)
                .load(coverUrl)
                .into(bookCover);
    }

}