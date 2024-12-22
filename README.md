
# Two-Factor Authentication (2FA) App

This is a Two-Factor Authentication (2FA) application built in Java using Android Studio. The app enhances security by requiring both a password and a randomly generated code for user verification.


## Features

- User Registration: Sign-up page to create a new user account with email and password.

- Email Verification: Sends a 4-digit code to the registered email address for authentication.

- Code Validation: Users must enter the correct verification code to proceed.

- Resend Code Option: Allows users to request a new code if the previous one expires.

- Secure Login: Users can log in only after successful verification.

- Database Integration: Stores user credentials and verification codes securely.


## Tech Stack

**Programming Language:** Java

**IDE:** Android Studio



## Installation


git clone  
```bash
  https://github.com/adeatabaku1/2FA_app.git
```
Open the project in Android Studio.

Sync Gradle files.

Build and run the application.
    
## Code Structure

```bash 
/app/src/main/java/com/example/2faapp/
├── activities/
│   ├── MainActivity.java
│   ├── LoginMainActivity.java
│   ├── SignUpActivity.java
│   ├── VerifiedActivity.java
│   ├── OtpVerificationActivity.java
│   ├── EmailSender.java
│___├── DB.java
```
## Security Measures

- Passwords are hashed before being stored in the database.

- Verification codes are valid for a limited time and expire after use.

- Codes are sent securely over email.


<p align="left">
  <img src="https://github.com/user-attachments/assets/a509667c-ca65-4134-805d-5f5b0c924cc9"/>
</p>
<p align="right">
  <img src="https://github.com/user-attachments/assets/4eeb6a33-98b2-44f1-b70d-a73a4310e921"/>
</p>



