// app\java\view\BookActivity.java

package edu.utsa.cs3773.bookworkburrow.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.R;

public class BookActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
    }

} // class BookActivity
