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
import androidx.lifecycle.LiveData;

import com.example.sportsapp.database.SportsAppRepository;
import com.example.sportsapp.database.entities.User;
import com.example.sportsapp.databinding.ActivityRegisterBinding;

import java.util.ArrayList;
import java.util.List;

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
                verifyUser();
            }
        });

    }

    public void verifyUser() {

        String username = binding.userNameLoginEditText.getText().toString();
        String password = binding.passwordLoginEditText.getText().toString();
        String confirmPassword = binding.confirmPasswordLoginEditText.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this, "Username cannot be Blank", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 3) {
            Toast.makeText(this, "Password must be at least 3 characters long", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not Match", Toast.LENGTH_SHORT).show();
            return;
        }

        LiveData<List<User>> userObserver = repository.getAllUsers(); //live data

        userObserver.observe(this, userList -> { //unpack the live data
            boolean found = false;

            for (User user : userList) {
                if (user.getUsername().equalsIgnoreCase(username)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                repository.insertUser(new User(username, password));
                Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show();
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Username is Taken", Toast.LENGTH_SHORT).show();
            }
        });
    }

    static Intent registerIntentFactory(Context context) {
        return new Intent(context, RegisterActivity.class);
    }
}