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

import edu.utsa.cs3773.bookworkburrow.FirebaseDiscountUtils;
import edu.utsa.cs3773.bookworkburrow.FirebaseUtil;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Account;
import edu.utsa.cs3773.bookworkburrow.model.Book;

public class ConfirmPurchaseActivity extends AppCompatActivity {

    private ImageView backButton;
    private Button applyDiscount;
    private EditText discountCode;
    private TextView discountDisplay;
    private LinearLayout booksContainer;
    private TextView subtotal;
    private TextView tax;
    private TextView total;

    Account account;
    private double discountAmount;
    private boolean discountValid;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_purchase);

        account = FirebaseUtil.getCurrUser();
        applyDiscount = findViewById(R.id.discount_apply_button);
        backButton = findViewById(R.id.back_arrow);
        booksContainer = findViewById(R.id.booksAddedContainer);
        discountCode = findViewById(R.id.discount_code);
        discountDisplay = findViewById(R.id.discount_text_display);
        subtotal = findViewById(R.id.subtotal);
        tax = findViewById(R.id.tax);
        total = findViewById(R.id.total);
        Button checkout = findViewById(R.id.checkout_button);
        Button cancel = findViewById(R.id.cancel_button);

        backButton.setOnClickListener(view ->returnToCart());
        cancel.setOnClickListener(view -> returnToCart());
        checkout.setOnClickListener(view -> handleCheckout());
        applyDiscount.setOnClickListener(view -> handleDiscount());

        //dummy data for account
        Book book0 = new Book();
        book0.setTitle("Percy Jackson and the Lightning Thief");
        book0.setAuthor("Rick Riordan");
        book0.setPrice(15.99);

        Book book1 = new Book();
        book1.setTitle("Percy Jackson and the Titan's Curse");
        book1.setAuthor("Rick Riordan");
        book1.setPrice(15.99);

        Book book2 = new Book();
        book2.setTitle("Percy Jackson and the Sea of Monsters");
        book2.setAuthor("Rick Riordan");
        book2.setPrice(15.99);

        account.getCart().addBook(book0);
        account.getCart().addBook(book1);
        account.getCart().addBook(book2);
        account.getCart().addBook(book2);

        loadBookViews();
        setPrices();

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
        for (Book book : account.getCart().getCartList()) {
            LayoutInflater inflater = LayoutInflater.from(this);
            // Inflate the individual book layout
            LinearLayout bookCartLayout = (LinearLayout) inflater.inflate(R.layout.book_cart_layout, null, false);

            TextView bookTitle = bookCartLayout.findViewById(R.id.cart_book_title);
            TextView bookAuthor = bookCartLayout.findViewById(R.id.cart_book_author);
            TextView bookPrice = bookCartLayout.findViewById(R.id.cart_book_price);

            bookTitle.setText(book.getTitle());
            bookAuthor.setText(book.getAuthor());
            bookPrice.setText("$" + book.getPrice());


            booksContainer.addView(bookCartLayout);
        }
    }

    public void returnToCart(){
        Intent cartView = new Intent(this, CartActivity.class);
        startActivity(cartView);

    }

    public void handleCheckout(){
        account.completeCheckout();
        Log.d("Cart after checkout", account.getCart().toString());
        LinearLayout root = findViewById(R.id.confirm_root);
        root.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(this);
        // Inflate the individual book layout
        LinearLayout confirmation = (LinearLayout) inflater.inflate(R.layout.purchase_complete, null, false);
        Button returnHome = confirmation.findViewById(R.id.purchase_return_home);
        returnHome.setOnClickListener(view ->returnToCart());
        root.addView(confirmation);

    }
    @SuppressLint("DefaultLocale")
    public void setPrices(){

        subtotal.setText(String.format("Subtotal $%.2f", account.getCart().getSubtotal()) );
        tax.setText(String.format("Tax $%.2f", account.getCart().getTax()));
        total.setText(String.format("Total $%.2f", account.getCart().getTotalWithTax()));
    }




}
