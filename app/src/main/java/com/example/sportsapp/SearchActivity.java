package com.example.sportsapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sportsapp.databinding.ActivityMainBinding;
import com.example.sportsapp.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {
    private static final String SEARCH_ACTIVITY_USER_ID = "com.example.sportsapp.SEARCH_ACTIVITY_USER_ID";
    private int loggedInUserId = -1;
    private ActivitySearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.mlbPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MlbActivity.mlbIntentFactory(getApplicationContext(), loggedInUserId);
                startActivity(intent);
            }
        });

        binding.nbaPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = NbaActivity.nbaIntentFactory(getApplicationContext(), loggedInUserId);
                startActivity(intent);
            }
        });
    }

    static Intent searchIntentFactory(Context context) {
        return new Intent(context, SearchActivity.class);
    }
}
