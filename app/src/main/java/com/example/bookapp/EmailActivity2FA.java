package com.example.bookapp;

import android.util.Log;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailActivity2FA {
    private static final String USERNAME = "diona.muqiqi3@gmail.com"; // Sender's email
    private static final String PASSWORD = ""; // app password
    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String SUBJECT = "Your 2FA Code";

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();


    public static void sendVerificationCode(String recipientEmail, String code, String subject, String text) {
        // Submit the email sending task to a background thread
        executorService.submit(() -> {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", SMTP_SERVER);
            props.put("mail.smtp.port", "587");

            // Create a new session with authentication
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            });

            try {
                // Creating a message with recipient, subject, and content
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(USERNAME));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject(subject);
                message.setText(text + " " + code);

                // Sending the email
                Log.i("EmailActivity2FA", "Sending email to: " + recipientEmail);
                Transport.send(message); // This is where the email is actually sent

                Log.d("EmailActivity2FA", "Verification email sent to " + recipientEmail);
            } catch (AuthenticationFailedException e) {
                Log.e("EmailActivity2FA", "Authentication failed. Please check the username and password.", e);
            } catch (MessagingException e) {
                Log.e("EmailActivity2FA", "Failed to send email. MessagingException occurred.", e);
            } catch (Exception e) {
                Log.e("EmailActivity2FA", "An error occurred while sending the email.", e);
            }
        });
    }
}
