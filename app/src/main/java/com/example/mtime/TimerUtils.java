package com.example.mtime;

import android.content.Context;
import android.content.SharedPreferences;

public class TimerUtils {

    private static final String PREFS_NAME = "timer_prefs";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_TOTAL_TIME = "total_time";
    private static final String KEY_ELAPSED_TIME = "elapsed_time";

    // Save the start time when the timer begins
    public static void saveStartTime(Context context, long startTime) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(KEY_START_TIME, startTime);
        editor.apply(); // Apply the change asynchronously
    }

    // Retrieve the saved start time
    public static long getStartTime(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getLong(KEY_START_TIME, 0); // Return 0 if the start time is not saved
    }

    // Save the total time (duration) for the timer
    public static void saveTotalTime(Context context, long totalTime) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(KEY_TOTAL_TIME, totalTime);
        editor.apply(); // Apply the change asynchronously
    }

    // Retrieve the saved total time
    public static long getTotalTime(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getLong(KEY_TOTAL_TIME, 0); // Return 0 if the total time is not saved
    }

    // Save the elapsed time (how much time has passed so far)
    public static void saveElapsedTime(Context context, long elapsedTime) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(KEY_ELAPSED_TIME, elapsedTime);
        editor.apply(); // Apply the change asynchronously
    }

    // Retrieve the saved elapsed time
    public static long getElapsedTime(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getLong(KEY_ELAPSED_TIME, 0); // Return 0 if the elapsed time is not saved
    }

    // Clear all stored timer-related data (useful for resetting the timer)
    public static void clearTimerData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(KEY_START_TIME);
        editor.remove(KEY_TOTAL_TIME);
        editor.remove(KEY_ELAPSED_TIME);
        editor.apply(); // Apply the changes asynchronously
    }
}


//package com.example.mtime;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//public class TimerUtils {
//
//    private static final String PREFS_NAME = "TimerPrefs";
//
//    private static final String KEY_START_TIME = "start_time";
//    private static final String KEY_ELAPSED_TIME = "elapsed_time";
//    private static final String KEY_TOTAL_TIME = "total_time";
//
//    public static void saveStartTime(Context context, long startTime) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putLong(KEY_START_TIME, startTime);
//        editor.apply();
//    }
//
//    public static long getStartTime(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getLong(KEY_START_TIME, 0);
//    }
//
//    public static void saveElapsedTime(Context context, long elapsedTime) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putLong(KEY_ELAPSED_TIME, elapsedTime);
//        editor.apply();
//    }
//
//    public static long getElapsedTime(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getLong(KEY_ELAPSED_TIME, 0);
//    }
//
//    public static void saveTotalTime(Context context, long totalTime) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putLong(KEY_TOTAL_TIME, totalTime);
//        editor.apply();
//    }
//
//    public static long getTotalTime(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        return sharedPreferences.getLong(KEY_TOTAL_TIME, 0);
//    }
//
//    public static void clearTimerState(Context context) {
//        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.apply();
//    }
//}
