package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.Random;

public class LoginMainActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView signUpText;
    private Button newButton;
    private DB db;
    private String generatedOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton=findViewById(R.id.loginButton);
        signUpText=findViewById(R.id.signUpText);
        newButton=findViewById(R.id.newButton);



        db = new DB(this);


        signUpText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginMainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        newButton.setOnClickListener(view ->{
            Toast.makeText(LoginMainActivity.this, "Button Clicked! ", Toast.LENGTH_SHORT).show();
        });
        loginButton.setOnClickListener(view-> validateFields());
        loginButton.setOnClickListener(view -> {
            Toast.makeText(LoginMainActivity.this, "Button Clicked! ", Toast.LENGTH_SHORT).show();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                if (db.validateUser(email, password)) {
                    generatedOtp = generateOtp();
                    sendEmail(email, generatedOtp);
                    Intent intent = new Intent(LoginMainActivity.this, OtpVerificationActivity.class);
                    intent.putExtra("otp", generatedOtp);
                    intent.putExtra("email", email);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginMainActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                }
            } else {

                Toast.makeText(LoginMainActivity.this, "Please enter Email and Password", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void validateFields() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email!", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password!", Toast.LENGTH_SHORT).show();
        } else {
            Boolean validateUser = db.validateUser(email, password);
            if (validateUser) {
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginMainActivity.this, OtpVerificationActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "LoggedIn Successfully!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private String generateOtp() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }


    private void sendEmail(String email, String otp) {
        String subject = "Your OTP Code";
        String message = "Your OTP is: " + otp;

        new EmailSender(email, subject, message).execute();
        Toast.makeText(this, "OTP sent to " + email, Toast.LENGTH_SHORT).show();
    }
}
