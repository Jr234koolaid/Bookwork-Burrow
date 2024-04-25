package edu.utsa.cs3773.bookworkburrow.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import edu.utsa.cs3773.bookworkburrow.FirebaseBookUtils;
import edu.utsa.cs3773.bookworkburrow.FirebaseDiscountUtils;
import edu.utsa.cs3773.bookworkburrow.FirebaseUserUtil;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Account;
import edu.utsa.cs3773.bookworkburrow.model.Book;

public class ConfirmPurchaseActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextView applyDiscount;
    private EditText discountCode;
    private TextView discountDisplay;
    private LinearLayout booksContainer;
    private TextView subtotal;
    private TextView tax;
    private TextView total;

    Account account;
    private double discountAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_purchase);
        applyDiscount = findViewById(R.id.discount_apply_button);
        backButton = findViewById(R.id.back_arrow);
        booksContainer = findViewById(R.id.booksAddedContainer);
        discountCode = findViewById(R.id.discount_code);
        discountDisplay = findViewById(R.id.discount_text_display);
        subtotal = findViewById(R.id.subtotal);
        tax = findViewById(R.id.tax);
        total = findViewById(R.id.total);
        TextView checkout = findViewById(R.id.checkout_button);
        TextView cancel = findViewById(R.id.cancel_button);

        FirebaseUserUtil.getCurrUser().thenAccept(Account ->{
            account = Account;
            backButton.setOnClickListener(view ->returnToCart());
            cancel.setOnClickListener(view -> returnToCart());
            checkout.setOnClickListener(view -> handleCheckout());
            applyDiscount.setOnClickListener(view -> handleDiscount());
            loadBookViews();
            setPrices();

        });


    }

    @SuppressLint("DefaultLocale")
    private void handleDiscount() {
        String code = discountCode.getText().toString();
        if(code.length() == 0){
            Toast.makeText(this, "No discount code given", Toast.LENGTH_SHORT).show();
        }
        else{
            FirebaseDiscountUtils.verifyCode(code).thenAccept(Boolean ->{
                if(Boolean){
                    FirebaseDiscountUtils.getAmountByCode(code).thenAccept(Double ->{
                        discountAmount = Double;
                        account.getCart().setDiscount(discountAmount);
                        Log.d("Cart's discount", account.getCart().getDiscount() + "%");
                        displayDiscount();
                        Toast.makeText(this, String.format("%.0f%% Discount applied!", Double), Toast.LENGTH_SHORT).show();
                    });
                }
                else Toast.makeText(this, "Discount code not valid", Toast.LENGTH_SHORT).show();
            });

        }
    }

    @SuppressLint("DefaultLocale")
    private void displayDiscount() {
        discountDisplay.setText(String.format("Discount -$%.2f (%.0f%% off)", account.getCart().getSubtotal() * account.getCart().getDiscount(), discountAmount));
        discountDisplay.setVisibility(View.VISIBLE);
        account.getCart().setDiscount(discountAmount);
        Log.d("Cart's discount", account.getCart().getDiscount() + "%");
        setPrices();
    }

    public void loadBookViews() {
        for(Book book : account.getCart().getCartList()){
            LayoutInflater inflater = LayoutInflater.from(this);
            View bookLayout = inflater.inflate(R.layout.layout_cart_book, booksContainer, false);

            TextView bookTitleText = bookLayout.findViewById(R.id.cart_book_text_title);
            bookTitleText.setText(this.getString(R.string.cart_book_text_title, book.getTitle()));

            TextView bookAuthorText = bookLayout.findViewById(R.id.cart_book_text_author);
            bookAuthorText.setText(this.getString(R.string.cart_book_text_author, book.getAuthor()));

            TextView bookPriceText = bookLayout.findViewById(R.id.cart_book_text_price);
            bookPriceText.setText(this.getString(R.string.cart_book_text_price, book.getPrice()));

            TextView removeButton = bookLayout.findViewById(R.id.cart_book_button_remove);
            removeButton.setVisibility(View.INVISIBLE);

            ImageView bookImage = bookLayout.findViewById(R.id.cart_book_image);

            booksContainer.addView(bookLayout);

            Glide.with(this).load(book.getCoverURL().toString()).into(bookImage);
        }
    }


    public void returnToCart(){
        finish();

    }

    public void handleCheckout(){
        account.getCart().clear().thenAccept(Boolean -> {
            if(Boolean)Log.d("Cart cleared", "Success");
            else Log.d("Cart cleared", "Failed");
        });
        account.completeCheckout().thenAccept(String ->{
            Log.d("Cart after checkout", account.getCart().toString());
            LinearLayout root = findViewById(R.id.confirm_root);
            root.removeAllViews();

            LayoutInflater inflater = LayoutInflater.from(this);
            // Inflate the individual book layout
            LinearLayout confirmation = (LinearLayout) inflater.inflate(R.layout.purchase_complete, null, false);
            TextView code = confirmation.findViewById(R.id.confirmation_code);
            code.setText(String);
            TextView returnHome = confirmation.findViewById(R.id.purchase_return_home);
            returnHome.setOnClickListener(view -> this.startActivity(new Intent(this, NavigationalActivity.class)));
            root.addView(confirmation);

        });


    }
    @SuppressLint("DefaultLocale")
    public void setPrices(){
        subtotal.setText(String.format("Subtotal $%.2f", account.getCart().getSubtotal()) );
        tax.setText(String.format("Tax $%.2f", account.getCart().getTax()));
        total.setText(String.format("Total $%.2f", account.getCart().getTotalWithTax()));
    }



}