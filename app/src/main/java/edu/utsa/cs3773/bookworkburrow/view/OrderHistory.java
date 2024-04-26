package edu.utsa.cs3773.bookworkburrow.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import edu.utsa.cs3773.bookworkburrow.FirebaseBookUtils;
import edu.utsa.cs3773.bookworkburrow.FirebaseOrderUtil;
import edu.utsa.cs3773.bookworkburrow.FirebaseUserUtil;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Account;
import edu.utsa.cs3773.bookworkburrow.model.Order;

public class OrderHistory extends AppCompatActivity {
    //Account account;
    ArrayList<String> Orders;
    TextView order1;
    TextView order2;
    TextView order3;

    Order currentOrder;
    String text;
    int num = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        order1 = (TextView)findViewById(R.id.firstOrder);
        order2 = (TextView)findViewById(R.id.secondOrder);
        order3 = (TextView)findViewById(R.id.thirdOrder);
        FirebaseUserUtil.getCurrUser().thenAccept(account->{
            FirebaseOrderUtil.getOrderHistory(account.getUID()).thenAccept(list ->{
                FirebaseOrderUtil.getOrderByID(list.get(0)).thenAccept(order -> {
                    text = "Books: ";
                    for(String bookName: order.getBookIDs())
                    {
                        text += bookName+ ", ";
                    }
                    text = text.substring(0,text.length()-2);
                    text += "\nDate:\n     " + order.getStringDate();
                    text += "\nPrice:\n     " + order.getTotalWithTax();
                    order1.setText(text);
                });
            });

        });
    }
    public void setOrder(String Id,int num)
    {
        FirebaseOrderUtil.getOrderByID(Id).thenAccept(order -> {
            text = "Books:";
            for(String bookName: order.getBookIDs())
            {
                text += bookName + "\n      ";
            }
            if(num == 1)
                order1.setText(text);
            if(num == 2)
                order2.setText(text);
        });
    }
}