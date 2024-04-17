package edu.utsa.cs3773.bookworkburrow.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import edu.utsa.cs3773.bookworkburrow.R;

public class ReadingActivity extends AppCompatActivity {

    private boolean isDarkTheme = false;
    private ScrollView scrollView;
    private TextView textView;
    private ImageButton toggleThemeButton;
    private Button backToBookshelfButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        scrollView = findViewById(R.id.scrollView);
        textView = findViewById(R.id.textView);
        toggleThemeButton = findViewById(R.id.toggleThemeButton);
        backToBookshelfButton = findViewById(R.id.backToBookshelfButton);

        backToBookshelfButton.setPaintFlags(backToBookshelfButton.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        backToBookshelfButton.setOnClickListener(v -> {
            Intent intent = new Intent(ReadingActivity.this, BookshelfActivity.class);
            startActivity(intent);
            finish();
        });

        toggleThemeButton.setOnClickListener(v -> {
            if (isDarkTheme) {
                scrollView.setBackgroundColor(Color.WHITE);
                textView.setTextColor(Color.BLACK);
                toggleThemeButton.setImageResource(R.drawable.ic_sun);
            } else {
                scrollView.setBackgroundColor(Color.BLACK);
                textView.setTextColor(Color.WHITE);
                toggleThemeButton.setImageResource(R.drawable.ic_moon);
            }
            isDarkTheme = !isDarkTheme;
        });

        String fileRefPath = getIntent().getStringExtra("selected_text_url");
        loadBookContent(fileRefPath);
    }

    private void loadBookContent(String fileRefPath) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference fileRef = storage.getReferenceFromUrl(fileRefPath);

        fileRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
            String text = new String(bytes);
            textView.setText(text);
        }).addOnFailureListener(exception -> {
            textView.setText(getString(R.string.error_loading_book));
        });
    }

}
