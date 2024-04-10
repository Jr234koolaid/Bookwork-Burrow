package edu.utsa.cs3773.bookworkburrow.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.controller.HomeController;

public class HomeActivity extends AppCompatActivity {

    enum NavigationBarState {

        HOME,
        SEARCH,
        CART,
        BOOKSHELF
    }

    private NavigationBarState m_navigationState = NavigationBarState.HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        HomeController homeController = new HomeController(this);

        // TODO: Update with the user info

        ImageButton homeButton = this.findViewById(R.id.home_button_home);
        homeButton.setOnClickListener(homeController);

        ImageButton searchButton = this.findViewById(R.id.home_button_search);
        searchButton.setOnClickListener(homeController);

        ImageButton cartButton = this.findViewById(R.id.home_button_cart);
        cartButton.setOnClickListener(homeController);

        ImageButton bookshelfButton = this.findViewById(R.id.home_button_bookshelf);
        bookshelfButton.setOnClickListener(homeController);

        this.showHome(homeController);
    }

    public void showHome(HomeController _controller) {

        // Update view
        hideCurrentView();

        // TODO: Update with the user info

        TextView welcomeText = this.findViewById(R.id.home_text_welcome);

        ProgressBar bookProgress = this.findViewById(R.id.home_bar_progress);

        TextView progressText = this.findViewById(R.id.home_text_progress_count);

        TextView goalText = this.findViewById(R.id.home_text_goal);

        Button goalUpdateButton = this.findViewById(R.id.home_button_update_goal);
        goalUpdateButton.setOnClickListener(_controller);

        NestedScrollView scrollLayout = this.findViewById(R.id.home_scroll_layout);

        ImageButton bookContinue = this.findViewById(R.id.home_button_continue_book);

        ConstraintLayout scrollConstraintLayout = this.findViewById(R.id.home_scroll_constrained_layout);

        HorizontalScrollView favoritesLayout = this.findViewById(R.id.home_scroll_favorites_layout);

        ImageButton homeButton = this.findViewById(R.id.home_button_home);
        homeButton.setImageResource(R.drawable.home_selected);

        // Set navigation state
        m_navigationState = NavigationBarState.HOME;
    }

    private void hideHome() {

        // Update view
        ImageButton homeButton = this.findViewById(R.id.home_button_home);
        homeButton.setImageResource(R.drawable.home);
    }

    public void showSearch(HomeController _controller) {

        // Update view
        hideCurrentView();

        ImageButton searchButton = this.findViewById(R.id.home_button_search);
        searchButton.setImageResource(R.drawable.search_selected);

        // Set navigation state
        m_navigationState = NavigationBarState.SEARCH;
    }

    private void hideSearch() {

        // Update view
        ImageButton searchButton = this.findViewById(R.id.home_button_search);
        searchButton.setImageResource(R.drawable.search);
    }

    public void showCart(HomeController _controller) {

        // Update view
        hideCurrentView();

        ImageButton cartButton = this.findViewById(R.id.home_button_cart);
        cartButton.setImageResource(R.drawable.shopping_cart_selected);

        // Set navigation state
        m_navigationState = NavigationBarState.CART;
    }

    private void hideCart() {

        // Update view
        ImageButton cartButton = this.findViewById(R.id.home_button_cart);
        cartButton.setImageResource(R.drawable.shopping_cart);
    }

    public void showBookshelf(HomeController _controller) {

        // Update view
        hideCurrentView();

        ImageButton bookshelfButton = this.findViewById(R.id.home_button_bookshelf);
        bookshelfButton.setImageResource(R.drawable.book_selected);

        // Set navigation state
        m_navigationState = NavigationBarState.BOOKSHELF;
    }

    private void hideBookshelf() {

        // Update view
        ImageButton bookshelfButton = this.findViewById(R.id.home_button_bookshelf);
        bookshelfButton.setImageResource(R.drawable.book);
    }

    private void hideCurrentView() {

        switch (m_navigationState) {

            case HOME:
                this.hideHome();
                break;

            case SEARCH:
                this.hideSearch();
                break;

            case CART:
                this.hideCart();
                break;

            case BOOKSHELF:
                this.hideBookshelf();
                break;
        }
    }

} // class HomeActivity
