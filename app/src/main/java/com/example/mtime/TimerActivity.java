package com.example.mtime;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {

    private TextView timerTextView;
    private Button stopTimerButton;
    private long totalTimeInMillis;
    private long remainingTimeInMillis;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer); // Layout with a TextView to display countdown

        // Initialize views
        timerTextView = findViewById(R.id.timerTextView);
        stopTimerButton = findViewById(R.id.stopTimerButton);

        // Get the total time from the intent
        totalTimeInMillis = getIntent().getLongExtra("total_time", 0);
        remainingTimeInMillis = totalTimeInMillis;

        // Start the countdown timer
        startCountdownTimer();

        // Stop button click listener
        stopTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });
    }

    private void startCountdownTimer() {
        // Create a CountDownTimer that updates the UI every second
        countDownTimer = new CountDownTimer(remainingTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer display with the remaining time
                updateTimerDisplay(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                // Timer is done
                timerTextView.setText("00:00:00");
            }
        };

        // Start the timer
        countDownTimer.start();
    }

    private void updateTimerDisplay(long timeLeft) {
        int hours = (int) (timeLeft / (1000 * 60 * 60));
        int minutes = (int) ((timeLeft / (1000 * 60)) % 60);
        int seconds = (int) ((timeLeft / 1000) % 60);

        // Format the time in hh:mm:ss format
        String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timerTextView.setText(timeFormatted);
    }

    private void stopTimer() {
        // Stop the countdown timer
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // Optionally, you can reset the timer text to 00:00:00
        timerTextView.setText("00:00:00");

        // Also, stop the TimerService in case it's running in the background
        Intent serviceIntent = new Intent(TimerActivity.this, TimerService.class);
        serviceIntent.setAction(TimerService.ACTION_STOP_TIMER);
        startService(serviceIntent);

        // Finish TimerActivity to go back to the HomeActivity or any other desired screen
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancel the countdown timer when the activity is destroyed
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}


//package com.example.mtime;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class TimerActivity extends AppCompatActivity {
//
//    private TextView timerTextView;
//    private Button stopTimerButton;
//    private long totalTimeInMillis;
//    private CountDownTimer countDownTimer;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_timer); // Layout with a TextView to display countdown
//
//        // Initialize views
//        timerTextView = findViewById(R.id.timerTextView);
//        stopTimerButton = findViewById(R.id.stopTimerButton);
//
//        // Get the total time from the intent
//        totalTimeInMillis = getIntent().getLongExtra("total_time", 0);
//
//        // Start the countdown timer
//        startCountdownTimer(totalTimeInMillis);
//
//        // Stop button click listener
//        stopTimerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                stopTimer();
//            }
//        });
//    }
//
//    private void startCountdownTimer(long totalTime) {
//        // Create a CountDownTimer that updates the UI every second
//        countDownTimer = new CountDownTimer(totalTime, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                // Update the timer display with the remaining time
//                updateTimerDisplay(millisUntilFinished);
//            }
//
//            @Override
//            public void onFinish() {
//                // Timer is done
//                timerTextView.setText("00:00:00");
//            }
//        };
//
//        // Start the timer
//        countDownTimer.start();
//    }
//
//    private void updateTimerDisplay(long timeLeft) {
//        int hours = (int) (timeLeft / (1000 * 60 * 60));
//        int minutes = (int) ((timeLeft / (1000 * 60)) % 60);
//        int seconds = (int) ((timeLeft / 1000) % 60);
//
//        // Format the time in hh:mm:ss format
//        String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
//        timerTextView.setText(timeFormatted);
//    }
//
//    private void stopTimer() {
//        // Stop the countdown timer
//        if (countDownTimer != null) {
//            countDownTimer.cancel();
//        }
//
//        // Optionally, you can reset the timer text to 00:00:00
//        timerTextView.setText("00:00:00");
//
//        // Also, stop the TimerService in case it's running in the background
//        Intent serviceIntent = new Intent(TimerActivity.this, TimerService.class);
//        serviceIntent.setAction(TimerService.ACTION_STOP_TIMER);
//        startService(serviceIntent);
//
//        // Finish TimerActivity to go back to the HomeActivity or any other desired screen
//        finish();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // Cancel the countdown timer when the activity is destroyed
//        if (countDownTimer != null) {
//            countDownTimer.cancel();
//        }
//    }
//}
