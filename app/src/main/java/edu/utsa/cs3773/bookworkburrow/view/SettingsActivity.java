package edu.utsa.cs3773.bookworkburrow.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import edu.utsa.cs3773.bookworkburrow.FirebaseUserUtil;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Account;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{
    CheckBox show;
    TextView password;
    TextView email;
    int textSize;
    Account account = FirebaseUserUtil.getCurrUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupImageButton(R.id.navigational_button_home);
        setupImageButton(R.id.navigational_button_search);
        setupImageButton(R.id.navigational_button_cart);
        setUpTextButton(R.id.logout);

        setUpTextButton(R.id.orderHistory);

        show=(CheckBox)findViewById(R.id.checkBox);
        show.setOnClickListener(this);

        email = findViewById(R.id.EmailAddress);
        email.setText(account.getEmail());

        password = findViewById(R.id.editPassword);
        //password.setText(account.getPassword());

        AppCompatButton changeButton = this.findViewById(R.id.changePassword);
        changeButton.setOnClickListener(this);

    }

    public void onClick(View v)
    {
        if(show.isChecked())
        {
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else
        {
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        if(v.getId() == R.id.changePassword)
        {
            Toast toast = Toast.makeText(this, "Check your email for a link to change your password", Toast.LENGTH_LONG);
            toast.show();
        }
        Intent intent;
        switch (v.getId()) {
            case R.id.navigational_button_home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Log.d("Nav bar clicked", "Account");
                break;
            case R.id.navigational_button_search:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Log.d("Nav bar clicked", "BookShelf");
                break;
            case R.id.navigational_button_cart:
                intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                Log.d("Nav bar clicked", "Cart");
                break;
            case R.id.logout:
                intent = new Intent(this, LoginActivity.class);
                FirebaseUserUtil.logOut();
                startActivity(intent);
                Log.d("Logout clicked", "Logged out");
                break;
            case R.id.orderHistory:
                intent = new Intent(this, OrderHistory.class);
                startActivity(intent);
                Log.d("Order history clicked", "Order history");
                break;
        }

    }
    private void setupButton(int buttonID)
    {
        Button button = findViewById(buttonID);
        button.setOnClickListener( this);
    }
    private void setUpTextButton(int buttonID)
    {
        TextView button = findViewById(buttonID);
        button.setOnClickListener(this);
    }
    private void setupImageButton(int buttonID){

        ImageButton button = findViewById(buttonID);
        button.setOnClickListener(this);
    }

}