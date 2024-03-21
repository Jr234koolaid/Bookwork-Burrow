package edu.utsa.cs3773.bookworkburrow.model;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.IOException;

public class AccountDatabase {

    private static AccountDatabase Instance;

    public static AccountDatabase getInstance() {

        if (Instance == null) {
            Instance = new AccountDatabase();

        } return Instance;
    }

    private AccountDatabase() { }

    public String add(String _email, String _password, String _firstName, String _lastName, File _dataDirectory) throws FirebaseAuthWeakPasswordException,
                                                                                                                        FirebaseAuthInvalidCredentialsException,
                                                                                                                        FirebaseAuthUserCollisionException,
                                                                                                                        IOException {

        // (Juan) Get user from firebase
        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        Task<AuthResult> createUserTask = fAuth.createUserWithEmailAndPassword(_email, _password);
        if (!createUserTask.isSuccessful()) return null;

        FirebaseUser fUser = fAuth.getCurrentUser();
        if (fUser == null) return null;

        String UID = fUser.getUid();
        String emailHash = String.valueOf(_email.hashCode());

        try {

            // Writes the account to a file
            Account account = new Account(UID);
            account.setEmail(_email);
            account.setFirstName(_firstName);
            account.setLastName(_lastName);

            AccountStream accountStream = new AccountStream(_dataDirectory, emailHash);
            accountStream.write(account);

            return UID;

        } catch (IOException e) {
            throw new IOException(e);
        }

        // (Juan) This writes the account to a local database file
//        Path accountsPath = Paths.get(_dataDirectory.getPath(), "accounts.bwd");
//
//        File fDatabase = new File(accountsPath.toString());
//        fDatabase.createNewFile();
//
//        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(fDatabase))) {
//
//            while (inputStream.available() > 0) {
//
//                String email = inputStream.readUTF();
//                String usernameHash = inputStream.readUTF();
//
//                if (_email.equals(email)) return null;
//            }
//        }
//
//        String usernameHash = String.valueOf(_username.hashCode());
//
//        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(fDatabase))) {
//            outputStream.writeUTF(_email);
//            outputStream.writeUTF(usernameHash);
//
//            // Writes the account to a file
//            Account account = new Account(UID);
//            account.setEmail(_email);
//            account.setUsername(_username);
//            account.setPassword(_password);
//
//            AccountStream accountStream = new AccountStream(_dataDirectory, usernameHash);
//            accountStream.write(account);
//
//            return UID;
//        }
    }

    public void remove(String _email, AppCompatActivity _activity) {


    }

    public String authenticate(String _email, String _password) throws  FirebaseAuthInvalidUserException,
                                                                        FirebaseAuthInvalidCredentialsException {

        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        Task<AuthResult> signInTask = fAuth.signInWithEmailAndPassword(_email, _password);
        if (!signInTask.isSuccessful()) return null;

        FirebaseUser fUser = fAuth.getCurrentUser();
        return (fUser == null) ? null : fUser.getUid();

        // (Juan) This stuff could be removed when we switch to an actual database
//        Path accountsPath = Paths.get(_dataDirectory.getPath(), "accounts.bwd");
//
//        File fDatabase = new File(accountsPath.toString());
//        if (fDatabase.createNewFile()) return false;
//
//        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(fDatabase))) {
//
//            while (inputStream.available() > 0) {
//
//                String email = inputStream.readUTF();
//                String usernameHash = inputStream.readUTF();
//
//                if (_email.equals(email)) {
//
//                    AccountStream accountStream = new AccountStream(_dataDirectory, usernameHash);
//                    Account account = accountStream.read();
//
//                    return _password.equals(account.getPassword());
//                }
//
//            } return false;
//        }
    }

} // class AccountDatabase
