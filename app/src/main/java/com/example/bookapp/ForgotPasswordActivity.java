package com.example.bookapp;

import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookapp.databinding.ActivityForgotPasswordBinding;
import com.example.bookapp.databinding.ActivityLoginBinding;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText emailEditText;

    DatabaseHelper databaseHelper;
    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //qeto prej login veq e kom marr
        databaseHelper = new DatabaseHelper(this);

        binding.resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    private String email = "";

    private void validateData() {
        email = binding.emailEt.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Enter email...", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email...", Toast.LENGTH_SHORT).show();
        } else {
            recoverPassword();
        }
    }

    private void recoverPassword() {
        //qetu duhet mu bo lidhja me databaze
    }
}