package edu.utsa.cs3773.bookworkburrow.model;

import java.net.URL;

public class Book {
    private String title;
    private String genre;
    private String author;
    private Double price;
    private String description;
    private URL textUrl;
    private URL coverUrl;

    public Book(){
        title = "No Title";
        genre = "No Genre";
        author = "Unknown Author";
        price = 0.0;
        description = "No description";
        textUrl = null;
        coverUrl = null;
    }

    public Book(String t){
        title = t;
        genre = "No Genre";
        author = "Unknown Author";
        price = 0.0;
        description = "No description";
        textUrl = null;
        coverUrl = null;
    }

    public String getTitle(){ return title; }
    public String getGenre(){ return genre; }
    public String getAuthor(){ return author; }
    public Double getPrice(){
        return price;
    }
    public String getDescription(){ return description; }
    public URL getTextUrl(){ return textUrl; }
    public URL getCoverUrl(){ return coverUrl; }

    public void setTitle(String s){ title = s; }
    public void setGenre(String s){ genre = s; }
    public void setAuthor(String s){ author = s; }
    public void setPrice(Double s){ price = s; }
    public void setDescription(String s){ description = s; }
    public void setTextUrl(URL s){ textUrl = s; }
    public void setCoverUrl(URL s){ coverUrl = s; }
}
