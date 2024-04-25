package edu.utsa.cs3773.bookworkburrow.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import edu.utsa.cs3773.bookworkburrow.FirebaseBookUtils;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Book;

public class OwnedBookActivity extends AppCompatActivity {

    private TextView bookTitle;
    private TextView backButton;
    private TextView author;
    private ScrollView descriptionContainer;
    private ImageView bookCover;
    private TextView readButton;

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

        String bookID = getIntent().getStringExtra("bookid");
        FirebaseBookUtils.getBookByID(bookID).thenAccept(Book ->{
            book = Book;
            setBookTitle(Book.getTitle());
            setAuthor(Book.getAuthor());
            setDescriptionContainer(Book.getDescription());
            setBookCover(book.getCoverURL().toString());
            backButton.setOnClickListener(view -> finish());
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