package edu.utsa.cs3773.bookworkburrow.view;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.controller.NavigationalController;

public class NavigationalActivity extends AppCompatActivity {

    private enum NavigationState { NONE, HOME, SEARCH, CART, BOOKSHELF }

    private NavigationState     mNavigationState;

    private NestedScrollView    mScrollView;

    private ImageButton         mHomeButton;
    private ImageButton         mSearchButton;
    private ImageButton         mCartButton;
    private ImageButton         mBookshelfButton;

    private NavigationalLayout  mNavigationalLayout;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        this.setContentView(R.layout.activity_navigational);

        mNavigationState = NavigationState.NONE;

        mScrollView = this.findViewById(R.id.navigational_scroll);

        NavigationalController controller = new NavigationalController(this);

        mHomeButton = this.findViewById(R.id.navigational_button_home);
        mHomeButton.setOnClickListener(controller);

        mSearchButton = this.findViewById(R.id.navigational_button_search);
        mSearchButton.setOnClickListener(controller);

        mCartButton = this.findViewById(R.id.navigational_button_cart);
        mCartButton.setOnClickListener(controller);

        mBookshelfButton = this.findViewById(R.id.navigational_button_bookshelf);
        mBookshelfButton.setOnClickListener(controller);

        ImageButton settingsButton = this.findViewById(R.id.navigational_button_settings);
        settingsButton.setOnClickListener(controller);

        // Select home as default
        this.selectHomeLayout();
    }

    public void selectHomeLayout() {

        if (mNavigationState == NavigationState.HOME) return;

        // Unselect current layout
        this.unselectLayout();

        // Add layout
        mNavigationalLayout = new HomeLayout(this, mScrollView);
        mNavigationalLayout.onShow();

        // Set status image
        mHomeButton.setImageResource(R.drawable.home_selected);

        // Set navigation state
        mNavigationState = NavigationState.HOME;
    }

    public void selectSearchLayout() {

        if (mNavigationState == NavigationState.SEARCH) return;

        // Unselect current layout
        this.unselectLayout();

        // Add layout
        //m_navigationLayout = new SearchLayout(this, m_scrollView);
        //m_navigationLayout.show();

        // Set status image
        mSearchButton.setImageResource(R.drawable.search_selected);

        // Set navigation state
        mNavigationState = NavigationState.SEARCH;
    }

    public void selectCartLayout() {

        if (mNavigationState == NavigationState.CART) return;

        // Unselect current layout
        this.unselectLayout();

        // Add layout
        //mNavigationLayout = new CartLayout(this, mScrollView);
        //mNavigationLayout.show();

        // Set status image
        mCartButton.setImageResource(R.drawable.shopping_cart_selected);

        // Set navigation state
        mNavigationState = NavigationState.CART;
    }

    public void selectBookshelfLayout() {

        if (mNavigationState == NavigationState.BOOKSHELF) return;

        // Unselect current layout
        this.unselectLayout();

        // Add layout
        //mNavigationLayout = new BookshelfLayout(this, mScrollView);
        //mNavigationLayout.show();

        // Set status image
        mBookshelfButton.setImageResource(R.drawable.book_selected);

        // Set navigation state
        mNavigationState = NavigationState.BOOKSHELF;
    }

    private void unselectLayout() {

        if (mNavigationState == NavigationState.NONE) return;

        // Reset status image
        switch (mNavigationState) {

            case HOME:
                mHomeButton.setImageResource(R.drawable.home);
                break;

            case SEARCH:
                mSearchButton.setImageResource(R.drawable.search);
                break;

            case CART:
                mCartButton.setImageResource(R.drawable.shopping_cart);
                break;

            case BOOKSHELF:
                mBookshelfButton.setImageResource(R.drawable.book);
                break;
        }

        // Remove layout
        mScrollView.removeAllViews();
    }

} // class NavigationalActivity
