package edu.utsa.cs3773.bookworkburrow.controller;

import android.text.Editable;
import android.text.TextWatcher;

import edu.utsa.cs3773.bookworkburrow.view.SearchLayout;

public class SearchTextController implements TextWatcher {

    private final SearchLayout mLayout;

    public SearchTextController(SearchLayout _layout) {
        mLayout = _layout;
    }

    @Override
    public void beforeTextChanged(CharSequence _s, int _start, int _count, int _after) {
    }

    @Override
    public void onTextChanged(CharSequence _s, int _start, int _before, int _count) {
    }

    @Override
    public void afterTextChanged(Editable _s) {
        mLayout.setSearchKeyword(_s.toString());
    }

} // class SearchTextController
