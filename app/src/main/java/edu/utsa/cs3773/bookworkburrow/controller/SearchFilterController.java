package edu.utsa.cs3773.bookworkburrow.controller;

import android.view.View;
import android.widget.AdapterView;

import edu.utsa.cs3773.bookworkburrow.view.SearchLayout;

public class SearchFilterController implements AdapterView.OnItemSelectedListener {

    private final SearchLayout mLayout;

    public SearchFilterController(SearchLayout _layout) {
        mLayout = _layout;
    }

    @Override
    public void onItemSelected(AdapterView<?> _parent, View _view, int _position, long _ID) {
        mLayout.setFilterItem(_position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> _parent) {
    }

} // class SearchFilterController
