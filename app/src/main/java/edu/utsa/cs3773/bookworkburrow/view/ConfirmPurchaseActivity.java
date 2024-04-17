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

        FirebaseUserUtil.getCurrUser().thenAccept(Account ->{
            account = Account;
            boolean loggedIn = FirebaseUserUtil.isLoggedIn();
            Log.d("account id", account.getUID());
            Log.d("Is logged in?", loggedIn + "!");
            Log.d("account name", account.getFirstName());
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


            FirebaseBookUtils.getAllBookIDs().thenAccept(ArrayList ->{
                for(String bookID: ArrayList){
                    FirebaseBookUtils.getBookByID(bookID).thenAccept(Book ->{
                        account.getCart().addBook(Book);
                        Log.d("Added book", Book.getTitle());
                        addBookView(Book);
                        setPrices();
                    });
                }
            });
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

    public void addBookView(Book book) {
        LayoutInflater inflater = LayoutInflater.from(this);
        // Inflate the individual book layout
        LinearLayout bookCartLayout = (LinearLayout) inflater.inflate(R.layout.book_cart_layout, null, false);

        TextView bookTitle = bookCartLayout.findViewById(R.id.cart_book_title);
        TextView bookAuthor = bookCartLayout.findViewById(R.id.cart_book_author);
        TextView bookPrice = bookCartLayout.findViewById(R.id.cart_book_price);
        Button remove = bookCartLayout.findViewById(R.id.book_cart_remove);

        remove.setVisibility(View.GONE);
        bookTitle.setText(book.getTitle());
        bookAuthor.setText(book.getAuthor());
        bookPrice.setText("$" + book.getPrice());


        booksContainer.addView(bookCartLayout);

    }

    public void returnToCart(){
        Intent cartView = new Intent(this, NavigationalActivity.class);
        startActivity(cartView);

    }

    public void handleCheckout(){
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
            returnHome.setOnClickListener(view ->returnToCart());
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