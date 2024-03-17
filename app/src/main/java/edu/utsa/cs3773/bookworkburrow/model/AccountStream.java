package edu.utsa.cs3773.bookworkburrow.model;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import edu.utsa.cs3773.bookworkburrow.view.model.Account;

public class AccountStream implements IOStream<Account> {

    private File m_file;

    public AccountStream(File _root, String _username) throws IOException {

        m_file = new File(_root.toString() + "/account/" + _username.hashCode());
        m_file.createNewFile();
    }

    @Override
    public Account read() throws IOException {

        Scanner scanner = new Scanner(m_file);

        scanner.close();

        Account account = new Account("Something", "Something", "Something");

        return account;
    }

    @Override
    public void write(Account _account) throws IOException {

        Scanner scanner = new Scanner(m_file);

        scanner.close();
    }

} // class AccountStream
