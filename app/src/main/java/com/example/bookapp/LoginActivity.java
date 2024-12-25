package com.example.bookapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookapp.databinding.ActivityLoginBinding;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        // Initialize UserSession
        UserSession.init(this);

        // Handle Login Button Click
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.loginEmail.getText().toString().trim();
                String password = binding.loginPassword.getText().toString();

                // Validation
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(LoginActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(LoginActivity.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                } else {
                    // Check Credentials
                    Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);

                    if (checkCredentials) {
                        // Login Successful
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        // Generate a 6-digit verification code
                        String verificationCode = generateVerificationCode();

                        // Store the code and email in UserSession
                        UserSession.storeGeneratedCode(verificationCode);
                        UserSession.setEmail(email);
                        UserSession.setCodeValidity(true);

                        // Set time validity for 5 minutes (300000 ms)
                        UserSession.setTimeValidity(true);


                        startTimer();

                        // Send the code via email
                        EmailActivity2FA.sendVerificationCode(
                                email,
                                verificationCode,
                                "Your Book App Verification Code",
                                "Use this code to complete your login:"
                        );

                        // Redirect to 2FA Verification Activity
                        Intent intent = new Intent(LoginActivity.this, CodeVerificationPage.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Redirect to Sign Up Activity
        binding.noAccountTv.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });

        // Redirect to Forgot Password Activity
        binding.forgotTv.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }


    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generate a random number between 100000 and 999999
        return String.valueOf(code);
    }


    private void startTimer() {
        // 5 minutes = 300,000 milliseconds
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UserSession.setTimeValidity(false);
                Toast.makeText(LoginActivity.this, "The verification code has expired.", Toast.LENGTH_SHORT).show();
            }
        }, 300000);
    }
}
