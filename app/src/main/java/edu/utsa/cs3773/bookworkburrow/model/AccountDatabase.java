package edu.utsa.cs3773.bookworkburrow.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AccountDatabase {

    private static AccountDatabase Instance;

    public static AccountDatabase getInstance() {

        if (Instance == null) {
            Instance = new AccountDatabase();

        } return Instance;
    }

    private AccountDatabase() { }

    public boolean add(File _root, String _email, String _username, String _password) throws IOException {

        // (Juan) This stuff could be removed when we switch to an actual database
        Path path = Paths.get(_root.getPath(), "accounts.bwd");

        File file = new File(path.toString());
        file.createNewFile();

        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(file))) {

            while (inputStream.available() > 0) {

                String email = inputStream.readUTF();
                String usernameHash = inputStream.readUTF();

                if (_email.equals(email)) return false;
            }
        }

        String usernameHash = String.valueOf(_username.hashCode());

        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(file))) {
            outputStream.writeUTF(_email);
            outputStream.writeUTF(usernameHash);

            AccountStream accountStream = new AccountStream(_root, usernameHash);
            accountStream.write(new Account(_username, _password, _email));

            return true;
        }
    }

    public boolean authenticate(File _root, String _email, String _password) throws IOException {

        // (Juan) This stuff could be removed when we switch to an actual database
        Path path = Paths.get(_root.getPath(), "accounts.bwd");
        File file = new File(path.toString());

        if (file.createNewFile()) return false;

        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(file))) {

            while (inputStream.available() > 0) {

                String email = inputStream.readUTF();
                String usernameHash = inputStream.readUTF();

                if (_email.equals(email)) {

                    AccountStream accountStream = new AccountStream(_root, usernameHash);
                    Account account = accountStream.read();

                    return _password.equals(account.getPassword());
                }

            } return false;
        }
    }

} // class AccountDatabase
