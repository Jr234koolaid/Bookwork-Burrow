package edu.utsa.cs3773.bookworkburrow.view;

import android.widget.ImageView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.io.IOException;
import java.util.ArrayList;

import edu.utsa.cs3773.bookworkburrow.FirebaseBookUtils;
import edu.utsa.cs3773.bookworkburrow.FirebaseUserUtil;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Account;
import edu.utsa.cs3773.bookworkburrow.model.Book;

public class HomeLayout extends NavigationalLayout {
    Account account;
    public HomeLayout(NavigationalActivity _context, ViewGroup _parent) {
        super(_context, _parent, R.layout.layout_home);
    }

    @Override
    protected void onDisplay() {
        FirebaseUserUtil.getCurrUser().thenAccept(account -> {
            this.account = account;

            TextView welcomeText = mLayoutView.findViewById(R.id.home_text_welcome);
            welcomeText.setText(mContext.getString(R.string.home_text_header_welcome, account.getFirstName()));

            ProgressBar bookProgress = mLayoutView.findViewById(R.id.home_bar_progress);
            int readingGoal = account.getReadingGoal();
            bookProgress.setProgress((int) ((0.0 / readingGoal) * 100.0));

            TextView progressText = mLayoutView.findViewById(R.id.home_text_progress_count);
            progressText.setText(mContext.getString(R.string.home_text_progress_count, 0));

            TextView goalText = mLayoutView.findViewById(R.id.home_text_goal);
            goalText.setText(mContext.getString(R.string.home_text_progress_goal, readingGoal));

            Button goalUpdateButton = mLayoutView.findViewById(R.id.home_button_update_goal);
            goalUpdateButton.setOnClickListener(view -> updateGoal());

            LinearLayout favoritesLayout = mLayoutView.findViewById(R.id.home_layout_favorites);
            ArrayList<String> favoritesIDList = account.getFavorites();
            if (favoritesIDList == null || favoritesIDList.isEmpty()) {
                showText(favoritesLayout, R.string.home_text_no_favorites);
            } else {
                for (String bookID : favoritesIDList) {
                    FirebaseBookUtils.getBookByID(bookID).thenAccept(book -> showBook(favoritesLayout, book));
                }
            }

            LinearLayout bookshelfLayout = mLayoutView.findViewById(R.id.home_layout_bookshelf);
            ArrayList<String> ownedIDList = account.getBooksOwned();
            if (ownedIDList == null || ownedIDList.isEmpty()) {
                showText(bookshelfLayout, R.string.home_text_no_books);
            } else {
                for (String bookID : ownedIDList) {
                    FirebaseBookUtils.getBookByID(bookID).thenAccept(book -> showBook(bookshelfLayout, book));
                }
            }

            ImageView shelfImageView = mLayoutView.findViewById(R.id.home_image_shelf);
            shelfImageView.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, BookshelfActivity.class);
                mContext.startActivity(intent);
            });
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
        imageButton.setOnClickListener(view -> this.openBook((view.getTag() == null) ? null : view.getTag().toString()));

        try {

            Bitmap coverBitmap = BitmapFactory.decodeStream(_book.getCoverURL().openConnection().getInputStream());
            imageButton.setImageBitmap(coverBitmap);

        } catch (IOException e) {
            imageButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray));
        }

        _layout.addView(imageButton);
    }

    private void showText(LinearLayout _layout, int _stringResource) {

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        int marginStart = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16.f, mMetrics));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMarginStart(marginStart);

        TextView noBooksText = new TextView(mContext);
        noBooksText.setLayoutParams(layoutParams);
        noBooksText.setTypeface(ResourcesCompat.getFont(mContext, R.font.commissioner_medium));
        noBooksText.setText(mContext.getString(_stringResource));
        noBooksText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.f);

        _layout.addView(noBooksText);
    }

    private void updateGoal() {

        // TODO: Update goal
    }

    private void openBook(String _bookID) {

        if (_bookID == null) return;

        // TODO: Use intent to go to right book?
        mContext.startActivity(new Intent(mContext, BookActivity.class));

    }

} // class HomeLayout
