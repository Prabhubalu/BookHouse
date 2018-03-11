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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText et_emailAddress, et_password, et_confirmPassword, et_phoneNumber;
    Button registerButton;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        et_emailAddress = findViewById(R.id.register_emailAddress);
        et_password = findViewById(R.id.register_password);
        et_confirmPassword = findViewById(R.id.register_confirmPassword);
        et_phoneNumber = findViewById(R.id.register_phoneNumber);

        registerButton = findViewById(R.id.register_registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailAddress = et_emailAddress.getText().toString();
                String password = et_password.getText().toString();
                String confirmPassword = et_confirmPassword.getText().toString();
                final String phoneNumber = et_phoneNumber.getText().toString();

                if (password.equals(confirmPassword)) {
                    mAuth.createUserWithEmailAndPassword(emailAddress, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mUser = mAuth.getCurrentUser();

                                writeNewUser(mUser.getUid(), emailAddress, phoneNumber);

                                Intent i = new Intent(RegisterActivity.this, BooksActivity.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(RegisterActivity.this, "Error: " + task.getException(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mUser = mAuth.getCurrentUser();
    }

    private void writeNewUser(String userID, String emailAddress, String phoneNumber) {
        User user = new User(emailAddress, phoneNumber);

        mDatabase.child("users").child(userID).setValue(user);
    }
}
