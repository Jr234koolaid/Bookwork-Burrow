package edu.utsa.cs3773.bookworkburrow.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class NavigationalLayout {

    protected final NavigationalActivity    mContext;
    protected final ViewGroup               mParent;
    protected final int                     mResourceID;
    protected final LayoutInflater          mInflater;

    protected View                          mLayoutView;

    public NavigationalLayout(NavigationalActivity _context, ViewGroup _parent, int _resourceID) {

        mContext = _context;
        mParent = _parent;
        mResourceID = _resourceID;
        mInflater = mContext.getLayoutInflater();

        mLayoutView = null;
    }

    public final void onShow() {

        // Get view
        mLayoutView = mInflater.inflate(mResourceID, mParent, false);

        // Display inherited view
        this.onDisplay();

        // Add view to parent
        mParent.addView(mLayoutView);
    }

    protected abstract void onDisplay();

} // abstract class NavigationLayout
