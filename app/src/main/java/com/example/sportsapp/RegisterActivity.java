package com.example.sportsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sportsapp.database.SportsAppRepository;
import com.example.sportsapp.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    private SportsAppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        repository = SportsAppRepository.getRepository(getApplication());

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verifyUser()){
                    Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                    startActivity(intent);
                }
            }
        });

    }

    public boolean verifyUser(){

        String username = binding.userNameLoginEditText.getText().toString();
        String password = binding.passwordLoginEditText.getText().toString();
        String confirmPassword = binding.confirmPasswordLoginEditText.getText().toString();

        if(username.isEmpty()){
            Toast.makeText(this, "Username cannot be Blank", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(password.length() < 3 ){
            Toast.makeText(this, "Password must be at least 3 characters long", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Passwords do not Match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;


    }

    static Intent registerIntentFactory(Context context) {
        return new Intent(context, RegisterActivity.class);
    }
}