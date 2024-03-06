package edu.utsa.cs3773.bookworkburrow.view.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Order {
    private ArrayList<Book> cartList;
    private Double totalPrice;
    private Calendar date;
    //private Date date;

    public Order(){
        cartList= new ArrayList<Book>();
        totalPrice = 0.0;
        date.set(Calendar.DAY_OF_MONTH,0);
    }

    public void addCart(Book newBook){
        cartList.add(newBook);
    }

    public void updatePrice(){
        // TODO
    }
}
