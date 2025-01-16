package com.example.mtime;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class RebootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Check if the broadcast is a boot event
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Retrieve the start time and total time from SharedPreferences
            long startTime = TimerUtils.getStartTime(context);
            long totalTimeInMillis = TimerUtils.getTotalTime(context);

            // Calculate the elapsed time
            long elapsedTime = System.currentTimeMillis() - startTime;
            long remainingTimeInMillis = totalTimeInMillis - elapsedTime;

            // If there is remaining time, start the service again
            if (remainingTimeInMillis > 0) {
                // Create an Intent to start the TimerService
                Intent serviceIntent = new Intent(context, TimerService.class);
                serviceIntent.setAction(TimerService.ACTION_START_TIMER);
                serviceIntent.putExtra("total_time", remainingTimeInMillis);

                // Check if the Android version is Oreo (API 26) or higher to use startForegroundService()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // Start the service as a foreground service
                    context.startForegroundService(serviceIntent);
                } else {
                    // For older versions, start the service normally
                    context.startService(serviceIntent);
                }
            }
        }
    }
}
