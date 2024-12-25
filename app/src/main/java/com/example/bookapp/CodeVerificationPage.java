package com.example.bookapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookapp.UserSession;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class CodeVerificationPage extends AppCompatActivity {

    private EditText editTextCode;
    private Button btnVerify, btnResendCode, btnGoToLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verification_page);

        // Initialize views
        editTextCode = findViewById(R.id.editTextCode);  // Ensure this ID matches with XML
        btnVerify = findViewById(R.id.btnVerify);  // Ensure this ID matches with XML
        btnResendCode = findViewById(R.id.btnResendCode);  // Ensure this ID matches with XML
        btnGoToLogIn = findViewById(R.id.btnGoToLogIn);  // Ensure this ID matches with XML

        // Verify Button Click Listener
        btnVerify.setOnClickListener(v -> {
            String enteredCode = editTextCode.getText().toString().trim();

            if (enteredCode.isEmpty()) {
                Toast.makeText(CodeVerificationPage.this, "Please enter the verification code", Toast.LENGTH_SHORT).show();
            } else if (!enteredCode.equals(UserSession.getGeneratedCode())) {
                Toast.makeText(CodeVerificationPage.this, "Invalid verification code", Toast.LENGTH_SHORT).show();
            } else if (!UserSession.getTimeIsValid()) {
                Toast.makeText(CodeVerificationPage.this, "Verification code has expired", Toast.LENGTH_SHORT).show();
            } else {
                // Verification successful
                Toast.makeText(CodeVerificationPage.this, "Verification Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CodeVerificationPage.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Resend Code Button Click Listener
        btnResendCode.setOnClickListener(v -> {
            String code = generateCode();
            UserSession.storeGeneratedCode(code);  // Store the generated code temporarily

            // Send the code to the user's email
            sendVerificationCode(UserSession.getEmail(), code, "Your 2FA code", "If you didn't try to log in, just ignore this email! \n Your 2FA code: ");
            UserSession.setTimeValidity(true);
            Toast.makeText(CodeVerificationPage.this, "Code sent!", Toast.LENGTH_SHORT).show();
        });

        // Go to Login Button Click Listener
        btnGoToLogIn.setOnClickListener(v -> {
            Intent intent = new Intent(CodeVerificationPage.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    // Dummy method to generate a code (replace with your actual code generation logic)
    private String generateCode() {
        return String.valueOf((int) (Math.random() * 9000) + 1000);  // Random 4-digit code
    }

    // Method to send the verification code to the user's email
    private void sendVerificationCode(String toEmail, String verificationCode, String subject, String messageText) {
        String host = "smtp.gmail.com";
        String port = "465";
        String fromEmail = "your-email@gmail.com";  // Replace with your email
        String password = "your-email-password";  // Replace with your email password or use app-specific password

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.socketFactory.port", port);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", port);

        // Create a session to authenticate the sender's credentials
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // Create the email message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(messageText + "\nVerification Code: " + verificationCode);

            // Send the email
            Transport.send(message);
            Toast.makeText(this, "Email sent successfully", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to send email", Toast.LENGTH_SHORT).show();
        }
    }
}
