package com.majorproject.aquatracker.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.majorproject.aquatracker.R;

public class LoginActivity extends AppCompatActivity {

    private EditText emailTextView, passwordTextView;
    private Button Btn;
    private TextView redirectSignup;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            // User is already logged in, redirect to the home page
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        emailTextView = findViewById(R.id.editTextEmail);
        passwordTextView = findViewById(R.id.editTextPassword);
        Btn = findViewById(R.id.buttonLogin);
        redirectSignup = findViewById(R.id.buttonRedirectSignup);

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTextView.getText().toString().trim();
                String password = passwordTextView.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    showErrorMessage("Please enter email");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    showErrorMessage("Please enter password");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    showErrorMessage("Please enter a valid email address");
                    return;
                }

                if (password.length() < 7) {
                    showErrorMessage("Password must be at least 7 characters long");
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(
                                            @NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            showSuccessMessage("Login successful");
                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                        } else {
                                            showErrorMessage("Login failed");
                                        }
                                    }
                                });
            }
        });

        redirectSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void showErrorMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private void showSuccessMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}