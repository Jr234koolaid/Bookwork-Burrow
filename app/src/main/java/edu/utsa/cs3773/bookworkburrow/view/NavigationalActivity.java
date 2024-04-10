package edu.utsa.cs3773.bookworkburrow.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.controller.HomeController;
import edu.utsa.cs3773.bookworkburrow.controller.NavigationalController;

public class NavigationalActivity extends AppCompatActivity {

    enum NavigationState { NONE, HOME, SEARCH, CART, BOOKSHELF }

    private NavigationState         m_navigationState;

    private NestedScrollView        m_scrollLayouts;

    private ImageButton             m_settingsButton;

    private ImageButton             m_homeButton;
    private ImageButton             m_searchButton;
    private ImageButton             m_cartButton;
    private ImageButton             m_bookshelfButton;

    private ConstraintLayout        m_homeLayout;
    private ConstraintLayout        m_searchLayout;
    private ConstraintLayout        m_cartLayout;
    private ConstraintLayout        m_bookshelfLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigational);

        LayoutInflater layoutInflater = this.getLayoutInflater();

        // Set members
        m_navigationState = NavigationState.NONE;

        m_scrollLayouts = this.findViewById(R.id.navigational_scroll_layouts);

        m_settingsButton = this.findViewById(R.id.navigational_button_settings);

        m_homeButton = this.findViewById(R.id.navigational_button_home);
        m_searchButton = this.findViewById(R.id.navigational_button_search);
        m_cartButton = this.findViewById(R.id.navigational_button_cart);
        m_bookshelfButton = this.findViewById(R.id.navigational_button_bookshelf);

        View homeRoot = layoutInflater.inflate(R.layout.navigational_home, null);
        //View searchRoot = m_layoutInflater.inflate(R.layout.navigational_search, null);
        //View cartRoot = m_layoutInflater.inflate(R.layout.navigational_cart, null);
        //View bookshelfRoot = m_layoutInflater.inflate(R.layout.navigational_bookshelf, null);

        m_homeLayout = homeRoot.findViewById(R.id.navigational_home_layout);
        //m_searchLayout = homeRoot.findViewById(R.id.navigational_search_layout);
        //m_cartLayout = homeRoot.findViewById(R.id.navigational_cart_layout);
        //m_bookshelfLayout = homeRoot.findViewById(R.id.navigational_bookshelf_layout);

        // Set controller
        NavigationalController controller = new NavigationalController(this);

        m_settingsButton.setOnClickListener(controller);

        m_homeButton.setOnClickListener(controller);
        m_searchButton.setOnClickListener(controller);
        m_cartButton.setOnClickListener(controller);
        m_bookshelfButton.setOnClickListener(controller);

        // Show home as default
        this.showHomeLayout();
    }

    public void showHomeLayout() {

        // Unselect current layout
        this.unselectChild();

        // TODO: Update with the user info
        // Update layout views
        TextView welcomeText = m_homeLayout.findViewById(R.id.home_text_welcome);

        ProgressBar bookProgress = m_homeLayout.findViewById(R.id.home_bar_progress);

        TextView progressText = m_homeLayout.findViewById(R.id.home_text_progress_count);

        TextView goalText = m_homeLayout.findViewById(R.id.home_text_goal);

        Button goalUpdateButton = m_homeLayout.findViewById(R.id.home_button_update_goal);

        ImageButton bookContinue = m_homeLayout.findViewById(R.id.home_button_continue_book);

        ConstraintLayout favoritesLayout = m_homeLayout.findViewById(R.id.home_constraint_favorites);

        // Set controller
        HomeController controller = new HomeController(this);

        goalUpdateButton.setOnClickListener(controller);

        // Set status image
        m_homeButton.setImageResource(R.drawable.home_selected);

        // Add layout
        m_scrollLayouts.addView(m_homeLayout);

        // Set navigation state
        m_navigationState = NavigationState.HOME;
    }

    public void showSearchLayout() {

        // Unselect current layout
        this.unselectChild();

        // Set status image
        m_searchButton.setImageResource(R.drawable.search_selected);

        // Set navigation state
        m_navigationState = NavigationState.SEARCH;
    }

    public void showCartLayout() {

        // Unselect current layout
        this.unselectChild();

        // Set status image
        m_cartButton.setImageResource(R.drawable.shopping_cart_selected);

        // Set navigation state
        m_navigationState = NavigationState.CART;
    }

    public void showBookshelfLayout() {

        // Unselect current layout
        this.unselectChild();

        // Set status image
        m_bookshelfButton.setImageResource(R.drawable.book_selected);

        // Set navigation state
        m_navigationState = NavigationState.BOOKSHELF;
    }

    private void unselectChild() {

        if (m_navigationState == NavigationState.NONE) return;

        // Reset status image
        switch (m_navigationState) {

            case HOME:
                m_homeButton.setImageResource(R.drawable.home);
                break;

            case SEARCH:
                m_searchButton.setImageResource(R.drawable.search);
                break;

            case CART:
                m_cartButton.setImageResource(R.drawable.shopping_cart);
                break;

            case BOOKSHELF:
                m_bookshelfButton.setImageResource(R.drawable.book);
                break;
        }

        // Remove child layout
        m_scrollLayouts.removeAllViews();
    }

} // class NavigationalActivity
