package edu.utsa.cs3773.bookworkburrow.model;


public class Review {
    String ID;
    String bookID;
    String UID;
    int rating;
    String description;

    public Review(String ID, String bookID, String UID, int rating, String description){
        this.bookID = bookID;
        this.ID = ID;
        this.UID = UID;
        this.rating = rating;
        this.description = description;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
