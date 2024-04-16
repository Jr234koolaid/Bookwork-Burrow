package edu.utsa.cs3773.bookworkburrow;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.common.util.concurrent.AtomicDouble;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class FirebaseDiscountUtils {

    static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static CompletableFuture<Boolean> verifyCode(String code){
        CompletableFuture completableFuture = new CompletableFuture<>();
        DocumentReference docRef = db.collection("discount-codes").document(code);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    completableFuture.complete(true);
                } else {
                    Log.d(TAG, "No such document");
                    completableFuture.complete(false);
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

        return completableFuture;
    }

    public static CompletableFuture<Double> getAmountByCode(String code){
        CompletableFuture completableFuture = new CompletableFuture<>();
        DocumentReference doc = db.collection("discount-codes").document(code);
        doc.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    double amount = document.getDouble("percent-off");
                    completableFuture.complete(amount);
                } else {
                    Log.d(TAG, "No such document");
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

        return completableFuture;
    }

}
