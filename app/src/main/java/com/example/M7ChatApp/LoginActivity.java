package com.example.M7ChatApp;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {
        attemptLogin();

    }

    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.example.M7ChatApp.RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    private void attemptLogin() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        if(email.equals("") || password.equals("")) return;
        final Intent intent = new Intent(getApplicationContext() , MainChatActivity.class);
        intent.putExtra("key" , email);
        Toast.makeText(this, "Login in progress...", Toast.LENGTH_SHORT).show();

        mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    showErrorDialog("Login failed!");
                }
                else{
                    Toast.makeText(LoginActivity.this, "Login Succeed", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops!")
                .setMessage(message)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.ok , null)
                .show();
    }



}