package com.example.bookapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.example.bookapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // View binding
    private ActivityMainBinding binding;

    // Notification channel ID
    private static final String CHANNEL_ID = "welcome_channel";
    private static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Create the notification channel
        createNotificationChannel();

        // Check and request notification permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_REQUEST_CODE);
            } else {
                // Show the welcome notification if permission is already granted
                showWelcomeNotification();
            }
        } else {
            // Show the welcome notification for devices below Android 13
            showWelcomeNotification();
        }

        // Handle Login Button click, start Login screen
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start LoginActivity when loginBtn is clicked
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Handle Skip Button click, start a screen without login (like Home screen)
        binding.skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Home screen or Dashboard
                Intent intent = new Intent(MainActivity.this, HomeActivity.class); // Replace HomeActivity with your main screen if needed
                startActivity(intent);
            }
        });
    }

    // Create a notification channel for Android 8.0 and above
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Welcome Channel";
            String description = "Channel for welcome notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Display the welcome notification
    private void showWelcomeNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo) // Set an icon for the notification
                .setContentTitle("Welcome to Book App")
                .setContentText("Enjoy exploring books and managing your library!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build()); // Display the notification
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                // Permission granted, show the notification
                showWelcomeNotification();
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(this, "Notification permission denied. You won't receive notifications.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
