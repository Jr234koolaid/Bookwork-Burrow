package edu.utsa.cs3773.bookworkburrow.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AccountStream {

    private final File m_file;

    public AccountStream(File _root, String _usernameHash) throws IOException {

        Path accountPath = Files.createDirectories(Paths.get(_root.getPath(), "account"));
        Path path = Paths.get(accountPath.toString(), (_usernameHash + ".bwa"));

        m_file = new File(path.toString());
        m_file.createNewFile();
    }

    public Account read() throws IOException {

        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(m_file))) {

            String username = inputStream.readUTF();
            String password = inputStream.readUTF();
            String name = inputStream.readUTF();

            Account account = new Account(username, password, name);

            return account;
        }
    }

    public void write(Account _account) throws IOException {

        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(m_file))) {

            outputStream.writeUTF(_account.getUsername());
            outputStream.writeUTF(_account.getPassword());
            outputStream.writeUTF(_account.getName());
        }
    }

} // class AccountStream
