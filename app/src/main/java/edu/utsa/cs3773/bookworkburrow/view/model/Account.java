package edu.utsa.cs3773.bookworkburrow.view.model;

public class Account {
    String username;
    String password;
    String name;
    enum Status {Teacher, Student, Other};
    //ArrayList<Book> booksOwned;
    //ArrayList<Book> favorites;
    //ArrayList<Order> orderHistory;
    //ArrayList<Review> reviewsMade;
    int readingGoal;

    public Account(String username, String password, String name){
        this.username = username;
        this.password = password;
        this.name = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
