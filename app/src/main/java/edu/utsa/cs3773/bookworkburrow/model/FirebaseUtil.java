package edu.utsa.cs3773.bookworkburrow.model;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.concurrent.atomic.AtomicReference;

import edu.utsa.cs3773.bookworkburrow.model.Account;

public class FirebaseUtil {

    /**
     * Creates a new user in Firebase Auth given an email and password
     * @param email
     * @param password
     * @param context
     */
    public static Account createUser(String email, String password, AppCompatActivity context){
        AtomicReference<String> uid = new AtomicReference<>("");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(context, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        FirebaseUser user = mAuth.getCurrentUser();
                        //create account with id
                        assert user != null;
                        uid.set(user.getUid());
                        Log.d(TAG, "createUserWithEmail:success");
                        Log.d("User ID", user.getUid());
                    } else {
                        // If sign in fails display a message
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(context, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        return new Account(uid.toString());
    }

    /**
     * Deletes the account given the account object
     * @param account
     */
    public static void deleteUser(Account account){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(account.getUID()) // Use the actual document ID
                .delete()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error deleting document", e));
    }

    public static void updateUser(Account account){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(account.getUID())
                .update("last", "Lovelace-Byron")
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully updated!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error updating document", e));
    }

    public static void readUser(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereLessThan("born", 1900)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });
    }

}
