package edu.utsa.cs3773.bookworkburrow.model;

import android.util.Log;

import java.util.ArrayList;

import edu.utsa.cs3773.bookworkburrow.FirebaseOrderUtil;
import edu.utsa.cs3773.bookworkburrow.FirebaseUtil;

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

    public String getUID() {
        return UID;
    }

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
    }

    public void setFirstName(String _firstName) {
        firstName = _firstName;
    }

    public void setLastName(String _lastName) {
        lastName = _lastName;
    }

    public int getReadingGoal() {
        return readingGoal;
    }

    public void setReadingGoal(int readingGoal) {
        this.readingGoal = readingGoal;
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
        FirebaseUtil.addToUserList(UID, "books-favorited", bookID)
                .thenAccept(Boolean ->{
            if(Boolean) Log.d("Added to favorites", bookID + " added to favorites");
        });
    }

}
