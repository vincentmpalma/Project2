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

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.sportsapp.database.SportsAppRepository;
import com.example.sportsapp.database.entities.User;
import com.example.sportsapp.databinding.ActivityDelete2Binding;
import com.example.sportsapp.databinding.ActivityDeleteAdminBinding;

import java.util.List;

public class DeleteAdminActivity extends AppCompatActivity {

    private static final String DElETE_ADMIN_ACTIVITY_USER_ID = "com.example.sportsapp.DElETE_ADMIN_ACTIVITY_USER_ID";
    static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.sportsapp.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;
    private ActivityDeleteAdminBinding binding;
    private SportsAppRepository repository;
    private User user1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = SportsAppRepository.getRepository(getApplication());
        loginUser(savedInstanceState);

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

    }

    private void delete() {
        String username = binding.admininputEditText.getText().toString();

        if (username.isEmpty()) {
            Toast.makeText(this, "Username cannot be Blank", Toast.LENGTH_SHORT).show();
            return;
        }

        LiveData<List<User>> userObserver = repository.getAllUsers(); //live data

        userObserver.observe(this, userList -> { //unpack the live data
            boolean found = false;

            for (User user : userList) {
                if (user.getUsername().equalsIgnoreCase(username)) {
                    found = true;
                    user1 =user;
                    break;
                }
            }

            if (found) {

                repository.deleteUser(user1);
                Toast.makeText(this, "Account Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = AdminActivity.adminPageIntentFactory(getApplicationContext(),loggedInUserId);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Username is Not found", Toast.LENGTH_SHORT).show();
            }
        });
        updateSharedPreference();
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
    private void loginUser(Bundle savedInstanceState) {
        //checked shared preference for logged in user
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_user_Id_key), LOGGED_OUT);


        if (loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }

        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(DElETE_ADMIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }

        if (loggedInUserId == LOGGED_OUT) {
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId); //live data
        userObserver.observe(this, user -> { //unpack the live data
            this.user1 = user;
            if (user != null) {
                invalidateOptionsMenu();
            }
        });
    }
    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE); //getting reference for shared preference for this app
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit(); //make it editable
        sharedPrefEditor.putInt(getString(R.string.preference_user_Id_key), loggedInUserId); //putting key and the corresponding value (LOGGED_OUT here for his logout function
        sharedPrefEditor.apply(); //apply changes
    }
}