package edu.utsa.cs3773.bookworkburrow.model;

import androidx.appcompat.app.AppCompatActivity;

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

    private final Account   m_account;
    private final File      m_file;

    public AccountStream(Account _account, AppCompatActivity _context) throws IOException {

        Path accountDirectory = Files.createDirectories(Paths.get(_context.getDataDir().getPath(), "account"));
        Path accountPath = Paths.get(accountDirectory.toString(), (_account.getUID() + ".bwa"));

        File accountFile = new File(accountPath.toString());
        accountFile.createNewFile();

        m_account = _account;
        m_file = accountFile;
    }

    public void read() throws IOException {

        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(m_file))) {

            String email = inputStream.readUTF();
            String firstName = inputStream.readUTF();
            String lastName = inputStream.readUTF();

            m_account.setEmail(email);
            m_account.setFirstName(firstName);
            m_account.setLastName(lastName);
        }
    }

    public void write() throws IOException {

        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(m_file))) {

            outputStream.writeUTF(m_account.getEmail());
            outputStream.writeUTF(m_account.getFirstName());
            outputStream.writeUTF(m_account.getLastName());
        }
    }

} // class AccountStream
