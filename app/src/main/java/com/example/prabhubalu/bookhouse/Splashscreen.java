package com.example.prabhubalu.bookhouse;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class Splashscreen extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_splashscreen);

        Handler myHandler = new Handler();
        myHandler.postDelayed(new Runnable() {

            @Override
            public void run() {

                    Intent intent = new Intent(Splashscreen.this,login.class);
                  startActivity(intent);


            }
        }, SPLASH_DURATION);
    }
}
