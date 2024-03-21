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

public class AccountDatabase {

    private static AccountDatabase Instance;

    public static AccountDatabase getInstance() {

        if (Instance == null) {
            Instance = new AccountDatabase();

        } return Instance;
    }

    private AccountDatabase() { }

    public String add(String _email, String _password) throws FirebaseAuthWeakPasswordException,
                                                              FirebaseAuthInvalidCredentialsException,
                                                              FirebaseAuthUserCollisionException {

        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        Task<AuthResult> createUserTask = fAuth.createUserWithEmailAndPassword(_email, _password);
        if (!createUserTask.isSuccessful()) return null;

        FirebaseUser fUser = fAuth.getCurrentUser();
        return (fUser == null) ? null : fUser.getUid();
    }

    public void remove(String _email, AppCompatActivity _activity) {
    }

    public String authenticate(String _email, String _password) throws FirebaseAuthInvalidUserException,
                                                                       FirebaseAuthInvalidCredentialsException {

        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        Task<AuthResult> signInTask = fAuth.signInWithEmailAndPassword(_email, _password);
        if (!signInTask.isSuccessful()) return null;

        FirebaseUser fUser = fAuth.getCurrentUser();
        return (fUser == null) ? null : fUser.getUid();
    }

} // class AccountDatabase

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