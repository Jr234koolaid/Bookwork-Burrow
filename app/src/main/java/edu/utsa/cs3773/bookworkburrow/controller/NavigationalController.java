package edu.utsa.cs3773.bookworkburrow.controller;

import android.view.View;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.view.NavigationalActivity;

public class NavigationalController implements View.OnClickListener {

    private final NavigationalActivity m_activity;

    public NavigationalController(NavigationalActivity _activity) {
        m_activity = _activity;
    }

    @Override
    public void onClick(View _view) {

        int viewID = _view.getId();
        if (viewID == R.id.navigational_button_settings) {

            // TODO: INTENT

        } else if (viewID == R.id.navigational_button_home) {
            m_activity.showHomeLayout();

        } else if (viewID == R.id.navigational_button_search) {
            m_activity.showSearchLayout();

        } else if (viewID == R.id.navigational_button_cart) {
            m_activity.showCartLayout();

        } else if (viewID == R.id.navigational_button_bookshelf) {
            m_activity.showBookshelfLayout();
        }
    }

} // class NavigationalController
