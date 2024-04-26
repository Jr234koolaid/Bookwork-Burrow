package edu.utsa.cs3773.bookworkburrow.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
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
    //Account account;
    ArrayList<String> Orders;
    TextView order1;
    TextView order2;
    TextView order3;

    TextView o1;
    TextView o2;
    TextView o3;
    String text;
    ArrayList<String> list;
    String Filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        order1 = (TextView) findViewById(R.id.firstOrder);
        order2 = (TextView) findViewById(R.id.secondOrder);
        order3 = (TextView) findViewById(R.id.thirdOrder);
        o1 = (TextView) findViewById(R.id.firstText);
        o2 = (TextView) findViewById(R.id.secondText);
        o3 = (TextView) findViewById(R.id.thirdText);
        Filter = getIntent().getStringExtra("Filter");
        setupImageButton(R.id.navigational_button_home);
        setupImageButton(R.id.navigational_button_search);
        setupImageButton(R.id.navigational_button_cart);
        setupButton(R.id.Date);
        setupButton(R.id.Price);
        if (Filter == null) {
            Filter = "";
        }
        if (Filter.equals("Date") || Filter.equals("")) {
            FirebaseUserUtil.getCurrUser().thenAccept(account -> {
                list = account.getOrderHistory();
                for (String id : list) {
                    FirebaseOrderUtil.getOrderByID(id).thenAccept(order -> {
                        text = "Books:\n      ";
                        for (String bookName : order.getBookIDs()) {
                            text += bookName + ", ";
                        }
                        text = text.substring(0, text.length() - 2);
                        text += "\nDate:\n     " + order.getStringDate();
                        text += "\nPrice:\n     " + order.getTotalWithTax();
                        if (list.indexOf(id) == 0) {
                            order1.setText(text);
                            o1.setText("Order 1");
                        }
                        if (list.indexOf(id) == 1) {
                            order2.setText(text);
                            o2.setText("Order 2");
                        }
                        if (list.indexOf(id) == 2) {
                            order3.setText(text);
                            o3.setText("Order 3");
                        }

                    });
                }
            });
        } else if (Filter.equals("Price")) {
            ArrayList<Order> temp = new ArrayList<>();
            FirebaseUserUtil.getCurrUser().thenAccept(account -> {
                List<String> list = account.getOrderHistory();
                List<CompletableFuture<Void>> futures = new ArrayList<>();
                for (String id : list) {
                    CompletableFuture<Void> future = FirebaseOrderUtil.getOrderByID(id).thenAccept(order -> {
                        temp.add(order);
                    });
                    futures.add(future);
                }
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
            });

        }
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
        if(v.getId() == R.id.Price)
        {
            intent = new Intent(this, OrderHistory.class);
            intent.putExtra("Filter","Price");
            startActivity(intent);
        }
        if(v.getId() == R.id.Date)
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