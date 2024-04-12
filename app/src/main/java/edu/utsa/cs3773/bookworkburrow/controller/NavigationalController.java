package edu.utsa.cs3773.bookworkburrow.controller;

import android.view.View;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.view.NavigationalActivity;

public class NavigationalController implements View.OnClickListener {

    private final NavigationalActivity mContext;

    public NavigationalController(NavigationalActivity _context) {
        mContext = _context;
    }

    @Override
    public void onClick(View _view) {

        int viewID = _view.getId();
        if (viewID == R.id.navigational_button_settings) {

            // TODO: INTENT

        } else if (viewID == R.id.navigational_button_home) {
            mContext.selectHomeLayout();

        } else if (viewID == R.id.navigational_button_search) {
            mContext.selectSearchLayout();

        } else if (viewID == R.id.navigational_button_cart) {
            mContext.selectCartLayout();

        } else if (viewID == R.id.navigational_button_bookshelf) {
            mContext.selectBookshelfLayout();
        }
    }

} // class NavigationalController
