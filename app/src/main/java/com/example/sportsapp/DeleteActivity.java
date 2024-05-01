package com.example.sportsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;



import com.example.sportsapp.databinding.ActivityDelete2Binding;


public class DeleteActivity extends AppCompatActivity {
    private static final String DElETE_ACTIVITY_USER_ID = "com.example.sportsapp.DElETE_ACTIVITY_USER_ID";
    private int loggedInUserId = -1;
    private ActivityDelete2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDelete2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserId);
                startActivity(intent);
            }
        });
    }

    private void delete() {

    }

    static Intent deleteActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, DeleteActivity.class);
        intent.putExtra(DElETE_ACTIVITY_USER_ID, userId);
        return intent;
    }
}