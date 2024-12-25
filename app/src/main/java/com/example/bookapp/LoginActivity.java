package com.example.bookapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookapp.databinding.ActivityLoginBinding;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        // Initialize UserSession
        UserSession.init(this);

        // Handle Login Button Click
        binding.loginBtn.setOnClickListener(view -> {
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
                boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);

                if (checkCredentials) {
                    // Login Successful
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Sending verification code...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    String verificationCode = generateVerificationCode();
                    UserSession.storeGeneratedCode(verificationCode);
                    UserSession.setEmail(email);
                    UserSession.setCodeValidity(true);
                    UserSession.setTimeValidity(true);
                    startTimer();

                    // Simulate email sending
                    new Thread(() -> {
                        try {
                            EmailActivity2FA.sendVerificationCode(email, verificationCode, "Verification Code", "Use this code to complete your login:");
                            runOnUiThread(() -> {
                                progressDialog.dismiss();
                                Intent intent = new Intent(LoginActivity.this, CodeVerificationPage.class);
                                startActivity(intent);
                                finish();
                            });
                        } catch (Exception e) {
                            runOnUiThread(() -> {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Failed to send email.", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }).start();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
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

    // Generate a random 6-digit verification code
    private String generateVerificationCode() {
        Random random = new Random();
        return String.valueOf(100000 + random.nextInt(900000)); // Random number between 100000 and 999999
    }

    // Timer to handle code expiration after 5 minutes
    private void startTimer() {
        new Handler().postDelayed(() -> {
            UserSession.setTimeValidity(false);
            Toast.makeText(LoginActivity.this, "The verification code has expired.", Toast.LENGTH_SHORT).show();
        }, 300000); // 5 minutes = 300,000 ms
    }
}
