package com.example.prabhubalu.bookhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

     EditText usernameWrapper;
     EditText passwordWrapper;
     Button btn, reg_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameWrapper = findViewById(R.id.username);
        passwordWrapper = findViewById(R.id.password);

        btn = findViewById(R.id.login);

        reg_btn = findViewById(R.id.register);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, BooksActivity.class);
                startActivity(i);
            }
        });


        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

    }
}
