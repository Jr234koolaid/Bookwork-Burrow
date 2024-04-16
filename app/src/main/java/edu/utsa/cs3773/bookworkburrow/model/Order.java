package edu.utsa.cs3773.bookworkburrow.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * object class representing and order in progress
 * @author Ryan Johnson
 */
public class Order {
    private ArrayList<Book> cartList;
    private Calendar date;
    double discount;

    /**
     * initializes a new order object
     */
    public Order(){
        cartList= new ArrayList<Book>();
        date = Calendar.getInstance(TimeZone.getDefault());
        discount = 0;
    }

    /**
     * adds a new book to the cart, accounting for its price
     * @param newBook, book to be added to cart (Book)
     */
    public void addBook(Book newBook){
        //TODO: update firestore
        cartList.add(newBook);
    }

    /**
     * removes a book from the cart, accounting for its price
     * @param book, book to remove
     */
    public void removeBook(Book book){
        //TODO: update firestore
        cartList.remove(book);
    }

    /**
     * returns the current price of all books in cart
     * @return Double, sum of the price of all books in cart
     */
    public double getSubtotal() {
        double price = 0;
        for(Book book : cartList) price += book.getPrice();
        return price;
    }

    /**
     * returns the current price of the total price after tax
     * @return Double, current price of the total price after tax
     */
    public double getTotalWithTax() {
        double price = getSubtotal() + getTax();
        if(discount > 0) price -= price * discount;
        return price;
    }

    public double getTax(){
        return getSubtotal() * 0.0825;
    }


    /**
     * updates the stored date of the order to current time
     */
    public void updateDate(){
        date = Calendar.getInstance(TimeZone.getDefault());
    }

    /**
     * returns the date as an easily readable string
     * @return String, the date in "Month, Date, Year" format
     */
    public String getStringDate(){
        String[] dayPart = date.getTime().toString().split(" ");
        return(dayPart[1]+", "+dayPart[2]+", "+dayPart[5]);
    }

    /**
     * returns the current date object
     * @return Calendar, the current date object
     */
    public Calendar getDate() { return date; }

    /**
     * sets the current date object
     * @param s, the new current date object (Double)
     */
    public void setDate(Calendar s){ date = s; }

    /**
     * returns the cart list
     * @return ArrayList<Book>, the cart list
     */
    public ArrayList<Book> getCartList() { return cartList; }

    /**
     * sets the cart list
     * @param s, the new cart list (ArrayList<Book>)
     */
    public void setCartList(ArrayList<Book> s) { cartList = s; }

    public String toString(){
        String s = "Order:";
        for(Book book : cartList) s += book.getTitle() + " | ";
        return s;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount / 100;
    }
}
