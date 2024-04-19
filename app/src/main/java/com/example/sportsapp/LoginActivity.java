package com.example.sportsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.sportsapp.database.SportsAppRepository;
import com.example.sportsapp.database.entities.User;
import com.example.sportsapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private SportsAppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = SportsAppRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //when log in button is clicked...
                verifyUser();
            }
        });
    }

    private void verifyUser() {

        String username = binding.userNameLoginEditText.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this, "Username may not be blank", Toast.LENGTH_SHORT).show();
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserName(username); //live data
        userObserver.observe(this, user -> { //unpack the live data
            if (user != null) {
                String password = binding.passwordLoginEditText.getText().toString();
                if (password.equals(user.getPassword())) {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
                    SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
                    sharedPrefEditor.putInt(MainActivity.SHARED_PREFERENCE_USERID_KEY, user.getId());
                    sharedPrefEditor.apply();
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                } else {
                    Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    binding.passwordLoginEditText.setSelection(0);
                }
            } else {
                Toast.makeText(this, username + " is not a valid username", Toast.LENGTH_SHORT).show();
                binding.userNameLoginEditText.setSelection(0);
            }
        });
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LoginActivity.class);
    }
}