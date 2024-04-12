package edu.utsa.cs3773.bookworkburrow.controller;

import android.view.View;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.view.NavigationalActivity;

public class HomeController implements View.OnClickListener {

    private final NavigationalActivity mContext;

    public HomeController(NavigationalActivity _context) {
        mContext = _context;
    }

    @Override
    public void onClick(View _view) {

        int viewID = _view.getId();
        Object viewTag = _view.getTag();

        if (viewID == R.id.home_button_update_goal) {

            // TODO: Update

        }
        else if (viewTag != null) {

            String tag = viewTag.toString();
            if (tag.equals("Continue")) {

                // TODO: Continue book

            } else if (tag.equals("Favorites")) {

                // TODO: GO to book page
            }
        }
    }

} // class HomeController
