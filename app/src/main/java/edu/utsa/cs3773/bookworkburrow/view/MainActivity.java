package edu.utsa.cs3773.bookworkburrow.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import edu.utsa.cs3773.bookworkburrow.FirebaseUserUtil;
import edu.utsa.cs3773.bookworkburrow.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        this.setContentView(R.layout.activity_main);
        //FirebaseUserUtil.logOut();
        if (FirebaseUserUtil.isLoggedIn()) {
//            //tests for firebase utils
//            Account account = FirebaseUtil.getCurrUser();
//            FirebaseOrderUtil.getOrderHistory(account.getUID()).thenAccept(ArrayList ->{
//                for(String id : ArrayList){
//                    FirebaseOrderUtil.getOrderByID(id).thenAccept(Order ->{
//                        Log.d("Order IDs", Order.getOrderID());
//                        Log.d("Order Dates", Order.getDate().toString());
//                        for(Book book : Order.getCartList()) Log.d("Order Books", book.getTitle());
//                    });
//                }
//
//            });

            this.startActivity(new Intent(this, NavigationalActivity.class));
            this.finish();


        } else {

            this.startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }
    }

} // class MainActivity
