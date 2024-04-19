package com.example.sportsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sportsapp.database.SportsAppRepository;
import com.example.sportsapp.database.entities.User;
import com.example.sportsapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    private SportsAppRepository repository;

    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository =SportsAppRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //when log in button is clicked...

                if(!verifyUser()){ //check if correct credentials is entered
                    Toast.makeText(LoginActivity.this, "Invalid Username or password", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId());
                    startActivity(intent);
                }
            }
        });
    }

    private boolean verifyUser(){

        String username = binding.userNameLoginEditText.getText().toString();
        if(username.isEmpty()){
            Toast.makeText(this, "Username may not be blank", Toast.LENGTH_SHORT).show();
            return false;
        }
        User user = repository.getUserByUserName(username);
        if(user != null){
            String password = binding.passwordLoginEditText.getText().toString();
            if(password.equals(user.getPassword())){
                return true;
            } else {
                Toast.makeText(this, "Invalid password.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        Toast.makeText(this, "No user named " + username +  " found", Toast.LENGTH_SHORT).show();
        return false;
    }

    static Intent loginIntentFactory(Context context){
        return new Intent(context, LoginActivity.class);
    }
}