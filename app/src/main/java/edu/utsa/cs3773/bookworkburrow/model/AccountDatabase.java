package edu.utsa.cs3773.bookworkburrow.model;

import androidx.appcompat.app.AppCompatActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class AccountDatabase {

    private static AccountDatabase Instance;

    public static AccountDatabase getInstance() {

        if (Instance == null) {
            Instance = new AccountDatabase();

        } return Instance;
    }

    private String m_root;

    private AccountDatabase() {
    }

    public void setContext(AppCompatActivity _context) {
        m_root = _context.getDataDir().toPath().toString();
    }

    public String addAccount(String _email, String _password) throws IOException {

        if (m_root == null) throw new IOException();

        // (Juan) This writes the account to a local database file
        int emailHash = _email.hashCode();
        int passwordHash = _password.hashCode();

        Path accountsPath = Paths.get(m_root, "accounts.bwd");

        File fDatabase = new File(accountsPath.toString());
        fDatabase.createNewFile();

        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(fDatabase))) {

            while (inputStream.available() > 0) {

                String email = inputStream.readUTF();
                String token = inputStream.readUTF();
                //String check = inputStream.readUTF();

                if (emailHash == Integer.parseInt(email)) throw new IOException("Email already in use");
            }

        } return this.update(fDatabase, emailHash, passwordHash);
    }

    public void updateAccount(String _email, String _password) throws IOException {

        // (Juan) This reads from the local database file
        int emailHash = _email.hashCode();
        int passwordHash = _password.hashCode();

        Path accountsPath = Paths.get(m_root, "accounts.bwd");

        File fDatabase = new File(accountsPath.toString());
        if (!fDatabase.createNewFile()) {

            try (DataInputStream inputStream = new DataInputStream(new FileInputStream(fDatabase))) {

                while (inputStream.available() > 0) {

                    String email = inputStream.readUTF();
                    String token = inputStream.readUTF();
                    //String check = inputStream.readUTF();

                    if (emailHash == Integer.parseInt(email)) {

                        this.update(fDatabase, emailHash, passwordHash);
                        return;
                    }
                }
            }

        } throw new IOException("Invalid email");
    }

    public boolean findAccount(String _email) throws IOException {

        int emailHash = _email.hashCode();

        Path accountsPath = Paths.get(m_root, "accounts.bwd");

        File fDatabase = new File(accountsPath.toString());
        if (!fDatabase.createNewFile()) {

            try (DataInputStream inputStream = new DataInputStream(new FileInputStream(fDatabase))) {

                while (inputStream.available() > 0) {

                    String email = inputStream.readUTF();
                    String token = inputStream.readUTF();
                    //String check = inputStream.readUTF();

                    if (emailHash == Integer.parseInt(email)) return true;
                }
            }

        } return false;
    }

    public void removeAccount(String _email) throws IOException {
    }

    public String authenticate(String _email, String _password) throws IOException {

        if (m_root == null) throw new IOException();

        // (Juan) This reads from the local database file
        int emailHash = _email.hashCode();
        int passwordHash = _password.hashCode();

        Path accountsPath = Paths.get(m_root, "accounts.bwd");

        File fDatabase = new File(accountsPath.toString());
        if (!fDatabase.createNewFile()) {

            try (DataInputStream inputStream = new DataInputStream(new FileInputStream(fDatabase))) {

                while (inputStream.available() > 0) {

                    // TODO (Juan): REMOVE
                    String week = LocalDate.now().getDayOfWeek().toString();

                    String email = inputStream.readUTF();
                    String token = inputStream.readUTF();
                    //String check = inputStream.readUTF();

                    if (emailHash == Integer.parseInt(email)) {

                        long rightValue = calculateRightHalf(emailHash);
                        long leftValue = calculateLeftHalf(passwordHash);

                        if ((rightValue + leftValue) == Long.parseLong(token)) return email;
                        break;
                    }
                }
            }

        } throw new IOException("Invalid email or password");
    }

    private long calculateRightHalf(int _emailHash) {
        return ((long) (_emailHash | 0xC0000000) << 32);
    }

    private long calculateLeftHalf(int _passwordHash) {
        return ((long) _passwordHash ^ (long) (_passwordHash & 0xC0000000));
    }

    private String update(File _database, int _emailHash, int _passwordHash) throws IOException {

        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(_database))) {

            long rightValue = this.calculateRightHalf(_emailHash);
            long leftValue = this.calculateLeftHalf(_passwordHash);

            String email = String.valueOf(_emailHash);
            String token = String.valueOf(rightValue + leftValue);

            outputStream.writeUTF(email);
            outputStream.writeUTF(token);

            return email;
        }
    }

} // class AccountDatabase

//        FirebaseAuth fAuth = FirebaseAuth.getInstance();
//
//        Task<AuthResult> createUserTask = fAuth.createUserWithEmailAndPassword(_email, _password);
//        if (!createUserTask.isSuccessful()) return null;
//
//        FirebaseUser fUser = fAuth.getCurrentUser();
//        return (fUser == null) ? null : fUser.getUid();

//        FirebaseAuth fAuth = FirebaseAuth.getInstance();
//
//        Task<AuthResult> signInTaskResult = fAuth.signInWithEmailAndPassword(_email, _password);
//        FirebaseUser fUser = fAuth.getCurrentUser();
//        if (!signInTaskResult.isSuccessful() || (fUser == null)) throw new IOException("Unable to sign in");
//
//        return fUser.getUid();