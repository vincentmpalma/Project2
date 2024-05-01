package com.example.sportsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.backup, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.backupItem);
        item.setVisible(true);

        item.setTitle("BACK"); //what is displayed in the menu
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(DeleteAdminActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Back?");
        alertBuilder.setPositiveButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                backup();
            }
        });

        alertBuilder.create().show();
    }
    private void backup() {
        startActivity(AdminActivity.adminPageIntentFactory(getApplicationContext(), loggedInUserId));
    }
}