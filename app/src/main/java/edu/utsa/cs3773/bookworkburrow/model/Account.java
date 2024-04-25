package edu.utsa.cs3773.bookworkburrow.model;

import android.util.Log;

import org.checkerframework.checker.guieffect.qual.UI;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import edu.utsa.cs3773.bookworkburrow.FirebaseBookUtils;
import edu.utsa.cs3773.bookworkburrow.FirebaseOrderUtil;
import edu.utsa.cs3773.bookworkburrow.FirebaseUserUtil;

public class Account {

    private String UID;
    private String email;
    private String firstName;
    private String lastName;
    ArrayList<String> booksOwned;
    ArrayList<String> favorites;
    ArrayList<String> orderHistory;
    Order cart;
    String cartID;
    int readingGoal;
    int booksRead;

    public Account(String UID) {
        booksOwned = new ArrayList<>();
        orderHistory = new ArrayList<>();
        cart = new Order();
        this.UID = UID;
    }


    public Account(String UID, String firstName, String lastName, String email) {
        this.UID = UID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        booksOwned = new ArrayList<>();
        orderHistory = new ArrayList<>();
        cart = new Order();
    }

    public String getUID() {return UID;}

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String _email) {
        email = _email;
        FirebaseUserUtil.updateUserStringField(getUID(), "email", _email)
                .thenAccept(Boolean ->{
                    if(!Boolean) Log.d("Update email", "Failed");
                    else Log.d("Update email", "Success");
                });
    }

    public void setFirstName(String _firstName) {
        firstName = _firstName;
        FirebaseUserUtil.updateUserStringField(getUID(), "first-name", _firstName)
                .thenAccept(Boolean ->{
                    if(!Boolean) Log.d("Update first name", "Failed");
                    else Log.d("Update first name", "Success");
                });
    }

    public void setLastName(String _lastName) {
        lastName = _lastName;
        FirebaseUserUtil.updateUserStringField(getUID(), "last-name", lastName)
                .thenAccept(Boolean ->{
                    if(!Boolean) Log.d("Update last name", "Failed");
                    else Log.d("Update last name", "Success");
                });
    }

    public int getReadingGoal() {
        return readingGoal;
    }

    public void setReadingGoal(int readingGoal) {
        this.readingGoal = readingGoal;
        FirebaseUserUtil.setUserIntField(UID, "reading-goal", readingGoal).thenAccept(Boolean ->{
            if(!Boolean) Log.d("M - Update reading goal", "Failed");
            else Log.d("M - Update reading goal", "Success, new reading goal: " + readingGoal);
        });
    }

    public ArrayList<String> getBooksOwned() {
        return booksOwned;
    }

    public void setBooksOwned(ArrayList<String> booksOwned) {
        this.booksOwned = booksOwned;
    }

    public ArrayList<String> getFavorites() {
        return favorites;
    }

    //todo: update DB with favorites
    public CompletableFuture<Boolean> addToFavorites(String bookID){
        cart.getBookIDs().add(bookID);
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        FirebaseUserUtil.addToUserList(UID,"books-favorited", bookID).thenAccept(Boolean ->{
            if(Boolean) {
                Log.d("Book added to favorites DB", "Success");
                completableFuture.complete(true);
            }
            else Log.d("Book added to favorites DB", "Failed");
        });
        return completableFuture;
    }

    public CompletableFuture<Boolean> removeFromFavorites(String bookID){
        cart.getBookIDs().add(bookID);
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        FirebaseUserUtil.removeFromUserList("books-favorited", bookID).thenAccept(Boolean ->{
            if(Boolean) {
                Log.d("Book removed from favorites DB", "Success");
                completableFuture.complete(true);
            }
            else Log.d("Book removed favorites DB", "Failed");
        });
        return completableFuture;
    }

    public void setFavorites(ArrayList<String> favorites) {
        this.favorites = favorites;
    }

    public ArrayList<String> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(ArrayList<String> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public Order getCart() {
        return cart;
    }

    public void setCart(Order cart) {
        this.cart = cart;
    }

    public int getBooksRead() {
        return booksRead;
    }

    public void setBooksRead(int booksRead) {
        this.booksRead = booksRead;
        FirebaseUserUtil.setUserIntField(UID, "books-read", booksRead).thenAccept(Boolean ->{
            if(!Boolean) Log.d("Model - Update books read", "Failed");
            else Log.d("Model - Update books read", "Success, new books read: " + booksRead);
        });
    }

    /**
     * Adds the purchased cart items to the booksOwned list
     * Adds current cart to order history and clears cart
     * Should be called after purchase is complete
     */
    public CompletableFuture<String> completeCheckout(){
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        for(Book book : cart.getCartList()){
            this.addBookToOwned(book.getId());
        }
        FirebaseOrderUtil.addOrder(UID, cart).thenAccept(String ->{
            if(String.length() > 0){
                cartID = String;
                orderHistory.add(cartID);
                cart = new Order();
                completableFuture.complete(String);
            }
            else completableFuture.completeExceptionally(new Throwable(String));
        });

        return completableFuture;
    }

    public String toString(){
        String s = "UID: " + UID + "\n";
        s += cart.toString();
        return s;
    }

    /**
     * Adds a book to the user's favorites list
     * @param bookID ID of book to add
     */
    public void addBookToFavorites(String bookID){
        favorites.add(bookID);
        FirebaseUserUtil.addToUserList(UID, "books-favorited", bookID)
                .thenAccept(Boolean ->{
            if(Boolean) Log.d("Added to favorites", bookID + " added to favorites");
        });
    }

    private void addBookToOwned(String bookID){
        booksOwned.add(bookID);
        FirebaseUserUtil.addToUserList(UID, "books-owned", bookID)
                .thenAccept(Boolean ->{
                    if(Boolean) Log.d("Added to owned", bookID + " added to owned");
                });
    }

    public void addBookByIDToCart(String bookID){
        FirebaseUserUtil.addToUserList(UID, "cart", bookID)
                .thenAccept(Boolean ->{
                    if(Boolean) Log.d("Added bookID to cart in DB", bookID + " added to cart");
                    FirebaseBookUtils.getBookByID(bookID).thenAccept(Book ->{
                        cart.addBook(Book);
                    });
                });
    }
}
