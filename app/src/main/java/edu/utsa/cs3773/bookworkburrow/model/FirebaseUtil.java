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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import edu.utsa.cs3773.bookworkburrow.view.model.Account;

public class FirebaseUtil {

    /**
     * Creates a new user in Firebase Auth given an email and password
     * @param email
     * @param password
     * @param context
     */
    public static CompletableFuture<Account> createUser(String email, String password, AppCompatActivity context){
        CompletableFuture<Account> completableFuture = new CompletableFuture<>();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(context, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Ensure the user is not null
                        if (user != null) {
                            // Create account with UID
                            String uid = user.getUid();
                            Log.d(TAG, "createUserWithEmail:success");
                            Log.d("User ID", uid);
                            // Successfully created the account, complete the future with the result
                            completableFuture.complete(new Account(uid));
                        } else {
                            // User is null, complete exceptionally
                            completableFuture.completeExceptionally(new Exception("FirebaseUser is null"));
                        }
                    } else {
                        // If sign in fails, display a message and complete exceptionally
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(context, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        completableFuture.completeExceptionally(task.getException());
                    }
                });
        return completableFuture;
    }

    /**
     * Deletes the account given the account object
     * @param account
     */
    public static void deleteUser(Account account){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(account.getUid()) // Use the actual document ID
                .delete()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error deleting document", e));
    }

    public static void updateUser(Account account){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(account.getUid())
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
