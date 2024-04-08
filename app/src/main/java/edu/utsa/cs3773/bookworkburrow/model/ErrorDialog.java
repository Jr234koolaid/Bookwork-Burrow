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

    private final String        m_message;
    private AlertDialog.Builder m_alertDialogBuilder;

    private ErrorDialog() {
        m_message = "Please make sure your information is correct and try again!";
    }

    public void setContext(AppCompatActivity _context) {
        m_alertDialogBuilder = new AlertDialog.Builder(_context).setPositiveButton("OK", null);
    }

    public void display(String _message) {

        if (m_alertDialogBuilder == null) return;

        if (_message == null) {
            m_alertDialogBuilder.setTitle("Unexpected Error...").setMessage(m_message).show();

        } else {
            m_alertDialogBuilder.setTitle(_message).setMessage(m_message).show();
        }
    }

} // class ErrorDialog
