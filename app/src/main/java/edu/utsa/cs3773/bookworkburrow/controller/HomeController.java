package edu.utsa.cs3773.bookworkburrow.controller;

import android.view.View;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.view.NavigationalActivity;

public class HomeController implements View.OnClickListener {

    private final NavigationalActivity m_activity;

    public HomeController(NavigationalActivity _activity) {
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

        } else if (viewTag.equals("Book")) {

            // TODO: GO to book
        }
    }

} // class HomeController
