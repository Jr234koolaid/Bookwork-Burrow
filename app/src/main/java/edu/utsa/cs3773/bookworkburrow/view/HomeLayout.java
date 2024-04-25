package edu.utsa.cs3773.bookworkburrow.view;

import android.content.Intent;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import edu.utsa.cs3773.bookworkburrow.FirebaseBookUtils;
import edu.utsa.cs3773.bookworkburrow.FirebaseUserUtil;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Book;

public class HomeLayout extends NavigationalLayout {

    public HomeLayout(NavigationalActivity _context, ViewGroup _parent) {
        super(_context, _parent, R.layout.layout_home);
    }

    @Override
    protected void onDisplay() {
      
        FirebaseUserUtil.getCurrUser().thenAccept(account ->{

            TextView welcomeText = mLayoutView.findViewById(R.id.home_text_welcome);
            welcomeText.setText(mContext.getString(R.string.home_text_header_welcome, account.getFirstName()));

            int readingGoal = account.getReadingGoal();
            int booksRead = account.getBooksRead();

            ProgressBar bookProgress = mLayoutView.findViewById(R.id.home_bar_progress);
            bookProgress.setProgress((int)((booksRead * 100.0) / readingGoal));

            TextView progressText = mLayoutView.findViewById(R.id.home_text_progress_count);
            progressText.setText(mContext.getString(R.string.home_text_progress_count, booksRead));

            TextView goalText = mLayoutView.findViewById(R.id.home_text_goal);
            goalText.setText(mContext.getString(R.string.home_text_progress_goal, readingGoal));

            Button goalUpdateButton = mLayoutView.findViewById(R.id.home_button_update_goal);
            goalUpdateButton.setOnClickListener(view -> updateGoal());

            LinearLayout favoritesLayout = mLayoutView.findViewById(R.id.home_layout_favorites);
            LinearLayout bookshelfLayout = mLayoutView.findViewById(R.id.home_layout_bookshelf);

            ArrayList<String> favoritesIDList = account.getFavorites();
            if (favoritesIDList == null || favoritesIDList.isEmpty()) {
                showText(favoritesLayout, R.string.home_text_no_favorites);

            } else {

                for (String bookID : favoritesIDList) {
                    FirebaseBookUtils.getBookByID(bookID).thenAccept(book -> showBook(favoritesLayout, book));
                }
            }

            ArrayList<String> ownedIDList = account.getBooksOwned();
            if (ownedIDList == null || ownedIDList.isEmpty()) {
                showText(bookshelfLayout, R.string.home_text_no_books);

            } else {

                for (String bookID : ownedIDList) {
                    FirebaseBookUtils.getBookByID(bookID).thenAccept(book -> showBook(bookshelfLayout, book));
                }
            }
        });
    }

    private void showBook(LinearLayout _layout, Book _book) {

        int width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 140.f, mMetrics));
        int height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 195.f, mMetrics));

        int marginEnd = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36.f, mMetrics));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMarginEnd(marginEnd);

        ImageButton imageButton = new ImageButton(mContext);
        imageButton.setTag(_book.getId());
        imageButton.setLayoutParams(layoutParams);
        imageButton.setOnClickListener(view -> this.openBook(_book.getId()));

        _layout.addView(imageButton);

        Glide.with(mContext).load(_book.getCoverURL().toString()).into(imageButton);
    }

    private void showText(LinearLayout _layout, int _stringResource) {

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        TextView noBooksText = new TextView(mContext);
        noBooksText.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        noBooksText.setTypeface(ResourcesCompat.getFont(mContext, R.font.commissioner_medium));
        noBooksText.setText(mContext.getString(_stringResource));
        noBooksText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.f);

        _layout.addView(noBooksText);
    }

    private void updateGoal() {

        mContext.startActivity(new Intent(mContext, UpdateReadingGoalActivity.class));
    }

    private void openBook(String _bookID) {

        if (_bookID == null) return;

        // TODO: Use intent to go to right book?

        Intent readIntent = new Intent(mContext, ReadingActivity.class);
        readIntent.putExtra("bookID", _bookID);
        mContext.startActivity(readIntent);


    }

} // class HomeLayout
