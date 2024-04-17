package edu.utsa.cs3773.bookworkburrow.model;

import android.util.Log;

import java.util.ArrayList;

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

    public Account(String UID) {
        booksOwned = new ArrayList<>();
        orderHistory = new ArrayList<>();
        cart = new Order();
        this.UID = UID;
    }

    public Account(String UID, String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        booksOwned = new ArrayList<>();
        orderHistory = new ArrayList<>();
        cart = new Order();
        this.UID = UID;
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
        FirebaseUserUtil.setUserReadingGoal(UID, readingGoal).thenAccept(Boolean ->{
            if(!Boolean) Log.d("Update reading goal", "Failed");
            else Log.d("Update reading goal", "Success");
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

    /**
     * Adds the purchased cart items to the booksOwned list
     * Adds current cart to order history and clears cart
     * Should be called after purchase is complete
     */
    public String completeCheckout(){
        for(String bookID : cart.getBookIDs()){
            this.addBookToOwned(bookID);
        }
        FirebaseOrderUtil.addOrder(UID, cart).thenAccept(String ->{
            if(String.length() > 0){
                cartID = String;
                orderHistory.add(cartID);
                cart = new Order();
            }
            else cartID = "Error processing";
        });

        return cartID;
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

}
