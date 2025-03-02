package com.scientiahub.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.scientiahub.R;
import com.scientiahub.utils.FirebaseAuthManager;
import android.content.Intent;

public class AuthActivity extends AppCompatActivity {
    private EditText emailInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button registerButton;
    private FirebaseAuthManager authManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        authManager = FirebaseAuthManager.getInstance();

        // Если пользователь уже авторизован, переходим в главное приложение
        if (authManager.isUserLoggedIn()) {
            startMainActivity();
            finish();
            return;
        }

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
    }

    private void setupClickListeners() {
        loginButton.setOnClickListener(v -> handleLogin());
        registerButton.setOnClickListener(v -> handleRegistration());
    }

    private void handleLogin() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (validateInput(email, password)) {
            authManager.loginUser(email, password)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(this, "Успешный вход", Toast.LENGTH_SHORT).show();
                    startMainActivity();
                })
                .addOnFailureListener(e -> 
                    Toast.makeText(this, "Ошибка входа: " + e.getMessage(), 
                    Toast.LENGTH_LONG).show());
        }
    }

    private void handleRegistration() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (validateInput(email, password)) {
            authManager.registerUser(email, password)
                .addOnSuccessListener(authResult -> {
                    Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show();
                    startMainActivity();
                })
                .addOnFailureListener(e -> 
                    Toast.makeText(this, "Ошибка регистрации: " + e.getMessage(), 
                    Toast.LENGTH_LONG).show());
        }
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Пароль должен содержать минимум 6 символов", 
                Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
} 
