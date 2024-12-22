package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText nameEditText, surnameEditText, emailEditText, passwordEditText;
    private Button signUpButton;
    private Button loginButton;
    private DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        db = new DB(this);

        nameEditText = findViewById(R.id.nameEditText);
        surnameEditText = findViewById(R.id.surnameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        loginButton=findViewById(R.id.loginButton);

        signUpButton.setOnClickListener(v ->{
            if(validateFields()) {
                String name = nameEditText.getText().toString().trim();
                String surname = surnameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if(db.checkIfEmailExists(email)) {
                    Toast.makeText(this, "Admin user already exists.", Toast.LENGTH_SHORT).show();
                }
                else{
                    String hashedPassword = hashPasswordWithBcrypt(password);

                    boolean isAdded = db.addUser(name, surname, email, hashedPassword);

                    if (isAdded) {
                        Toast.makeText(SignUpActivity.this, "Sign-Up Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, LoginMainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Sign-Up Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        loginButton.setOnClickListener(v ->{
            Toast.makeText(SignUpActivity.this, "Returning to LogIn page", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity.this, LoginMainActivity.class);
            startActivity(intent);
            finish();
        });

    }
    private boolean validateFields() {

        String name = nameEditText.getText().toString().trim();
        String surname = surnameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name) || !name.matches("[a-zA-Z]+")) {
            nameEditText.setError("Name must contain only letters and not be empty.");
            return false;
        }

        if (TextUtils.isEmpty(surname) || !surname.matches("[a-zA-Z]+")) {
            surnameEditText.setError("Surname must contain only letters and not be empty.");
            return false;
        }

        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address.");
            return false;
        }

        if (TextUtils.isEmpty(password) ||
                !Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).{6,20}$").matcher(password).matches()) {
            passwordEditText.setError("Password must contain 1 lowercase, 1 uppercase, 1 digit, 1 special character, and be 6-20 characters long.");
            return false;
        }

        return true;
    }
    private String hashPasswordWithBcrypt(String password) {
        return org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
    }
}






