package com.example.sportsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.sportsapp.database.SportsAppRepository;
import com.example.sportsapp.database.entities.User;
import com.example.sportsapp.databinding.ActivityAdminCreateBinding;
import com.example.sportsapp.databinding.ActivityRegisterBinding;

import java.util.ArrayList;
import java.util.List;

public class AdminCreateActivity extends AppCompatActivity {

    private ActivityAdminCreateBinding binding;

    private static final String ADMIN_CREATE_ACTIVITY_ID = "com.example.sportsapp.ADMIN_CREATE_ACTIVITY_ID";
    private SportsAppRepository repository;
    private int loggedInUserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminCreateBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        repository = SportsAppRepository.getRepository(getApplication());

        binding.createButton.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = AdminActivity.adminPageIntentFactory(getApplicationContext(),loggedInUserId);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Username is Taken", Toast.LENGTH_SHORT).show();
            }
        });
    }
    static Intent registerIntentFactory(Context context) {
        return new Intent(context, AdminActivity.class);
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
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AdminCreateActivity.this);
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
        startActivity(AdminActivity.adminPageIntentFactory(getApplicationContext(),loggedInUserId));
    }
    static Intent admincreateIntent(Context context) {
        return new Intent(context, AdminCreateActivity.class);
    }
}