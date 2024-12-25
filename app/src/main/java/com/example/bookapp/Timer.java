package com.example.bookapp;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

public class Timer {

    private Context context;
    private int time;

    public Timer(Context context, int time) {
        this.context = context;
        this.time = time;
    }

    public void startTimer() {
        new CountDownTimer(time, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                System.out.println("Time remaining: " + millisUntilFinished / 1000 + " seconds");
            }

            @Override
            public void onFinish() {
                UserSession.setCodeValidity(false);  // Set the code validity to false when timer finishes
                Toast.makeText(context, "Code time has expired!", Toast.LENGTH_LONG).show();
            }
        }.start();
    }
}