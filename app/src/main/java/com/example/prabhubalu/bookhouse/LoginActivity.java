package com.example.prabhubalu.bookhouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

     EditText et_emailAddress, et_password;
     Button loginButton, registerButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        et_emailAddress = findViewById(R.id.login_emailAddress);
        et_password = findViewById(R.id.login_password);

        loginButton = findViewById(R.id.login_loginButton);
        registerButton = findViewById(R.id.login_registerButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = et_emailAddress.getText().toString();
                String password = et_password.getText().toString();

                mAuth.signInWithEmailAndPassword(emailAddress, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent i = new Intent(LoginActivity.this, BooksActivity.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Error: " + task.getException(), Toast.LENGTH_LONG).show();

                                }
                            }
                        });
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

    }
}
