package edu.utsa.cs3773.bookworkburrow.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.R;

public class ConfirmPurchaseActivity extends AppCompatActivity {
    private ImageView backButton;
    private LinearLayout booksContainer;
    private Button checkout;
    private Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_purchase);

        backButton = findViewById(R.id.back_arrow);
        booksContainer = findViewById(R.id.booksAddedContainer);
        checkout = findViewById(R.id.checkout_button);
        cancel = findViewById(R.id.cancel_button);

        backButton.setOnClickListener(view ->returnToCart());
        cancel.setOnClickListener(view -> returnToCart());
        checkout.setOnClickListener(view -> displayConfirmation());


    }

    public void returnToCart(){
        Intent cartView = new Intent(this, CartActivity.class);
        startActivity(cartView);

    }

    public void displayConfirmation(){
        TextView message = new TextView(this);
        message.setText("Purchase complete");
        booksContainer.addView(message);
    }


}
