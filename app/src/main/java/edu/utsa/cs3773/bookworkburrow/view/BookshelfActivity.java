package edu.utsa.cs3773.bookworkburrow.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.GridLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import edu.utsa.cs3773.bookworkburrow.R;

public class BookshelfActivity extends AppCompatActivity {

    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookshelf);

        gridLayout = findViewById(R.id.gridLayout);

        loadBooksFromFirebase();
    }

    private void loadBooksFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("books").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String imageUrl = document.getString("img-url");
                    String textUrl = document.getString("text-url");
                    createButtonForBook(imageUrl, textUrl);
                }
            } else {
                // Handle failure
            }
        });
    }


    private void createButtonForBook(String imageUrl, String textUrl) {
        // Define the size of the button
        int size = getResources().getDimensionPixelSize(R.dimen.book_image_size);
        Button button = new Button(this);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = size;   // Width of button
        params.height = size;  // Height of button
        button.setLayoutParams(params);

        // Set a placeholder background while loading
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.design_default_color_on_primary));

        // Load the image using Glide
        Glide.with(this)
                .load(imageUrl)
                .override(size, size) // Override the image size
                .centerCrop()        // Center crop to fill the button bounds
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        button.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                        // Set a placeholder or error image if needed
                    }
                });

        button.setOnClickListener(v -> {
            Intent intent = new Intent(BookshelfActivity.this, ReadingActivity.class);
            intent.putExtra("selected_text_url", textUrl);
            startActivity(intent);
        });

        // Add button to the GridLayout
        gridLayout.addView(button);
    }
}
