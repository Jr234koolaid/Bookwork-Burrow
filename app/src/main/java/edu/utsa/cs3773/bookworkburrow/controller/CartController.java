package edu.utsa.cs3773.bookworkburrow.controller;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.View;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.view.ConfirmPurchaseActivity;
import edu.utsa.cs3773.bookworkburrow.view.NavigationalActivity;

public class CartController implements View.OnClickListener {

    private final NavigationalActivity mContext;

    public CartController(NavigationalActivity _context) {
        mContext = _context;
    }

    @Override
    public void onClick(View _view) {

        int viewId = _view.getId();
        if(viewId == R.id.cart_button_checkout){
            Intent intent = new Intent(mContext, ConfirmPurchaseActivity.class);
            startActivity(mContext, intent, null);
        }

    }

} // class CartController
