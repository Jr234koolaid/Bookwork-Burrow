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
    private Double bookPrice;
    private Double totalPrice;
    private Calendar date;

    /**
     * initializes a new order object
     */
    public Order(){
        cartList= new ArrayList<Book>();
        bookPrice = 0.0;
        totalPrice = 0.0;
        date = Calendar.getInstance(TimeZone.getDefault());
    }

    /**
     * adds a new book to the cart, accounting for its price
     * @param newBook, book to be added to cart (Book)
     */
    public void addCart(Book newBook){
        cartList.add(newBook);
        updatePrice();
    }

    /**
     * removes a book from the cart, accounting for its price
     * @param index, index of the target book's position on the cart list (int)
     */
    public void removeCart(int index){
        cartList.remove(index);
        updatePrice();
    }

    /**
     * recounts the sum of the price of the books in the cart
     */
    public void updatePrice(){
        Double newPrice = 0.0;
        for(int i=0;i<cartList.size();i++){
            newPrice += cartList.get(i).getPrice();
        }
        bookPrice = newPrice;
    }

    /**
     * updates the stored date of the order to current time
     */
    public void updateDate(){
        date = Calendar.getInstance(TimeZone.getDefault());
    }

    /**
     * calculates the total price of the order by adding tax
     * @return Double, the total price after tax
     */
    public Double calculateTotal(){
        totalPrice = bookPrice + (bookPrice * 0.1);
        return totalPrice;
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
     * returns the cart list
     * @return ArrayList<Book>, the cart list
     */
    public ArrayList<Book> getCartList() { return cartList; }

    /**
     * returns the current price of all books in cart
     * @return Double, sum of the price of all books in cart
     */
    public Double getBookPrice() { return bookPrice; }

    /**
     * returns the current price of the total price after tax
     * @return Double, current price of the total price after tax
     */
    public Double getTotalPrice() { return totalPrice; }

    /**
     * returns the current date object
     * @return Calendar, the current date object
     */
    public Calendar getDate() { return date; }

    /**
     * sets the cart list
     * @param s, the new cart list (ArrayList<Book>)
     */
    public void setCartList(ArrayList<Book> s) { cartList = s; }

    /**
     * sets the current price of all books in cart
     * @param s, the new price of all books in cart (Double)
     */
    public void setBookPrice(Double s){ bookPrice = s; }

    /**
     * sets the current price of the total price after tax
     * @param s, the new price of the total price after tax (Double)
     */
    public void setTotalPrice(Double s){ totalPrice = s; }

    /**
     * sets the current date object
     * @param s, the new current date object (Double)
     */
    public void setDate(Calendar s){ date = s; }

    /**
     * Adds a book to the order
     * @param book to add
     */
    public void addBook(Book book){
        cartList.add(book);
    }

    /**
     * Removes a book from the order
     * @param book to remove
     */
    public void removeBook(Book book){
        cartList.remove(book);
    }
}
