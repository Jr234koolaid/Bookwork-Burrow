// app\java\view\BookActivity.java

package edu.utsa.cs3773.bookworkburrow.view;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Book;

public class BookActivity extends AppCompatActivity
{
    Book readingBook;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        // expecting book object sent through extras
        // alternatively, could find book object in database through title and author
        Bundle extras = getIntent().getExtras();
        // TODO: readingBook = extras.getParcelable(readingBook,Book);

        textTest();
    }

    public void textTest(){
        readingBook = new Book();
        TextView textContainer = findViewById(R.id.bookTextContainer);
        TextView titleContainer = findViewById(R.id.titleContainer);
        titleContainer.setText(readingBook.getTitle());
        textContainer.setText("This is a test");
    }

} // class BookActivity
