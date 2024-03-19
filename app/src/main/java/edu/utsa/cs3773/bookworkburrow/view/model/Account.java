package edu.utsa.cs3773.bookworkburrow.view.model;

public class Account {
    String uid;
    String name;
    enum Status {Teacher, Student, Other};
    //ArrayList<Book> booksOwned;
    //ArrayList<Book> favorites;
    //ArrayList<Order> orderHistory;
    //ArrayList<Review> reviewsMade;
    int readingGoal;

    public Account(String uid){
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReadingGoal() {
        return readingGoal;
    }

    public void setReadingGoal(int readingGoal) {
        this.readingGoal = readingGoal;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
