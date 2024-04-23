package com.example.sportsapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sportsapp.databinding.ActivityMlbBinding;

public class MlbActivity extends AppCompatActivity {

    ActivityMlbBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMlbBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

    }
}