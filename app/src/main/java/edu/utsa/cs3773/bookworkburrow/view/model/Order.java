package edu.utsa.cs3773.bookworkburrow.view.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Order {
    private ArrayList<Book> cartList;
    private Double bookPrice;
    private Double totalPrice;
    private Calendar date;
    //private Date date;

    public Order(){
        cartList= new ArrayList<Book>();
        bookPrice = 0.0;
        totalPrice = 0.0;
        date = Calendar.getInstance(TimeZone.getDefault());
    }

    public void addCart(Book newBook){
        cartList.add(newBook);
        updatePrice();
    }

    public void removeCart(int index){
        cartList.remove(index);
        updatePrice();
    }

    public void updatePrice(){
        Double newPrice = 0.0;
        for(int i=0;i<cartList.size();i++){
            newPrice += cartList.get(i).getPrice();
        }
        bookPrice = newPrice;
    }

    public void updateDate(){
        date = Calendar.getInstance(TimeZone.getDefault());
    }

    public Double calculateTotal(){
        totalPrice = bookPrice + (bookPrice * 0.1);
        return totalPrice;
    }

    public String getStringDate(){
        String[] dayPart = date.getTime().toString().split(" ");
        return(dayPart[1]+", "+dayPart[2]+", "+dayPart[5]);
    }

    public ArrayList<Book> getCartList() { return cartList; }

    public Double getBookPrice() { return bookPrice; }

    public Double getTotalPrice() { return totalPrice; }

    public Calendar getDate() { return date; }

    public void setCartList(ArrayList<Book> s) { cartList = s; }

    public void setBookPrice(Double s){ bookPrice = s; }

    public void setTotalPrice(Double s){ totalPrice = s; }

    public void setDate(Calendar s){ date = s; }
}
