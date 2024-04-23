package edu.utsa.cs3773.bookworkburrow.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.utsa.cs3773.bookworkburrow.FirebaseUserUtil;
import edu.utsa.cs3773.bookworkburrow.R;
import edu.utsa.cs3773.bookworkburrow.model.Account;


public class UpdateReadingGoalActivity extends AppCompatActivity {
    private EditText booksReadInput;
    private EditText goalInput;
    private TextView updateButton;
    private Account account;

    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_update_reading_goal);
        booksReadInput = findViewById(R.id.TotalBooksReadInput);
        goalInput = findViewById(R.id.TotalBooksToReadInput);
        updateButton = findViewById(R.id.UpdateButton);
        backButton = findViewById(R.id.goal_back_button);
        backButton.setOnClickListener(view ->{
            this.startActivity(new Intent(this, NavigationalActivity.class));
        });
        FirebaseUserUtil.getCurrUser().thenAccept(Account ->{
            account = Account;
            booksReadInput.setText(account.getBooksRead() + "");
            goalInput.setText(account.getReadingGoal() + "");
            updateButton.setOnClickListener(view -> update());

        });
    }

    private void update(){
        int read = Integer.parseInt(booksReadInput.getText().toString());
        int goal = Integer.parseInt(goalInput.getText().toString());
        account.setReadingGoal(goal);
        account.setBooksRead(read);
        Log.d("Books read in update", ""+account.getBooksRead());
        Log.d("Goal in update", ""+account.getReadingGoal());
        Toast.makeText(this, "Reading goal / Books read updated!", Toast.LENGTH_SHORT).show();
    }
}