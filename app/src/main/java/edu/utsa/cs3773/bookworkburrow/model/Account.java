package edu.utsa.cs3773.bookworkburrow.model;

import java.util.ArrayList;

public class Account {

    private final String m_UID;
    private String  m_email;
    private String  m_firstName;
    private String  m_lastName;
    ArrayList<Book> booksOwned;
    ArrayList<Book> favorites;
    ArrayList<Order> orderHistory;
    Order cart;
    int readingGoal;

    public Account(String _UID) {
        booksOwned = new ArrayList<>();
        orderHistory = new ArrayList<>();
        cart = new Order();
        m_UID = _UID;
    }

    public String getUID() {
        return m_UID;
    }

    public String getEmail() {
        return m_email;
    }

    public String getFirstName() {
        return m_firstName;
    }

    public String getLastName() {
        return m_lastName;
    }

    public void setEmail(String _email) {
        m_email = _email;
    }

    public void setFirstName(String _firstName) {
        m_firstName = _firstName;
    }

    public void setLastName(String _lastName) {
        m_lastName = _lastName;
    }

    public int getReadingGoal() {
        return readingGoal;
    }

    public void setReadingGoal(int readingGoal) {
        this.readingGoal = readingGoal;
    }

    public ArrayList<Book> getBooksOwned() {
        return booksOwned;
    }

    public void setBooksOwned(ArrayList<Book> booksOwned) {
        this.booksOwned = booksOwned;
    }

    public ArrayList<Book> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<Book> favorites) {
        this.favorites = favorites;
    }

    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(ArrayList<Order> orderHistory) {
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
    public void completeCheckout(){
        orderHistory.add(cart);
        booksOwned.addAll(cart.getCartList());
        cart = new Order();
        //TODO: update firebase
    }

    public String toString(){
        String s = "UID: " + m_UID + "\n";
        s += cart.toString();
        return s;
    }
}
