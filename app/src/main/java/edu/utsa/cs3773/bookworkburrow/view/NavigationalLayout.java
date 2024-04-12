package edu.utsa.cs3773.bookworkburrow.view;

import android.view.View;
import android.view.ViewGroup;

public abstract class NavigationalLayout {

    protected final NavigationalActivity    mContext;
    protected final ViewGroup               mParent;
    protected final int                     mResourceID;

    protected View                          mLayoutRoot;

    public NavigationalLayout(NavigationalActivity _context, ViewGroup _parent, int _resource) {

        mContext = _context;
        mParent = _parent;
        mResourceID = _resource;

        mLayoutRoot = null;
    }

    public void onShow() {

        if (mLayoutRoot == null) {
            mLayoutRoot = ((ViewGroup)View.inflate(mContext, mResourceID, mParent)).getChildAt(0);
        }
    }

} // abstract class NavigationLayout
