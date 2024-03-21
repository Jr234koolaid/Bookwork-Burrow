package edu.utsa.cs3773.bookworkburrow.model;

public class Account {

    private final String m_UID;

    private String  m_email;
    private String  m_firstName;
    private String  m_lastName;

    enum Status { Teacher, Student, Other };
    //ArrayList<Book> booksOwned;
    //ArrayList<Book> favorites;
    //ArrayList<Order> orderHistory;
    //ArrayList<Review> reviewsMade;
    int readingGoal;

    public Account(String _UID) {
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
}
