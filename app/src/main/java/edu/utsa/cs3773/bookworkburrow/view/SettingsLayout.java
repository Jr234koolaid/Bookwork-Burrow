package edu.utsa.cs3773.bookworkburrow.view;

import android.content.Intent;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;

import edu.utsa.cs3773.bookworkburrow.ChangePasswordActivity;
import edu.utsa.cs3773.bookworkburrow.FirebaseUserUtil;
import edu.utsa.cs3773.bookworkburrow.R;

public class SettingsLayout extends NavigationalLayout {

    public SettingsLayout(NavigationalActivity _context, ViewGroup _parent) {
        super(_context, _parent, R.layout.layout_settings);
    }

    @Override
    protected void onDisplay() {

        AppCompatButton orderHistoryButton = mLayoutView.findViewById(R.id.settings_button_order_history);
        orderHistoryButton.setOnClickListener(view -> mContext.startActivity(new Intent(mContext, OrderHistory.class)));

        AppCompatButton changePasswordButton = mLayoutView.findViewById(R.id.settings_button_change_password);
        changePasswordButton.setOnClickListener(view -> mContext.startActivity(new Intent(mContext, ChangePasswordActivity.class)));

        AppCompatButton logoutButton = mLayoutView.findViewById(R.id.settings_button_login);
        logoutButton.setOnClickListener(view -> this.logout());
    }

    private void logout() {

        // Log out via firebase
        FirebaseUserUtil.logOut();

        // Go back to login screen
        mContext.startActivity(new Intent(mContext, LoginActivity.class));
        mContext.finish();
    }

} // class SettingsLayout
