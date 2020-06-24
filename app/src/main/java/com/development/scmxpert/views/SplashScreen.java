package com.development.scmxpert.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.development.scmxpert.R;
import com.development.scmxpert.views.login.LoginScreen;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_SCREEN_TIME_OUT=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            Intent i=new Intent(SplashScreen.this,
                    LoginScreen.class);
            startActivity(i);
            finish();

        }, SPLASH_SCREEN_TIME_OUT);
    }
}
