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

    DatabaseHelper databaseHelper;
    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    private void validateData() {

        String email = binding.emailEt.getText().toString().trim();
        String newPassword = binding.newPasswordEt.getText().toString().trim();
        String confirmPassword = binding.confirmPasswordEt.getText().toString().trim();

        if (email.isEmpty()) {
            Toast.makeText(this, "Enter email...", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email...", Toast.LENGTH_SHORT).show();
        } else if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please enter and confirm your new passord", Toast.LENGTH_SHORT).show();
        } else if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show();
        } else if (newPassword.length() < 6 ) {
            Toast.makeText(this, "Password must be at leasr 6 character!", Toast.LENGTH_SHORT).show();
        } else if (!isValidPassword(newPassword)) {
            Toast.makeText(this, "Password must include: \n- At least one uppercase letter\n- At least one lowercase letter\n- At least one number\n- At least one special character\" ", Toast.LENGTH_SHORT).show();
        } else {
            recoverPassword(email, newPassword);
        }
    }
    // qekjo mdoket duhet me ju shtu ni pjese qetu nelt qe me funksionu
    private boolean isValidPassword(String password) {
        // Password regex: 6-20 chars, 1 digit, 1 special char, 1 uppercase, 1 lowercase
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,20}$";
        return password.matches(passwordPattern);
    }

    private void recoverPassword(String email, String newPassword) {
        boolean userExists = databaseHelper.checkEmail(email);

        if (userExists) {
            boolean isUpdated = databaseHelper.updatePassword(email, newPassword);

            if (isUpdated) {
                Toast.makeText(ForgotPasswordActivity.this, "Password reset successfully!", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(ForgotPasswordActivity.this,"Failed to reset password.", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(ForgotPasswordActivity.this, "Email not found", Toast.LENGTH_SHORT).show();
        }
    }
}