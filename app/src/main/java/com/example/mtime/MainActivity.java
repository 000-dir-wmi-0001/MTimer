package com.example.mtime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIMEOUT = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // The layout with the logo splash screen

        // Handler to delay the transition to the next activity (HomeActivity)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // After the splash screen timeout, navigate to HomeActivity
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Close MainActivity so the user can't return to it with the back button
            }
        }, SPLASH_SCREEN_TIMEOUT); // Delay in milliseconds (3000ms = 3 seconds)
    }
}

