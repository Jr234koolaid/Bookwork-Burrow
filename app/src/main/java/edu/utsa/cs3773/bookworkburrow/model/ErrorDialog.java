package edu.utsa.cs3773.bookworkburrow.model;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ErrorDialog {

    private static ErrorDialog Instance;

    public static ErrorDialog getInstance() {

        if (Instance == null) {
            Instance = new ErrorDialog();

        } return Instance;
    }

    private final String        mMessage;

    private AlertDialog.Builder mAlertDialogBuilder;

    private ErrorDialog() {

        mMessage = "Please make sure your information is correct and try again!";

        mAlertDialogBuilder = null;
    }

    public void setContext(AppCompatActivity _context) {
        mAlertDialogBuilder = new AlertDialog.Builder(_context).setPositiveButton("OK", null);
    }

    public void display(String _title) {

        if (mAlertDialogBuilder == null) return;

        if (_title == null) {
            mAlertDialogBuilder.setTitle("Unexpected Error...").setMessage(mMessage).show();

        } else {
            mAlertDialogBuilder.setTitle(_title).setMessage(mMessage).show();
        }
    }

} // class ErrorDialog
