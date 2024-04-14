package edu.utsa.cs3773.bookworkburrow.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.widget.NestedScrollView;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.controller.NavigationalController;

public class NavigationalActivity extends AppCompatActivity {

    private enum NavigationState { NONE, HOME, SEARCH, CART, SETTINGS }

    private NavigationState     mNavigationState;

    private NestedScrollView    mScrollView;

    private ImageButton         mHomeButton;
    private ImageButton         mSearchButton;
    private ImageButton         mCartButton;
    private ImageButton         mSettingsButton;

    private Drawable            mBarBackground;
    private Drawable            mBarBackgroundSelected;

    private HomeLayout          mHomeLayout;
    private CartLayout          mCartLayout;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        this.setContentView(R.layout.activity_navigational);

        mNavigationState = NavigationState.NONE;

        mScrollView = this.findViewById(R.id.navigational_scroll);

        mBarBackground = AppCompatResources.getDrawable(this, R.drawable.background_bar);
        mBarBackgroundSelected = AppCompatResources.getDrawable(this, R.drawable.background_bar_selected);

        mHomeLayout = new HomeLayout(this, mScrollView);
        mCartLayout = new CartLayout(this, mScrollView);

        NavigationalController controller = new NavigationalController(this);

        mHomeButton = this.findViewById(R.id.navigational_button_home);
        mHomeButton.setOnClickListener(controller);

        mSearchButton = this.findViewById(R.id.navigational_button_search);
        mSearchButton.setOnClickListener(controller);

        mCartButton = this.findViewById(R.id.navigational_button_cart);
        mCartButton.setOnClickListener(controller);

        mSettingsButton = this.findViewById(R.id.navigational_button_settings);
        mSettingsButton.setOnClickListener(controller);

        // Select home as default
        this.selectHomeLayout();
    }

    public void selectHomeLayout() {

        if (mNavigationState == NavigationState.HOME) return;

        // Unselect current layout
        this.unselectLayout();

        // Add layout
        mHomeLayout.onShow();

        // Set status background
        mHomeButton.setBackground(mBarBackgroundSelected);

        // Set navigation state
        mNavigationState = NavigationState.HOME;
    }

    public void selectSearchLayout() {

        if (mNavigationState == NavigationState.SEARCH) return;

        // Unselect current layout
        this.unselectLayout();

        // Add layout
        //mSearchLayout.onShow();

        // Set status background
        mSearchButton.setBackground(mBarBackgroundSelected);

        // Set navigation state
        mNavigationState = NavigationState.SEARCH;
    }

    public void selectCartLayout() {

        if (mNavigationState == NavigationState.CART) return;

        // Unselect current layout
        this.unselectLayout();

        // Add layout
        mCartLayout.onShow();

        // Set status background
        mCartButton.setBackground(mBarBackgroundSelected);

        // Set navigation state
        mNavigationState = NavigationState.CART;
    }

    public void selectSettingsLayout() {

        if (mNavigationState == NavigationState.SETTINGS) return;

        // Unselect current layout
        this.unselectLayout();

        // Add layout
        //mSettingsLayout.onShow();

        // Set status background
        mSettingsButton.setBackground(mBarBackgroundSelected);

        // Set navigation state
        mNavigationState = NavigationState.SETTINGS;
    }

    private void unselectLayout() {

        if (mNavigationState == NavigationState.NONE) return;

        // Reset status background
        switch (mNavigationState) {

            case HOME:
                mHomeButton.setBackground(mBarBackground);
                break;

            case SEARCH:
                mSearchButton.setBackground(mBarBackground);
                break;

            case CART:
                mCartButton.setBackground(mBarBackground);
                break;

            case SETTINGS:
                mSettingsButton.setBackground(mBarBackground);
                break;
        }

        // Remove layout
        mScrollView.scrollTo(0, 0);
        mScrollView.removeAllViews();
    }

} // class NavigationalActivity
