package edu.utsa.cs3773.bookworkburrow.controller;

import android.view.View;

import edu.utsa.cs3773.bookworkburrow.view.NavigationalActivity;

public class CartController implements View.OnClickListener {

    private final NavigationalActivity mContext;

    public CartController(NavigationalActivity _context) {
        mContext = _context;
    }

    @Override
    public void onClick(View _view) {

        int viewId = _view.getId();

    }

} // class CartController
