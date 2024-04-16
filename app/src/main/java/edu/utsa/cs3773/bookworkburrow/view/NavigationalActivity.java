package edu.utsa.cs3773.bookworkburrow.view;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import edu.utsa.cs3773.bookworkburrow.R;

public class NavigationalActivity extends AppCompatActivity {

    private enum NavigationState { NONE, HOME, SEARCH, CART, SETTINGS }

    private NavigationState     mNavigationState;

    private NestedScrollView    mScrollView;

    private ImageButton         mHomeButton;
    private ImageButton         mSearchButton;
    private ImageButton         mCartButton;
    private ImageButton         mSettingsButton;

    private HomeLayout          mHomeLayout;
    private CartLayout          mCartLayout;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        super.onCreate(_savedInstanceState);
        this.setContentView(R.layout.activity_navigational);

        mNavigationState = NavigationState.NONE;

        mScrollView = this.findViewById(R.id.navigational_scroll);

        mHomeLayout = new HomeLayout(this, mScrollView);
        mCartLayout = new CartLayout(this, mScrollView);

        mHomeButton = this.findViewById(R.id.navigational_button_home);
        mHomeButton.setOnClickListener(view -> this.show(NavigationalActivity.NavigationState.HOME));

        mSearchButton = this.findViewById(R.id.navigational_button_search);
        mSearchButton.setOnClickListener(view -> this.show(NavigationalActivity.NavigationState.SEARCH));

        mCartButton = this.findViewById(R.id.navigational_button_cart);
        mCartButton.setOnClickListener(view -> this.show(NavigationalActivity.NavigationState.CART));

        mSettingsButton = this.findViewById(R.id.navigational_button_settings);
        mSettingsButton.setOnClickListener(view -> this.show(NavigationalActivity.NavigationState.SETTINGS));

        // show home as default
        this.show(NavigationState.HOME);
    }

    private void show(NavigationState _state) {

        if (mNavigationState == _state) return;

        switch (_state) {

            case HOME:
                this.selectLayout(mHomeLayout, mHomeButton, R.drawable.home_selected);
                break;

            case SEARCH:
                this.selectLayout(null, mSearchButton, R.drawable.search_selected);
                break;

            case CART:
                this.selectLayout(mCartLayout, mCartButton, R.drawable.shopping_cart_selected);
                break;

            case SETTINGS:
                this.selectLayout(null, mSettingsButton, R.drawable.settings_selected);
                break;
        }

        // Set navigation state
        mNavigationState = _state;
    }

    private void selectLayout(NavigationalLayout _layout, ImageButton _button, int _resource) {

        // Unselect current layout
        this.unselectLayout();

        // Add layout
        _layout.onShow();

        // Set image resource
        _button.setImageResource(_resource);
    }

    private void unselectLayout() {

        if (mNavigationState == NavigationState.NONE) return;

        // Reset image resource
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

            case SETTINGS:
                mSettingsButton.setImageResource(R.drawable.settings);
                break;
        }

        // Remove layout
        mScrollView.scrollTo(0, 0);
        mScrollView.removeAllViews();
    }

} // class NavigationalActivity
