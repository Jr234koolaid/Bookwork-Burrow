package edu.utsa.cs3773.bookworkburrow.view;

import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;

import edu.utsa.cs3773.bookworkburrow.FirebaseBookUtils;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Book;

public class SearchLayout extends NavigationalLayout {

    private EditText            mSearchText;
    private ConstraintLayout    mBookContainer;

    public SearchLayout(NavigationalActivity _context, ViewGroup _parent) {
        super(_context, _parent, R.layout.layout_search);
    }

    @Override
    protected void onDisplay() {

        mSearchText = mLayoutView.findViewById(R.id.search_edit_search);
        mBookContainer = mLayoutView.findViewById(R.id.search_layout_book_container);

        FirebaseBookUtils.getAllBookIDs().thenAccept(bookList -> {

            for (int i = 0; i < bookList.size(); i++) {

                String bookID = bookList.get(i);
                if ((i % 2) == 0) {
                    FirebaseBookUtils.getBookByID(bookID).thenAccept(book -> this.showBook(book, true));

                } else {
                    FirebaseBookUtils.getBookByID(bookID).thenAccept(book -> this.showBook(book, false));
                }
            }
        });
    }

    private void search() {

        // TODO: Something with search
    }

    private void showBook(Book _book, boolean _start) {

        // Book properties
        int width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 140.f, mMetrics));
        int height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 195.f, mMetrics));

        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(width, height);

        ImageButton imageButton = new ImageButton(mContext);
        imageButton.setId(View.generateViewId());
        imageButton.setTag(_book.getId());
        imageButton.setLayoutParams(layoutParams);
        imageButton.setOnClickListener(view -> this.openBook((view.getTag() == null) ? null : view.getTag().toString()));

        Glide.with(mContext)
                .load(FirebaseStorage.getInstance().getReferenceFromUrl(_book.getCoverURL().toString()))
                .into(imageButton);

        mBookContainer.addView(imageButton);

        // Constraint layout properties
        int marginStart = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32.f, mMetrics));
        int marginEnd = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32.f, mMetrics));
        int marginTop = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24.f, mMetrics));

        ConstraintSet layoutSet = new ConstraintSet();
        layoutSet.clone(mBookContainer);

        int childCount = mBookContainer.getChildCount();
        int index = (childCount - 3);

        if (childCount % 2 == 1) {

            layoutSet.connect(imageButton.getId(), ConstraintSet.START, mBookContainer.getId(), ConstraintSet.START, marginStart);

            if (index < 0) {
                layoutSet.connect(imageButton.getId(), ConstraintSet.TOP, mBookContainer.getId(), ConstraintSet.TOP);

            } else {

                View child = mBookContainer.getChildAt(index);

                layoutSet.connect(imageButton.getId(), ConstraintSet.TOP, child.getId(), ConstraintSet.BOTTOM, marginTop);
            }

        } else {

            layoutSet.connect(imageButton.getId(), ConstraintSet.END, mBookContainer.getId(), ConstraintSet.END, marginEnd);

            if (index < 0) {
                layoutSet.connect(imageButton.getId(), ConstraintSet.TOP, mBookContainer.getId(), ConstraintSet.TOP);

            } else {

                View child = mBookContainer.getChildAt(index);

                layoutSet.connect(imageButton.getId(), ConstraintSet.TOP, child.getId(), ConstraintSet.BOTTOM, marginTop);
            }
        }

        layoutSet.applyTo(mBookContainer);
    }

    private void openBook(String _bookID) {

        if (_bookID == null) return;

        // TODO: Use intent to go to right book?
        mContext.startActivity(new Intent(mContext, BookActivity.class));
    }

} // class SearchLayout
