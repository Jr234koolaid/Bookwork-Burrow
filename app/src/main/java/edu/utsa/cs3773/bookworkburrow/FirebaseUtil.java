package edu.utsa.cs3773.bookworkburrow;

import static android.content.ContentValues.TAG;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import edu.utsa.cs3773.bookworkburrow.model.Account;

public class FirebaseUtil {

    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Checks if a user is already logged in using Firebase Auth
     * @return boolean
     */
    public static boolean isLoggedIn(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null;
    }

    public static Account getCurrUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String id = user.getUid();
            Account account = new Account(id);
            return account;
        }
        return new Account("user not found");
    }

    /**
     * Attempts to log in a user with the provided username and password.
     * @param email The user's email.
     * @param password The user's password.
     * @param context The AppCompatActivity context.
     * @return CompletableFuture<Account>
     */
    public static CompletableFuture<Account> loginWithUsernamePassword(String email, String password, AppCompatActivity context){
        CompletableFuture<Account> completableFuture = new CompletableFuture<>();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(context, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Ensure the user is not null
                        if (user != null) {
                            String uid = user.getUid();
                            // Successfully signed in, complete the future with the result
                            completableFuture.complete(new Account(uid));
                            //TODO: load all of their info
                        } else {
                            // User is null, complete exceptionally
                            completableFuture.completeExceptionally(new Exception("FirebaseUser is null"));
                        }
                    } else {
                        // If sign in fails, display a message to the user and complete exceptionally
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        completableFuture.completeExceptionally(task.getException());
                    }
                });

        return completableFuture;
    }

    /**
     * Creates a new user in Firebase Auth given an email and password
     * @param email
     * @param password
     * @param context
     */
    public static CompletableFuture<Account> createUser(String firstname, String lastname, String email, String password, AppCompatActivity context){
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
                            //add to firestore
                            HashMap<String, Object> userMap = new HashMap<>();
                            userMap.put("email", user.getEmail());
                            userMap.put("first-name", firstname);
                            userMap.put("last-name", lastname);
                            db.collection("users").document(user.getUid()).set(userMap);
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
     * Updates a string value on the database for user objects
     * @param documentID userID
     * @param field which attribute to change
     * @param value the updated value
     */
    public static void updateUserStringField(String documentID, String field, double value){
        db.collection("users").document(documentID).update(field, value);
    }

    /**
     * Updates the reading goal on the database for user objects
     * @param documentID userID
     * @param value the updated value
     */
    public static void updateUserReadingGoal(String documentID, int value){
        db.collection("users").document(documentID).update("reading-goal", value);
    }

    /**
     * Updates a list (books owned, favorites, orders) of user with a new value
     * @param userID userID of user
     * @param list list name (books-owned, books-favorited, orders)
     * @param valueID ID of book or order to add to collected
     * @return boolean if successfully added
     */
    public static CompletableFuture<Boolean> addToUserList(String userID, String list, String valueID){
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        db.collection("users").document(userID).update(list, valueID)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        completableFuture.complete(true);
                    }
                    else{
                        completableFuture.completeExceptionally(new Throwable("Could not retrieve books"));
                    }
                });

        return completableFuture;
    }



    //TODO: get all owned books, all favorites, all orders

}
