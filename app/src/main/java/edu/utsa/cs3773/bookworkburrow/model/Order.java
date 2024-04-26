package edu.utsa.cs3773.bookworkburrow.model;

import android.util.Log;

import com.google.firebase.Timestamp;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

import edu.utsa.cs3773.bookworkburrow.FirebaseBookUtils;
import edu.utsa.cs3773.bookworkburrow.FirebaseOrderUtil;

/**
 * object class representing and order in progress
 * @author Ryan Johnson
 */
public class Order {
    private ArrayList<Book> cartList;
    private Timestamp date;
    double discount;
    private String orderID;
    private ArrayList<String> bookIDs;

    /**
     * initializes a new order object
     */
    public Order(){
        cartList= new ArrayList<Book>();
        date = new Timestamp(new Date());
        discount = 0;
    }

    /**
     * adds a new book to the cart, accounting for its price
     * @param newBook, book to be added to cart (Book)
     */
    public void addBook(Book newBook){
        cartList.add(newBook);
        FirebaseOrderUtil.addBookToCart(newBook.getId()).thenAccept(Boolean ->{
            if(Boolean) Log.d("addBook function", "Success");
            else Log.d("addBook function", "Failure");
        });
    }

    /**
     * removes a book from the cart, accounting for its price
     * @param book, book to remove
     */
    public void removeBook(Book book){
        //TODO: update firestore
        cartList.remove(book);
        FirebaseOrderUtil.removeBookFromCart(book.getId()).thenAccept(Boolean ->{
            if(Boolean) Log.d("removeBook function", "Success");
            else Log.d("removeBook function", "Failure");
        });
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
     * returns the date as an easily readable string
     * @return String, the date in "Month, Date, Year" format
     */
    public String getStringDate(){
//        String[] dayPart = date.getTime().toString().split(" ");
//        return(dayPart[1]+", "+dayPart[2]+", "+dayPart[5]);
        return date.toDate().toString();
    }

    /**
     * returns the current date object
     * @return Timestamp, the current date object
     */
    public Timestamp getDate() { return date; }

    /**
     * sets the current date object
     * @param s, the new current date object (Double)
     */
    public void setDate(Timestamp s){ date = s; }

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

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public ArrayList<String> getBookIDs() {
        return bookIDs;
    }

    /**
     * Adds all books to cart list
     * @param bookIDs array list of bookID strings
     */
    public CompletableFuture<Boolean> setBookIDs(ArrayList<String> bookIDs) {
        // This will store all the futures from the asynchronous operations
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // Assign the new bookIDs to the instance variable
        this.bookIDs = bookIDs;

        // Loop through each book ID and initiate an asynchronous fetch operation
        for (String ID : bookIDs) {
            CompletableFuture<Void> future = FirebaseBookUtils.getBookByID(ID).thenAccept(book -> {
                cartList.add(book);
                Log.d("Book added to order", book.getTitle());
            });
            futures.add(future);
        }

        // Create a CompletableFuture that will complete when all fetched futures are complete
        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> {
                    // Once all futures complete, we return true
                    return true;
                });
    }

    /**
     * Removes all books from cart object and DB
     */
    public CompletableFuture<Boolean> clear() {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        FirebaseOrderUtil.clearCart().thenAccept(Boolean ->{
            if(Boolean) Log.d("Clear cart", "Success");
            else Log.d("Clear cart", "failure");
        });
        return completableFuture;
    }

}
