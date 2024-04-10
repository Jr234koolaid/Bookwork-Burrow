package edu.utsa.cs3773.bookworkburrow;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import edu.utsa.cs3773.bookworkburrow.model.Book;

public class FirebaseBookUtils {

    /**
     * Queries all of the books given the title
     * @param title
     * @return ArrayList of books with the same title
     */
    public static CompletableFuture<ArrayList<Book>> getBooksByTitle(String title){
        CompletableFuture completableFuture = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("books").whereEqualTo("title", title).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        ArrayList<Book> books = new ArrayList<>();
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            Book book = new Book();
                            String author = documentSnapshot.getString("author");
                            String genre = documentSnapshot.getString("genre");
                            String description = documentSnapshot.getString("description");
                            String text_url = documentSnapshot.getString("text-url");
                            String image_url = documentSnapshot.getString("image-url");
                            //TODO: set props to book object
                            books.add(book);
                        }
                        completableFuture.complete(books);
                    }
                    else{
                        completableFuture.completeExceptionally(new Throwable("Could not find books given title"));
                    }
                });
        return completableFuture;
    }

    /**
     * Queries all books in a given genre
     * @param genre
     * @return ArrayList of books with the same genre
     */
    public static CompletableFuture<ArrayList<Book>> getBooksByGenre(String genre){
        CompletableFuture completableFuture = new CompletableFuture<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("books").whereEqualTo("genre", genre).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        ArrayList<Book> books = new ArrayList<>();
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            Book book = new Book();
                            String author = documentSnapshot.getString("author");
                            String title = documentSnapshot.getString("title");
                            String description = documentSnapshot.getString("description");
                            String text_url = documentSnapshot.getString("text-url");
                            String image_url = documentSnapshot.getString("image-url");
                            //TODO: set props to book object
                            books.add(book);
                        }
                        completableFuture.complete(books);
                    }
                    else{
                        completableFuture.completeExceptionally(new Throwable("Could not find books given title"));
                    }
                });
        return completableFuture;
    }
}
