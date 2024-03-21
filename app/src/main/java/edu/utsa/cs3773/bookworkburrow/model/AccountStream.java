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

    public AccountStream(File _root, String _name) throws IOException {

        Path accountDirectory = Files.createDirectories(Paths.get(_root.getPath(), "account"));
        Path accountPath = Paths.get(accountDirectory.toString(), (_name + ".bwa"));

        m_file = new File(accountPath.toString());
        m_file.createNewFile();
    }

    public Account read() throws IOException {

        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(m_file))) {

            if (inputStream.available() <= 0) return null;

            String UID = inputStream.readUTF();
            String email = inputStream.readUTF();
            String firstName = inputStream.readUTF();
            String lastName = inputStream.readUTF();

            Account account = new Account(UID);
            account.setEmail(email);
            account.setFirstName(firstName);
            account.setLastName(lastName);

            return account;
        }
    }

    public void write(Account _account) throws IOException {

        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(m_file))) {

            outputStream.writeUTF(_account.getUID());
            outputStream.writeUTF(_account.getEmail());
            outputStream.writeUTF(_account.getFirstName());
            outputStream.writeUTF(_account.getLastName());
        }
    }

} // class AccountStream
