package edu.utsa.cs3773.bookworkburrow.model;

import java.net.URL;

public class Book {
    private String title;
    private String genre;
    private String author;
    private Double price;
    private String description;
    private String id;


    public Book(){
        title = "No Title";
        genre = "No Genre";
        author = "Unknown Author";
        price = 0.0;
        description = "No Description";
        id = null;
    }

    public Book(String enterId){
        id = enterId;
        // TODO: retrieve book information from database via id
    }

    public String getTitle(){ return title; }
    public String getGenre(){ return genre; }
    public String getAuthor(){ return author; }
    public Double getPrice(){ return price; }
    public String getDescription(){ return description; }
    public String getId(){ return id; }

    public void setTitle(String s){ title = s; }
    public void setGenre(String s){ genre = s; }
    public void setAuthor(String s){ author = s; }
    public void setPrice(Double s){ price = s; }
    public void setDescription(String s){ description = s; }
    public void setId(String s){ id = s; }
}
