package edu.utsa.cs3773.bookworkburrow.model;

import android.widget.EditText;

import java.io.IOException;

public class Input {

    private Input() {
    }

    public static String checkEmail(EditText _emailText) throws IOException {

        String email = _emailText.getText().toString();
        if (email.isEmpty()) throw new IOException("A required email field is empty");

        int mailServerIndex = email.lastIndexOf('@');
        if (mailServerIndex == -1) throw new IOException("Malformed email: invalid mail server");
        String mailServer = email.substring(mailServerIndex);

        int domainIndex = mailServer.lastIndexOf('.');
        if ((domainIndex == -1) || (domainIndex == (mailServer.length() - 1))) throw new IOException("Malformed email: invalid domain");

        return email;
    }

    public static String checkPassword(EditText _passwordText) throws IOException {

        String password = _passwordText.getText().toString();
        if (password.isEmpty()) throw new IOException("A required password field is empty");
        if (password.length() < 8) throw new IOException("Password must be greater than 8 characters long");

        // TODO (Juan): Probably more checks here

        return password;
    }

    public static String checkName(EditText _nameText) throws IOException {

        String name = _nameText.getText().toString();
        if (name.isEmpty()) throw new IOException("A required name field is empty");

        for (char c : name.toCharArray()) {

            if (!Character.isAlphabetic(c)) throw new IOException("Invalid name: Invalid character '" + c + "'");

        } return name;
    }

} // class InputChecker
