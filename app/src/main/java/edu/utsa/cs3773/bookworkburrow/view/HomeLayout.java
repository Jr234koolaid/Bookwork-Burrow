package edu.utsa.cs3773.bookworkburrow.view;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.utsa.cs3773.bookworkburrow.FirebaseUtil;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.controller.HomeController;
import edu.utsa.cs3773.bookworkburrow.model.Account;

public class HomeLayout extends NavigationalLayout {

    public HomeLayout(NavigationalActivity _context, ViewGroup _parent) {
        super(_context, _parent, R.layout.layout_home);
    }

    @Override
    protected void onDisplay() {

        // TODO: Update with the user info
        Account account = FirebaseUtil.getCurrUser();

        HomeController controller = new HomeController(mContext);

        TextView welcomeText = mLayoutView.findViewById(R.id.home_text_welcome);
        welcomeText.setText(mContext.getString(R.string.home_text_header_welcome, "User"));

        ProgressBar bookProgress = mLayoutView.findViewById(R.id.home_bar_progress);
        bookProgress.setProgress((int)((21.0 / 30.0) * 100.0));

        TextView progressText = mLayoutView.findViewById(R.id.home_text_progress_count);
        progressText.setText(mContext.getString(R.string.home_text_progress_count, 21));

        TextView goalText = mLayoutView.findViewById(R.id.home_text_goal);
        goalText.setText(mContext.getString(R.string.home_text_progress_goal, 30));

        Button goalUpdateButton = mLayoutView.findViewById(R.id.home_button_update_goal);
        goalUpdateButton.setOnClickListener(controller);

        ImageButton bookContinue = mLayoutView.findViewById(R.id.home_button_continue_book);

        LinearLayout favoritesLayout = mLayoutView.findViewById(R.id.home_layout_favorites);

        LinearLayout bookshelfLayout = mLayoutView.findViewById(R.id.home_layout_bookshelf);
    }

} // class HomeLayout
