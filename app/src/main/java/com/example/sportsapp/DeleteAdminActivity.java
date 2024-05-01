package com.example.sportsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sportsapp.databinding.ActivityDelete2Binding;
import com.example.sportsapp.databinding.ActivityDeleteAdminBinding;

public class DeleteAdminActivity extends AppCompatActivity {

    private static final String DElETE_ADMIN_ACTIVITY_USER_ID = "com.example.sportsapp.DElETE_ADMIN_ACTIVITY_USER_ID";
    private int loggedInUserId = -1;
    private ActivityDeleteAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

    }

    private void delete() {
    }

    static Intent deleteadminActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, DeleteAdminActivity.class);
        intent.putExtra(DElETE_ADMIN_ACTIVITY_USER_ID, userId);
        return intent;
    }
}