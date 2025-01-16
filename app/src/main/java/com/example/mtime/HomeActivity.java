package com.example.mtime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private EditText hoursInput, minutesInput;
    private Button startTimerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize UI elements
        hoursInput = findViewById(R.id.hoursInput);
        minutesInput = findViewById(R.id.minutesInput);
        startTimerButton = findViewById(R.id.startTimerButton);

        startTimerButton.setOnClickListener(v -> startTimer());
    }

    private void startTimer() {
        // Validate user inputs
        String hoursString = hoursInput.getText().toString().trim();
        String minutesString = minutesInput.getText().toString().trim();

        if (hoursString.isEmpty() || minutesString.isEmpty()) {
            Toast.makeText(this, "Please enter both hours and minutes", Toast.LENGTH_SHORT).show();
            return;
        }

        int hours = Integer.parseInt(hoursString);
        int minutes = Integer.parseInt(minutesString);

        // Convert time to milliseconds
        long totalTimeInMillis = (hours * 3600 + minutes * 60) * 1000;

        // Start TimerService with the time in milliseconds
        Intent intent = new Intent(this, TimerService.class);
        intent.setAction(TimerService.ACTION_START_TIMER);
        intent.putExtra("total_time", totalTimeInMillis);
        startService(intent);
    }
}


//package com.example.mtime;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//
//public class HomeActivity extends AppCompatActivity {
//
//    private EditText hoursInput;
//    private EditText minutesInput;
//    private Button startTimerButton;
//    private long totalTimeInMillis;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home); // Layout with hours and minutes input
//
//        // Initialize views
//        hoursInput = findViewById(R.id.hoursInput);
//        minutesInput = findViewById(R.id.minutesInput);
//        startTimerButton = findViewById(R.id.startTimerButton);
//
//        // Check if a timer is already running (on app restart or phone restart)
//        if (TimerUtils.getStartTime(this) > 0) {
//            // Adjust the remaining time
//            long elapsedTime = TimerUtils.getElapsedTime(this);
//            long remainingTime = TimerUtils.getTotalTime(this) - elapsedTime;
//            if (remainingTime > 0) {
//                // Resume timer with the remaining time
//                startTimer(remainingTime);
//                navigateToTimerActivity(remainingTime); // Redirect to TimerActivity
//            }
//        }
//
//        // When the "Start Timer" button is clicked
//        startTimerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String hoursStr = hoursInput.getText().toString().trim();
//                String minutesStr = minutesInput.getText().toString().trim();
//
//                if (hoursStr.isEmpty() || minutesStr.isEmpty()) {
//                    Toast.makeText(HomeActivity.this, "Please enter both hours and minutes", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                try {
//                    int hours = Integer.parseInt(hoursStr);
//                    int minutes = Integer.parseInt(minutesStr);
//
//                    if (hours < 0 || hours > 999 || minutes < 0 || minutes > 59) {
//                        Toast.makeText(HomeActivity.this, "Invalid time format", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                    // Calculate total time in milliseconds
//                    totalTimeInMillis = (hours * 60 * 60 * 1000) + (minutes * 60 * 1000);
//
//                    // Start the TimerService
//                    Intent serviceIntent = new Intent(HomeActivity.this, TimerService.class);
//                    serviceIntent.setAction(TimerService.ACTION_START_TIMER);
//                    serviceIntent.putExtra("total_time", totalTimeInMillis);
//                    startService(serviceIntent);
//
//                    // Save total time in SharedPreferences
//                    TimerUtils.saveTotalTime(HomeActivity.this, totalTimeInMillis);
//                    TimerUtils.saveStartTime(HomeActivity.this, System.currentTimeMillis()); // Save start time
//
//                    // Redirect to TimerActivity to show the countdown
//                    navigateToTimerActivity(totalTimeInMillis);
//
//                } catch (NumberFormatException e) {
//                    Toast.makeText(HomeActivity.this, "Please enter valid numeric values", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//    private void startTimer(long remainingTime) {
//        // Start the timer using the remaining time if available
//        Intent intent = new Intent(HomeActivity.this, TimerService.class);
//        intent.setAction(TimerService.ACTION_START_TIMER);
//        intent.putExtra("total_time", remainingTime);
//        startService(intent);
//    }
//
//    private void navigateToTimerActivity(long totalTimeInMillis) {
//        // Redirect to TimerActivity with the total time to display the countdown
//        Intent intent = new Intent(HomeActivity.this, TimerActivity.class);
//        intent.putExtra("total_time", totalTimeInMillis); // Pass total time to TimerActivity
//        startActivity(intent);
//    }
//}
