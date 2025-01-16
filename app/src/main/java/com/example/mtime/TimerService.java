package com.example.mtime;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Build;
import android.service.notification.NotificationListenerService;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class TimerService extends android.app.Service {

    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "timer_channel";
    private CountDownTimer countDownTimer;
    private long totalTimeInMillis;
    private long remainingTimeInMillis;

    public static final String ACTION_START_TIMER = "com.example.mtime.action.START_TIMER";
    public static final String ACTION_STOP_TIMER = "com.example.mtime.action.STOP_TIMER";

    @Override
    public void onCreate() {
        super.onCreate();

        // Create notification channel for devices running Android O or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Timer Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    @SuppressLint("ForegroundServiceType")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        if (ACTION_START_TIMER.equals(action)) {
            // Retrieve the time from the intent
            totalTimeInMillis = intent.getLongExtra("total_time", 0);
            remainingTimeInMillis = totalTimeInMillis; // Set remaining time to total time initially

            // Start the countdown timer in the foreground service
            startCountdownTimer();

            // Start the foreground service with a persistent notification
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // For Android 13 (API level 33) and higher, use foreground service type
                startForeground(NOTIFICATION_ID, createNotification(remainingTimeInMillis),
                        Service.FOREGROUND_SERVICE_TYPE_WORKER); // API 34+
            } else {
                // For devices with lower API levels (21 to 33), no foreground service type required
                startForeground(NOTIFICATION_ID, createNotification(remainingTimeInMillis));
            }
        }
        else if (ACTION_STOP_TIMER.equals(action)) {
            // Stop the timer and service
            stopTimer();
            stopSelf(); // Stop the service after stopping the timer
        }

        return START_STICKY; // Keep the service running even if the app is killed
    }

    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(remainingTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTimeInMillis = millisUntilFinished;
                updateNotification(remainingTimeInMillis); // Update notification with remaining time
            }

            @Override
            public void onFinish() {
                remainingTimeInMillis = 0;
                updateNotification(remainingTimeInMillis); // Final update for notification
                stopSelf(); // Stop the service once the timer is finished
            }
        };
        countDownTimer.start();
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Stop the countdown timer
            countDownTimer = null; // Nullify the timer instance
        }
    }

    private Notification createNotification(long remainingTimeInMillis) {
        // Create a notification showing the timer countdown
        String timeFormatted = formatTime(remainingTimeInMillis);

        // PendingIntent to open the main activity when clicking the notification
        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Timer is running")
                .setContentText("Remaining time: " + timeFormatted)
                .setSmallIcon(R.drawable.logo1) // Set your app's icon here
                .setContentIntent(resultPendingIntent)
                .setOngoing(true) // The notification will be persistent
                .build();
    }

    private void updateNotification(long remainingTimeInMillis) {
        Notification notification = createNotification(remainingTimeInMillis);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(NOTIFICATION_ID, notification);
        }
    }

    private String formatTime(long timeInMillis) {
        int hours = (int) (timeInMillis / (1000 * 60 * 60));
        int minutes = (int) ((timeInMillis / (1000 * 60)) % 60);
        int seconds = (int) ((timeInMillis / 1000) % 60);
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer(); // Ensure timer is stopped when service is destroyed
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

//package com.example.mtime;
//
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//import android.os.SystemClock;
//import android.widget.Toast;
//import androidx.annotation.Nullable;
//
//public class TimerService extends Service {
//
//    public static final String ACTION_START_TIMER = "com.example.mtime.ACTION_START_TIMER";
//    public static final String ACTION_STOP_TIMER = "com.example.mtime.ACTION_STOP_TIMER";
//    public static final String ACTION_UPDATE_TIME = "com.example.mtime.ACTION_UPDATE_TIME";
//    private long startTime;
//    private long elapsedTime;
//    private boolean isTimerRunning;
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (intent != null) {
//            String action = intent.getAction();
//            if (ACTION_START_TIMER.equals(action)) {
//                long totalTime = intent.getLongExtra("total_time", 0);
//                startTimer(totalTime);
//            } else if (ACTION_STOP_TIMER.equals(action)) {
//                stopTimer();
//            }
//        }
//        return START_STICKY; // Ensures the service restarts if it gets killed
//    }
//
//    private void startTimer(long totalTime) {
//        startTime = System.currentTimeMillis(); // Record the start time
//        isTimerRunning = true;
//
//        // Save the start time and total time to SharedPreferences
//        TimerUtils.saveStartTime(this, startTime);
//        TimerUtils.saveTotalTime(this, totalTime);
//
//        // Start a new thread to update the time
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (isTimerRunning) {
//                    long currentTime = System.currentTimeMillis();
//                    long elapsedTime = currentTime - startTime;
//                    TimerUtils.saveElapsedTime(TimerService.this, elapsedTime);
//
//                    // If the elapsed time exceeds the total time, stop the timer
//                    if (elapsedTime >= TimerUtils.getTotalTime(TimerService.this)) {
//                        stopTimer();
//                    }
//
//                    try {
//                        Thread.sleep(1000); // Sleep for 1 second
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
//    }
//
//    private void stopTimer() {
//        isTimerRunning = false;
//
//        // Notify the user the timer has finished
//        Toast.makeText(this, "Timer Stopped", Toast.LENGTH_SHORT).show();
//
//        // Reset timer values in SharedPreferences when stopped
//        TimerUtils.clearTimerState(this);
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//}
