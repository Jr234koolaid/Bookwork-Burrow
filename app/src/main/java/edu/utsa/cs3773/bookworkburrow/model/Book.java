package edu.utsa.cs3773.bookworkburrow.model;

import java.net.URL;

public class Book implements Comparable<Book> {
    private String title;
    private String genre;
    private String author;
    private Double price;
    private String description;
    private String id;
    private URL textURL;
    private URL coverURL;

    public Book(){
        title = "No Title";
        genre = "No Genre";
        author = "Unknown Author";
        price = 0.0;
        description = "No Description";
        id = null;
        textURL = null;
        coverURL = null;
    }

    public Book(String enterId){
        id = enterId;
    }

    public String getTitle(){ return title; }
    public String getGenre(){ return genre; }
    public String getAuthor(){ return author; }
    public Double getPrice(){ return price; }
    public String getDescription(){ return description; }
    public String getId(){ return id; }
    public URL getTextURL(){ return textURL; }
    public URL getCoverURL(){ return coverURL; }

    public void setTitle(String s){ title = s; }
    public void setGenre(String s){ genre = s; }
    public void setAuthor(String s){ author = s; }
    public void setPrice(Double s){ price = s; }
    public void setDescription(String s){ description = s; }
    public void setId(String s){ id = s; }
    public void setTextUrl(URL url){ textURL = url; }
    public void setCoverUrl(URL url){ coverURL = url; }

    @Override
    public int compareTo(Book book) { return title.compareTo(book.getTitle()); }
}
