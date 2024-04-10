package edu.utsa.cs3773.bookworkburrow.controller;

import android.view.View;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.view.HomeActivity;

public class HomeController implements View.OnClickListener {

    private final HomeActivity m_activity;

    public HomeController(HomeActivity _activity) {

        m_activity = _activity;
    }

    @Override
    public void onClick(View _view) {

        int viewID = _view.getId();

        Object tag = _view.getTag();
        String viewTag = (tag == null) ? "" : tag.toString();

        if (viewID == R.id.home_button_update_goal) {

            // TODO: Update
        }
        // TODO: Remove, set tag
        else if (viewID == R.id.home_button_continue_book) {

            // TODO: Continue book
        }
        else if (viewID == R.id.home_button_home) {
            m_activity.showHome(this);

        }
        else if (viewID == R.id.home_button_search) {
            m_activity.showSearch(this);

        }
        else if (viewID == R.id.home_button_cart) {
            m_activity.showCart(this);

        }
        else if (viewID == R.id.home_button_bookshelf) {
            m_activity.showBookshelf(this);

        }
        else if (viewTag.equals("Book")) {

            // TODO: GO to book
        }
    }

} // class HomeController
