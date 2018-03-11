package com.example.prabhubalu.bookhouse;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        FirebaseApp.initializeApp(this);

        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();

        }

        else {
            Handler myHandler = new Handler();

            myHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, BooksActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_DURATION);
        }
    }
}
