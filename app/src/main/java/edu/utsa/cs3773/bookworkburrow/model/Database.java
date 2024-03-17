package edu.utsa.cs3773.bookworkburrow.model;

public class Database {

    private static Database Instance;

    public static Database getInstance() {

        if (Instance == null) {
            Instance = new Database();

        } return Instance;
    }

    private Database() {

    }

    public boolean add(String _username, String _password) {
        return false;
    }

    public boolean authenticate(String _username, String _password) {
        return false;
    }

} // class Database
