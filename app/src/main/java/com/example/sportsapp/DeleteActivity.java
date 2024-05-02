package com.example.sportsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;


import com.example.sportsapp.database.SportsAppRepository;
import com.example.sportsapp.database.entities.User;
import com.example.sportsapp.databinding.ActivityDelete2Binding;


public class DeleteActivity extends AppCompatActivity {
    private static final String DElETE_ACTIVITY_USER_ID = "com.example.sportsapp.DElETE_ACTIVITY_USER_ID";
    static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.sportsapp.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;
    private ActivityDelete2Binding binding;
    private SportsAppRepository repository;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDelete2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        repository = SportsAppRepository.getRepository(getApplication());
        loginUser(savedInstanceState);
        
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
        repository.deleteUser(user);
        Toast.makeText(this, "Account Deleted", Toast.LENGTH_SHORT).show();
        Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
        startActivity(intent);
    }

    static Intent deleteActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, DeleteActivity.class);
        intent.putExtra(DElETE_ACTIVITY_USER_ID, userId);
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
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(DeleteActivity.this);
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
        startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(),loggedInUserId));
    }
    private void loginUser(Bundle savedInstanceState) {
        //checked shared preference for logged in user
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_user_Id_key), LOGGED_OUT);


        if (loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }

        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(DElETE_ACTIVITY_USER_ID, LOGGED_OUT);
        }

        if (loggedInUserId == LOGGED_OUT) {
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId); //live data
        userObserver.observe(this, user -> { //unpack the live data
            this.user = user;
            if (user != null) {
                invalidateOptionsMenu();
            }
        });
    }
}