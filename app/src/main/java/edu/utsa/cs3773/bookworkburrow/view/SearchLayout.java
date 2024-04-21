package edu.utsa.cs3773.bookworkburrow.view;

import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import edu.utsa.cs3773.bookworkburrow.FirebaseBookUtils;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Book;

public class SearchLayout extends NavigationalLayout {

    private EditText            mSearchText;
    private AppCompatSpinner    mSortSpinner;
    private AppCompatSpinner    mGenreSpinner;
    private ConstraintLayout    mBookContainer;

    public SearchLayout(NavigationalActivity _context, ViewGroup _parent) {
        super(_context, _parent, R.layout.layout_search);
    }

    @Override
    protected void onDisplay() {

        mSearchText = mLayoutView.findViewById(R.id.search_edit_search);
        mSortSpinner = mLayoutView.findViewById(R.id.search_spinner_sort);
        mGenreSpinner = mLayoutView.findViewById(R.id.search_spinner_genre);
        mBookContainer = mLayoutView.findViewById(R.id.search_layout_book_container);

        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(mContext, R.array.sort_array, R.layout.layout_search_spinner_item);
        sortAdapter.setDropDownViewResource(R.layout.layout_search_spinner_dropdown);

        ArrayAdapter<CharSequence> genreAdapter = ArrayAdapter.createFromResource(mContext, R.array.genre_array, R.layout.layout_search_spinner_item);
        genreAdapter.setDropDownViewResource(R.layout.layout_search_spinner_dropdown);

        mSortSpinner.setAdapter(sortAdapter);
        mGenreSpinner.setAdapter(genreAdapter);

        FirebaseBookUtils.getAllBookIDs().thenAccept(bookList -> {

            for (int i = 0; i < bookList.size(); i++) {

                String bookID = bookList.get(i);
                FirebaseBookUtils.getBookByID(bookID).thenAccept(book -> this.showBook(book));
            }
        });
    }

    private void search() {

        // TODO: Something with search
    }

    private void showBook(Book _book) {

        // Book properties
        int width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 140.f, mMetrics));
        int height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 210.f, mMetrics));

        ImageButton imageButton = new ImageButton(mContext);
        imageButton.setId(View.generateViewId());
        imageButton.setTag(_book.getId());
        imageButton.setLayoutParams(new ConstraintLayout.LayoutParams(width, height));
        imageButton.setOnClickListener(view -> this.openBook((view.getTag() == null) ? null : view.getTag().toString()));
        imageButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
        imageButton.setScaleType(ImageView.ScaleType.FIT_XY);

        this.addToContainer(imageButton);

        Glide.with(mContext).load(_book.getCoverURL().toString()).into(imageButton);
    }

    private void addToContainer(ImageButton _imageButton) {

        // Add image button to container
        mBookContainer.addView(_imageButton);

        // Place image button in correct spot
        int ID = _imageButton.getId();

        int childCount = mBookContainer.getChildCount();
        int index = (childCount - 3);

        // Set layout set
        int marginTop = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24.f, mMetrics));

        ConstraintSet layoutSet = new ConstraintSet();
        layoutSet.clone(mBookContainer);

        if ((childCount % 2) == 1) {
            layoutSet.connect(_imageButton.getId(), ConstraintSet.START, mBookContainer.getId(), ConstraintSet.START, 0);

        } else {

            layoutSet.connect(_imageButton.getId(), ConstraintSet.END, mBookContainer.getId(), ConstraintSet.END, 0);
        }

        if (index < 0) {
            layoutSet.connect(_imageButton.getId(), ConstraintSet.TOP, mBookContainer.getId(), ConstraintSet.TOP);

        } else {

            View child = mBookContainer.getChildAt(index);

            layoutSet.connect(_imageButton.getId(), ConstraintSet.TOP, child.getId(), ConstraintSet.BOTTOM, marginTop);
        }

        // Apply layout set
        layoutSet.applyTo(mBookContainer);
    }

    private void openBook(String _bookID) {

        if (_bookID == null) return;

        Intent intent = new Intent(mContext, BookSummaryActivity.class);
        intent.putExtra("bookid", _bookID);

        mContext.startActivity(intent);
    }

} // class SearchLayout
