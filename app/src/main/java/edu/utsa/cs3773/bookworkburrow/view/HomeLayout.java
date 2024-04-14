package edu.utsa.cs3773.bookworkburrow.view;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.controller.HomeController;

public class HomeLayout extends NavigationalLayout {

    public HomeLayout(NavigationalActivity _context, ViewGroup _parent) {
        super(_context, _parent, R.layout.layout_home);
    }

    @Override
    protected void onDisplay() {

        // TODO: Update with the user info
        HomeController controller = new HomeController(mContext);

        TextView welcomeText = mLayoutView.findViewById(R.id.home_text_welcome);

        ProgressBar bookProgress = mLayoutView.findViewById(R.id.home_bar_progress);

        TextView progressText = mLayoutView.findViewById(R.id.home_text_progress_count);

        TextView goalText = mLayoutView.findViewById(R.id.home_text_goal);

        Button goalUpdateButton = mLayoutView.findViewById(R.id.home_button_update_goal);
        goalUpdateButton.setOnClickListener(controller);

        ImageButton bookContinue = mLayoutView.findViewById(R.id.home_button_continue_book);

        ConstraintLayout favoritesLayout = mLayoutView.findViewById(R.id.home_constraint_favorites);
    }

} // class HomeLayout
