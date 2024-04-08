// app\java\view\BookActivity.java

package edu.utsa.cs3773.bookworkburrow.view;

import android.os.Bundle;
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
        Bundle extras = getIntent().getExtras();
        // TODO: readingBook = extras.getParcelable(readingBook,Book);
    }

} // class BookActivity
