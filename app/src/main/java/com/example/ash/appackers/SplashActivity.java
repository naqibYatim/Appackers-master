package com.example.ash.appackers;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    // time for splash screen out
    private static int time = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // add new handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homePage =new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(homePage);
                finish();
            }
        }, time);

    }
}
