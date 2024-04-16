package edu.utsa.cs3773.bookworkburrow;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.C;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import edu.utsa.cs3773.bookworkburrow.model.Book;

public class FirebaseBookUtils {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static CompletableFuture<Book> getBookByID(String ID){
        CompletableFuture<Book>  completableFuture = new CompletableFuture<>();
        db.collection("books").document(ID).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        DocumentSnapshot doc = task.getResult();
                        Book book = new Book();
                        book.setId(doc.getId());
                        book.setTitle(doc.getString("title"));
                        book.setAuthor(doc.getString("author"));
                        book.setPrice(doc.getDouble("price"));
                        book.setGenre(doc.getString("genre"));
                        book.setDescription(doc.getString("description"));
                        try {
                            book.setTextUrl(new URL(doc.getString("text-url")));
                            book.setCoverUrl(new URL(doc.getString("img-url")));
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }

                        completableFuture.complete(book);
                    }
                    else completableFuture.completeExceptionally(new Throwable(task.getException()));

                });
        return completableFuture;
    }

    /**
     * Queries all of the books given the title
     * @param title
     * @return ArrayList of books IDs with the same title
     */

    public static CompletableFuture<ArrayList<String>> getBooksByTitle(String title){
        CompletableFuture<ArrayList<String>>  completableFuture = new CompletableFuture<>();

        db.collection("books").whereEqualTo("title", title).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        ArrayList<String> books = new ArrayList<>();
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            books.add(documentSnapshot.getId());
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
     * @param genre genre of book
     * @return ArrayList of book IDs with the same genre
     */
    public static CompletableFuture<ArrayList<String>> getBooksByGenre(String genre){
        CompletableFuture<ArrayList<String>>  completableFuture = new CompletableFuture<>();
        db.collection("books").whereEqualTo("genre", genre).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        ArrayList<String> books = new ArrayList<>();
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            books.add(documentSnapshot.getId());
                        }
                        completableFuture.complete(books);
                    }
                    else{
                        completableFuture.completeExceptionally(new Throwable("Could not find books given genre"));
                    }
                });
        return completableFuture;
    }

    /**
     * Queries all books in database
     * @return Arraylist of all book IDs
     */
    public static CompletableFuture<ArrayList<String>> getAllBookIDs(){
        CompletableFuture<ArrayList<String>> completableFuture = new CompletableFuture<>();
        db.collection("books").get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        ArrayList<String> books = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot : task.getResult())
                            books.add(documentSnapshot.getId());
                        completableFuture.complete(books);
                    }
                    else{
                        completableFuture.completeExceptionally(new Throwable("Could not retrieve books"));
                    }
                });
        return completableFuture;
    }

    /**
     * Gets all books priced under a maximum price
     * @param max maximum price
     * @return Arraylist of book IDs
     */
    public static CompletableFuture<ArrayList<String>> getBooksPricedUnder(double max){
        CompletableFuture<ArrayList<String>> completableFuture = new CompletableFuture<>();
        db.collection("books").whereLessThan("price", max).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        ArrayList<String> books = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot : task.getResult())
                            books.add(documentSnapshot.getId());
                        completableFuture.complete(books);
                    }
                    else{
                        completableFuture.completeExceptionally(new Throwable("Could not retrieve books"));
                    }
                });
        return completableFuture;
    }

    /**
     * Gets all books priced over a minimum price
     * @param min minimum price
     * @return Arraylist of book IDs
     */
    public static CompletableFuture<ArrayList<String>> getBooksPricedOver(double min){
        CompletableFuture<ArrayList<String>> completableFuture = new CompletableFuture<>();
        db.collection("books").whereLessThan("price", min).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        ArrayList<String> books = new ArrayList<>();
                        for(DocumentSnapshot documentSnapshot : task.getResult())
                            books.add(documentSnapshot.getId());
                        completableFuture.complete(books);
                    }
                    else{
                        completableFuture.completeExceptionally(new Throwable("Could not retrieve books"));
                    }
                });
        return completableFuture;
    }
}
