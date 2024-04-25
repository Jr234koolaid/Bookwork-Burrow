package edu.utsa.cs3773.bookworkburrow;

import android.util.Log;
import android.util.StateSet;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import edu.utsa.cs3773.bookworkburrow.model.Book;
import edu.utsa.cs3773.bookworkburrow.model.Order;

public class FirebaseOrderUtil {

    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static String userId = FirebaseUserUtil.user.getUid();

    /**
     * Adds an order to the database
     * @param userID user who created the order
     * @param order order object
     * @return ID of the order
     */
    public static CompletableFuture<String> addOrder(String userID, Order order){
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        //create new order in order collection
        HashMap<String, Object> orderMap = new HashMap<>();
        orderMap.put("user-id", userID);
        orderMap.put("date", FieldValue.serverTimestamp());
        ArrayList<String> bookIDs = new ArrayList<>();
        for(Book book : order.getCartList()){
            bookIDs.add(book.getId());
        }
        orderMap.put("books", bookIDs);
        orderMap.put("price", order.getTotalWithTax());

        db.collection("orders").add(orderMap)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentReference doc = task.getResult();
                        addOrderToUser(userID, doc.getId()).thenAccept(Boolean ->{
                            if(!Boolean) Log.d("Error: Order in Firestore", "couldn't add to user");
                            completableFuture.complete(doc.getId());
                        });
                    }
                    else completableFuture.completeExceptionally(new Throwable("Could not retrieve books"));

                });

        return completableFuture;
    }

    /**
     * Adds the newly created order document to the user document
     * @param userID
     * @param orderID order id
     * @return boolean whether successful or not
     */
    private static CompletableFuture<Boolean> addOrderToUser(String userID, String orderID){
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        db.collection("users").document(userID).update("orders", FieldValue.arrayUnion(orderID))
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) completableFuture.complete(true);
                    else completableFuture.complete(false);
                });
        return completableFuture;
    }

    /**
     * Gets the user's order history
     * @param userID user id
     * @return Arraylist of order ids
     */
    public static CompletableFuture<ArrayList<String>> getOrderHistory(String userID){
        CompletableFuture<ArrayList<String>> completableFuture = new CompletableFuture<>();
        db.collection("users").document(userID).get()
                .addOnCompleteListener(task -> {
                   if(task.isSuccessful()) {
                       DocumentSnapshot doc = task.getResult();
                       completableFuture.complete((ArrayList<String>) doc.get("orders"));
                   }
                   else completableFuture.completeExceptionally(new Throwable(task.getException()));
                });

        return completableFuture;
    }

    /**
     *
     * @param orderID
     * @return
     */
    public static CompletableFuture<Order> getOrderByID(String orderID){
        CompletableFuture<Order> completableFuture = new CompletableFuture<>();
        db.collection("orders").document(orderID).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot doc = task.getResult();
                        Order order = new Order();
                        ArrayList<String> bookIDs = (ArrayList<String>) doc.get("books");
                        order.setBookIDs(bookIDs);
                        order.setOrderID(orderID);
                        order.setDate((Timestamp) doc.get("date"));
                        completableFuture.complete(order);
                    }
                    else completableFuture.completeExceptionally(new Throwable(task.getException()));
                });
        return completableFuture;
    }

    /**
     * Adds a book ID to a user's cart array in Firestore.
     * @param bookId The book ID to add to the user's cart array.
     */
    public static CompletableFuture<Boolean> addBookToCart(String bookId) {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        db.collection("users").document(userId)
                .update("cart", FieldValue.arrayUnion(bookId))
                .addOnSuccessListener(aVoid -> {
                    Log.d("UpdateSuccess", "Book added to user's cart array");
                    completableFuture.complete(true);
                })
                .addOnFailureListener(e -> {
                    db.collection("users").document(userId)
                            .set(Collections.singletonMap("cart", Collections.singletonList(bookId)), SetOptions.merge())
                            .addOnSuccessListener(aVoid ->
                                    Log.d("SetSuccess", "Cart field created and book added"))
                            .addOnFailureListener(eSet ->{
                                        Log.w("SetFailure", "Error creating cart field and adding book", eSet);
                                        completableFuture.complete(false);
                                    });
                });
        return completableFuture;
    }

    /**
     * Removes a book ID from a user's cart array in Firestore.
     * @param bookId The book ID to remove from the user's cart array.
     */
    public static CompletableFuture<Boolean> removeBookFromCart(String bookId) {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        db.collection("users").document(userId)
                .update("cart", FieldValue.arrayRemove(bookId))
                .addOnSuccessListener(aVoid -> {
                    Log.d("UpdateSuccess", "Book removed from user's cart array");
                    completableFuture.complete(true);
                })
                .addOnFailureListener(e -> {
                    Log.w("UpdateFailure", "Error removing book from cart", e);
                    completableFuture.complete(false);
                });
        return completableFuture;
    }

    public static CompletableFuture<Boolean> clearCart(){
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        db.collection("users").document(userId)
                .update("cart", new ArrayList<>())
                .addOnSuccessListener(task ->{
                    Log.d("UpdateSuccess", "cart cleared");
                    completableFuture.complete(true);
                })
                .addOnFailureListener(e -> {
                    Log.w("UpdateFailure", "cart not cleared", e);
                    completableFuture.complete(false);
                });
        return completableFuture;

    }


}
