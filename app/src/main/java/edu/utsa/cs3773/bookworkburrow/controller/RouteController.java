package edu.utsa.cs3773.bookworkburrow.controller;

import android.content.Context;

import edu.utsa.cs3773.bookworkburrow.FirebaseUtil;

public class RouteController {
    public boolean isLoggedIn() {
        return FirebaseUtil.isLoggedIn();
    }
}
