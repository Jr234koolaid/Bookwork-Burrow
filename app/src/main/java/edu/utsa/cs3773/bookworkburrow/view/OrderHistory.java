package edu.utsa.cs3773.bookworkburrow.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import edu.utsa.cs3773.bookworkburrow.FirebaseBookUtils;
import edu.utsa.cs3773.bookworkburrow.FirebaseOrderUtil;
import edu.utsa.cs3773.bookworkburrow.FirebaseUserUtil;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Account;
import edu.utsa.cs3773.bookworkburrow.model.Order;

public class OrderHistory extends AppCompatActivity implements View.OnClickListener {
    String Filter;
    private LinearLayout ordersLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        Filter = getIntent().getStringExtra("Filter");
        ordersLayout = findViewById(R.id.ordersLinearLayout);
        setupButton(R.id.dateFilterButton);
        setupButton(R.id.priceFilterButton);
        FirebaseUserUtil.getCurrUser().thenAccept(account -> {
            FirebaseOrderUtil.getOrdersByUserId(account.getUID(), Filter == null ? "" : Filter).thenAccept(list -> {
                for(Order o : list){
                    addOrderView(o);
                }
            });
        });
    }
    private void addOrderView(Order order) {
        LayoutInflater inflater = this.getLayoutInflater();
        View orderLayout = inflater.inflate(R.layout.order_desc, ordersLayout, false);

        TextView dateTextView = orderLayout.findViewById(R.id.orderDate);
        dateTextView.setText(""+order.getDate().toDate());

        TextView priceTextView = orderLayout.findViewById(R.id.orderPrice);
        priceTextView.setText(String.format(Locale.getDefault(), "Price: $%.2f", order.getSubtotal()));

        TextView booksTextView = orderLayout.findViewById(R.id.orderBookList);
        booksTextView.setText("Books: " + TextUtils.join(", ", order.getBookIDs()));

        ordersLayout.addView(orderLayout);
    }
    public void onClick(View v)
    {
        Intent intent;
        if(v.getId() == R.id.navigational_button_home)
        {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.navigational_button_cart)
        {
            intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.priceFilterButton)
        {
            intent = new Intent(this, OrderHistory.class);
            intent.putExtra("Filter","Price");
            startActivity(intent);
        }
        if(v.getId() == R.id.dateFilterButton)
        {
            intent = new Intent(this, OrderHistory.class);
            intent.putExtra("Filter","Date");
            startActivity(intent);
        }



    }
    private void setupImageButton(int buttonID){
        ImageButton button = findViewById(buttonID);
        button.setOnClickListener(this);
    }
    private void setupButton(int buttonID)
    {
        Button button = findViewById(buttonID);
        button.setOnClickListener(this);
    }
}