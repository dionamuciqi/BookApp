package com.example.bookapp;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    EditText title_input, author_input, pages_input;
    Button add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        title_input = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        pages_input = findViewById(R.id.pages_input);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = title_input.getText().toString().trim();
                String author = author_input.getText().toString().trim();
                String pagesString = pages_input.getText().toString().trim();

                if (title.isEmpty() || author.isEmpty() || pagesString.isEmpty()) {
                    Toast.makeText(AddActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        int pages = Integer.parseInt(pagesString);

                        // DatabaseHelper to add book
                        DatabaseHelper db = new DatabaseHelper(AddActivity.this);
                        db.addBook(title, author, pages);

                        // Success message
                        Toast.makeText(AddActivity.this, "Book added successfully", Toast.LENGTH_SHORT).show();

                        // Optionally, you can clear the input fields after adding the book
                        title_input.setText("");
                        author_input.setText("");
                        pages_input.setText("");

                    } catch (NumberFormatException e) {
                        // Failure message if pages is not a valid number
                        Toast.makeText(AddActivity.this, "Please enter a valid number for pages", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
