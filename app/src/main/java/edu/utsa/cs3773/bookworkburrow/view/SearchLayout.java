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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.CompletableFuture;

import edu.utsa.cs3773.bookworkburrow.FirebaseBookUtils;
import edu.utsa.cs3773.bookworkburrow.FirebaseUserUtil;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.controller.SearchFilterController;
import edu.utsa.cs3773.bookworkburrow.controller.SearchSortController;
import edu.utsa.cs3773.bookworkburrow.controller.SearchTextController;
import edu.utsa.cs3773.bookworkburrow.model.Book;

public class SearchLayout extends NavigationalLayout {

    private enum SortItem   { ASCENDING, DESCENDING };
    private enum FilterItem { NONE, EDUCATION, FANTASY, FICTION, ROMANCE };

    private SortItem[]              mSortArray;
    private FilterItem[]            mFilterArray;

    private SortItem                mSortItem;
    private FilterItem              mFilterItem;

    private String                  mKeyword;

    private ConstraintLayout        mBookContainer;

    public SearchLayout(NavigationalActivity _context, ViewGroup _parent) {
        super(_context, _parent, R.layout.layout_search);
    }

    @Override
    protected void onDisplay() {

        mSortArray = SortItem.values();
        mFilterArray = FilterItem.values();

        mSortItem = SortItem.ASCENDING;
        mFilterItem = FilterItem.NONE;

        mKeyword = "";

        mBookContainer = mLayoutView.findViewById(R.id.search_layout_book_container);

        EditText searchText = mLayoutView.findViewById(R.id.search_edit_search);
        searchText.addTextChangedListener(new SearchTextController(this));

        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(mContext, R.array.sort_array, R.layout.layout_search_spinner_item);
        sortAdapter.setDropDownViewResource(R.layout.layout_search_spinner_dropdown);

        AppCompatSpinner sortSpinner = mLayoutView.findViewById(R.id.search_spinner_sort);
        sortSpinner.setAdapter(sortAdapter);
        sortSpinner.setOnItemSelectedListener(new SearchSortController(this));

        ArrayAdapter<CharSequence> genreAdapter = ArrayAdapter.createFromResource(mContext, R.array.filter_array, R.layout.layout_search_spinner_item);
        genreAdapter.setDropDownViewResource(R.layout.layout_search_spinner_dropdown);

        AppCompatSpinner filterSpinner = mLayoutView.findViewById(R.id.search_spinner_filter);
        filterSpinner.setAdapter(genreAdapter);
        filterSpinner.setOnItemSelectedListener(new SearchFilterController(this));

        // Call update
        this.onUpdate();
    }

    public void setSearchKeyword(String _keyword) {

        // Set keyword
        mKeyword = ((_keyword == null) || _keyword.isEmpty()) ? "" : _keyword.toLowerCase();

        // Call update
        this.onUpdate();
    }

    public void setSortItem(int _position) {

        // Set sorting item
        mSortItem = mSortArray[_position];

        // Call update
        this.onUpdate();
    }

    public void setFilterItem(int _position) {

        // Set filtering item
        mFilterItem = mFilterArray[_position];

        // Call update
        this.onUpdate();
    }

    private void onUpdate() {

        FirebaseBookUtils.getAllBookIDs().thenAccept(bookIDList -> {
            this.generateBookList(bookIDList).thenAccept(bookList -> this.showBookList(bookList));
        });
    }

    private CompletableFuture<ArrayList<Book>> generateBookList(ArrayList<String> bookIDList) {

        CompletableFuture<ArrayList<Book>> completableFuture = new CompletableFuture<>();

        ArrayList<Book> bookList = new ArrayList<>();
        for (String bookID : bookIDList) {

            FirebaseBookUtils.getBookByID(bookID).thenAccept(book -> {

                // Add book to list
                bookList.add(book);

                // Check for completion
                if (bookList.size() == bookIDList.size()) {
                    completableFuture.complete(bookList);
                }
            });

        } return completableFuture;
    }

    private void showBookList(ArrayList<Book> bookList) {

        if (bookList.isEmpty()) return;

        // Clear container
        mBookContainer.removeAllViews();

        // Filter book list by keyword
        if (!mKeyword.isEmpty()) {
            bookList.removeIf(book -> (!book.getTitle().toLowerCase().contains(mKeyword)));
        }

        if (bookList.isEmpty()) return;

        // Filter book list by genre
        switch (mFilterItem) {

            case EDUCATION:
                bookList.removeIf(book -> (!book.getGenre().equalsIgnoreCase("education")));
                break;

            case FANTASY:
                bookList.removeIf(book -> (!book.getGenre().equalsIgnoreCase("fantasy")));
                break;

            case FICTION:
                bookList.removeIf(book -> (!book.getGenre().equalsIgnoreCase("fiction")));
                break;

            case ROMANCE:
                bookList.removeIf(book -> (!book.getGenre().equalsIgnoreCase("romance")));
                break;

            default: break;
        }

        if (bookList.isEmpty()) return;

        // Sort book list
        switch (mSortItem) {

            case ASCENDING:
                bookList.sort(Comparator.naturalOrder());
                break;

            case DESCENDING:
                bookList.sort(Comparator.reverseOrder());
                break;
        }

        // Show books
        for (Book book : bookList) {
            this.showBook(book);
        }
    }

    private void showBook(Book _book) {

        // Image button properties
        int width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 140.f, mMetrics));
        int height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 210.f, mMetrics));

        ImageButton imageButton = new ImageButton(mContext);
        imageButton.setId(View.generateViewId());
        imageButton.setTag(_book.getId());
        imageButton.setLayoutParams(new ConstraintLayout.LayoutParams(width, height));
        imageButton.setOnClickListener(view -> this.openBook((view.getTag() == null) ? null : view.getTag().toString()));
        imageButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
        imageButton.setScaleType(ImageView.ScaleType.FIT_XY);

        // Add image button to container
        mBookContainer.addView(imageButton);

        // Apply image button to container
        this.applyToContainer(imageButton);

        Glide.with(mContext).load(_book.getCoverURL().toString()).into(imageButton);
    }

    private void applyToContainer(ImageButton _imageButton) {

        // Place image button in correct spot
        int ID = _imageButton.getId();

        int childCount = mBookContainer.getChildCount();
        int index = (childCount - 3);

        // Set layout set
        int marginTop = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24.f, mMetrics));
        int marginStart = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6.f, mMetrics));
        int marginEnd = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6.f, mMetrics));

        ConstraintSet layoutSet = new ConstraintSet();
        layoutSet.clone(mBookContainer);

        if ((childCount % 2) == 1) {
            layoutSet.connect(ID, ConstraintSet.START, mBookContainer.getId(), ConstraintSet.START, marginStart);

        } else {
            layoutSet.connect(ID, ConstraintSet.END, mBookContainer.getId(), ConstraintSet.END, marginEnd);
        }

        if (index < 0) {
            layoutSet.connect(ID, ConstraintSet.TOP, mBookContainer.getId(), ConstraintSet.TOP);

        } else {

            View child = mBookContainer.getChildAt(index);

            layoutSet.connect(ID, ConstraintSet.TOP, child.getId(), ConstraintSet.BOTTOM, marginTop);
        }

        // Apply layout set
        layoutSet.applyTo(mBookContainer);
    }

    private void openBook(String _bookID) {

        if (_bookID == null) return;

        FirebaseUserUtil.getCurrUser().thenAccept(account->{

            boolean bookOwned = account.getBooksOwned().contains(_bookID);

            Intent intent = new Intent(mContext, (bookOwned) ? OwnedBookActivity.class : BookSummaryActivity.class);
            intent.putExtra("bookID", _bookID);

            mContext.startActivity(intent);
        });
    }

} // class SearchLayout
