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
        if (viewID == R.id.navigational_button_home) {
            mContext.show(NavigationalActivity.NavigationState.HOME);

        } else if (viewID == R.id.navigational_button_search) {
            mContext.show(NavigationalActivity.NavigationState.SEARCH);

        } else if (viewID == R.id.navigational_button_cart) {
            mContext.show(NavigationalActivity.NavigationState.CART);

        } else if (viewID == R.id.navigational_button_settings) {
            mContext.show(NavigationalActivity.NavigationState.SETTINGS);
        }
    }

} // class NavigationalController
