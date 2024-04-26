package edu.utsa.cs3773.bookworkburrow.view;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;

import edu.utsa.cs3773.bookworkburrow.FirebaseUserUtil;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.controller.CartController;
import edu.utsa.cs3773.bookworkburrow.model.Book;
import edu.utsa.cs3773.bookworkburrow.model.Order;

public class CartLayout extends NavigationalLayout {

    private TextView mSubtotalCostText;

    public CartLayout(NavigationalActivity _context, ViewGroup _parent) {
        super(_context, _parent, R.layout.layout_cart);
    }

    @Override
    protected void onDisplay() {

        mSubtotalCostText = mLayoutView.findViewById(R.id.cart_text_subtotal_cost);

        FirebaseUserUtil.getCurrUser().thenAccept(account ->{

            Order order = account.getCart();
            Log.d("Cart books", order.getCartList().toString());

            CartController cartController = new CartController(mContext);

            AppCompatButton checkoutButton = mLayoutView.findViewById(R.id.cart_button_checkout);
            checkoutButton.setOnClickListener(cartController);

            // Update cart
            this.updateCart(order);
        });
    }

    private void updateCart(Order _order) {

        LinearLayout bookContainer = mLayoutView.findViewById(R.id.cart_layout_book_container);
        bookContainer.removeAllViews();

        for (Book book : _order.getCartList()) {

            View bookLayout = mInflater.inflate(R.layout.layout_cart_book, bookContainer, false);

            TextView bookTitleText = bookLayout.findViewById(R.id.cart_book_text_title);
            bookTitleText.setText(mContext.getString(R.string.cart_book_text_title, book.getTitle()));

            TextView bookAuthorText = bookLayout.findViewById(R.id.cart_book_text_author);
            bookAuthorText.setText(mContext.getString(R.string.cart_book_text_author, book.getAuthor()));

            TextView bookPriceText = bookLayout.findViewById(R.id.cart_book_text_price);
            bookPriceText.setText(mContext.getString(R.string.cart_book_text_price, book.getPrice()));

            AppCompatButton removeButton = bookLayout.findViewById(R.id.cart_book_button_remove);
            removeButton.setOnClickListener(view -> this.removeBook(_order, book));

            ImageView bookImage = bookLayout.findViewById(R.id.cart_book_image);

            bookContainer.addView(bookLayout);

            Glide.with(mContext).load(book.getCoverURL().toString()).into(bookImage);
        }

        mSubtotalCostText.setText(mContext.getString(R.string.cart_text_subtotal_cost, _order.getSubtotal()));
    }

    private void removeBook(Order _order, Book _book) {

        // Remove book from order
        _order.removeBook(_book);

        // Update cart
        this.updateCart(_order);
    }

} // class CartLayout
