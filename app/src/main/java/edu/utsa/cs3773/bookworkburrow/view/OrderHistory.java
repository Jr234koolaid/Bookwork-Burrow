package edu.utsa.cs3773.bookworkburrow.view;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.util.ArrayList;

import edu.utsa.cs3773.bookworkburrow.FirebaseOrderUtil;
import edu.utsa.cs3773.bookworkburrow.FirebaseUserUtil;
import edu.utsa.cs3773.bookworkburrow.databinding.ActivityOrderHistoryBinding;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Account;
import edu.utsa.cs3773.bookworkburrow.model.Order;

public class OrderHistory extends AppCompatActivity {
    private Account account;
    private ArrayList<String> temp;
    private Order currentOrder;
    String text;
    ArrayList<String> printList;
    TextView list;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        /*list = findViewById(R.id.list);
        FirebaseUserUtil.getCurrUser().thenAccept(Account ->{
            account = Account;
            FirebaseOrderUtil.getOrderHistory(account.getUID()).thenAccept(ArrayList ->{
                temp = ArrayList;
                for(int i = 0; i < temp.size();i++)
                {
                    text = "";
                    FirebaseOrderUtil.getOrderByID(temp.get(i)).thenAccept(Order -> {
                        currentOrder = Order;
                    });
                }
            });
        });*/

    }
}