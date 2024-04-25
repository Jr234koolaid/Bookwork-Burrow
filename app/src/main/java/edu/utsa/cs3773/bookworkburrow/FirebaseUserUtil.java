package edu.utsa.cs3773.bookworkburrow;

import static android.content.ContentValues.TAG;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import edu.utsa.cs3773.bookworkburrow.model.Account;

public class FirebaseUserUtil {

    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    /**
     * Checks if a user is already logged in using Firebase Auth
     * @return boolean
     */
    public static boolean isLoggedIn(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return user != null;
    }

    public static CompletableFuture<Account> getCurrUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        CompletableFuture<Account> completableFuture = new CompletableFuture<>();
        db.collection("users").document(user.getUid()).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        Account account = new Account(user.getUid(), doc.getString("first-name"), doc.getString("last-name"), doc.getString("email"));
                        account.setFavorites((ArrayList<String>) doc.get("books-favorited"));
                        account.setBooksOwned((ArrayList<String>) doc.get("books-owned"));
                        account.setOrderHistory((ArrayList<String>) doc.get("orders"));
                        Double goal = doc.getDouble("reading-goal");
                        account.setReadingGoal(goal.intValue());
                        Double booksRead = doc.getDouble("books-read");
                        account.setBooksRead(booksRead.intValue());
                        //todo add cart from db
                        account.getCart().setBookIDs((ArrayList<String>)doc.get("cart")).thenAccept(Boolean ->{
                            completableFuture.complete(account);
                        });

                    } else
                        completableFuture.completeExceptionally(new Throwable(task.getException()));

                });
        return completableFuture;
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
                            Account account = new Account(uid);
                            // Successfully signed in, complete the future with the result
                            completableFuture.complete(new Account(uid));
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
                            userMap.put("books-owned", new ArrayList<>());
                            userMap.put("books-favorited", new ArrayList<>());
                            userMap.put("orders", new ArrayList<>());
                            userMap.put("reading-goal", 5);
                            userMap.put("books-read", 0);
                            userMap.put("cart", new ArrayList<>());
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
     * @param userID userID
     * @param field which attribute to change
     * @param value the updated value
     */
    public static CompletableFuture<Boolean> updateUserStringField(String userID, String field, String value){
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        db.collection("users").document(userID).update(field, value)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) completableFuture.complete(true);
                    else completableFuture.completeExceptionally(new Throwable(task.getException()));
                });
        return completableFuture;
    }

    /**
     * Updates the reading goal on the database for user objects
     * @param userID userID
     * @param value the updated value
     */
    public static CompletableFuture<Boolean> setUserIntField(String userID, String field, int value){
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        db.collection("users").document(userID).update(field, value)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) completableFuture.complete(true);
                    else completableFuture.completeExceptionally(new Throwable(task.getException()));
                });
        return completableFuture;
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
        db.collection("users").document(userID).update(list, FieldValue.arrayUnion(valueID))
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

    /**
     * Updates a list (books owned, favorites, orders) of user by removing the value
     * @param list list name (books-owned, books-favorited, orders)
     * @param valueID ID of book or order to add to collected
     * @return boolean if successfully added
     */
    public static CompletableFuture<Boolean> removeFromUserList(String list, String valueID){
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        db.collection("users").document(user.getUid()).update(list, FieldValue.arrayRemove(valueID))
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

    /**
     * Resets the user's password with a new string
     * @param newPassword new password to set
     * @return boolean whether it was successful or not
     */
    public static CompletableFuture<Boolean> resetPassword(String newPassword){
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        user.updatePassword(newPassword).addOnCompleteListener(task -> {
            if(task.isSuccessful()) completableFuture.complete(true);
            else completableFuture.completeExceptionally(new Throwable(task.getException()));
        });
        return completableFuture;
    }

    /**
     * Logs out the current user
     */
    public static void logOut(){
        FirebaseAuth.getInstance().signOut();
    }





}