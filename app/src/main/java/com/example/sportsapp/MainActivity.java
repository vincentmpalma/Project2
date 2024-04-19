package com.example.sportsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sportsapp.database.SportsAppRepository;
import com.example.sportsapp.database.entities.SportsApp;
import com.example.sportsapp.database.entities.User;
import com.example.sportsapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "com.example.sportsapp.MAIN_ACTIVITY_USER_ID";
    private ActivityMainBinding binding;

    private SportsAppRepository repository;

    public static final String TAG = "DAC_SPORTS_APP";

    String mLeague = "";
    double mFirstInt = 0.0;
    int mSecondInt = 0;


    //TODO: add login information
    private int loggedInUserId = -1; //default user
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginUser(); //make sure user is logged in and we have user object

        invalidateOptionsMenu();

        if (loggedInUserId == -1) {
            Intent intent = LoginActivity.loginIntentFactory(getApplicationContext()); //getting login intent
            startActivity(intent); //starting login view
        }

        repository = SportsAppRepository.getRepository(getApplication());

        binding.sportsAppDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        updateDisplay();
        binding.logButton.setOnClickListener(new View.OnClickListener() { //making on click listener for Submit button
            @Override
            public void onClick(View v) {
                getInfoFromDisplay(); //gets values from edit text and stores them in m variables
                insertSportsAppRecord(); //insert records from inputs into the database
                updateDisplay(); //updates the display
            }
        });


        //dont know why he did this, can prob just comment it out
        binding.favLeagueInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDisplay();
            }
        });

    }

    private void loginUser() {
        //TODO: Make login method FUNCTIONAL
        user = new User("Drew","password");
        loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, -1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        item.setTitle(user.getUsername()); //what is displayed in the menu
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void showLogoutDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertDialog.setMessage("Logout?");

        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();
    }

    private void logout() {
        //TODO: Finish logout method
        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    static Intent mainActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent; //returning intent to start mainActivity
    }


    private void insertSportsAppRecord() {
        if (mLeague.isEmpty()) {
            return;
        }

        SportsApp app = new SportsApp(mLeague, mFirstInt, mSecondInt, loggedInUserId); //,make a new POJO
        repository.insertSportsApp(app); //inserting pojo we just created into the database through the repo
    }

    private void updateDisplay() {
        ArrayList<SportsApp> allLogs = repository.getAllLogs();
        if (allLogs.isEmpty()) {
            binding.sportsAppDisplayTextView.setText(R.string.nothing_to_show);
        } else {

            StringBuilder sb = new StringBuilder();
            for (SportsApp app : allLogs) {
                sb.append(app);
            }

            binding.sportsAppDisplayTextView.setText(sb.toString());

        }
        //String currentInfo = binding.sportsAppDisplayTextView.getText().toString();
        //String newDisplay = String.format(Locale.US, "Favorite League:%s%nFirstDouble:%.2f%nSecondInt:%d%n=-=-=-=-=-=%n%s", mLeague, mFirstInt,mSecondInt,currentInfo);
        //binding.sportsAppDisplayTextView.setText(newDisplay);
        //Log.i(TAG, repository.getAllLogs().toString());
    }

    private void getInfoFromDisplay() {
        mLeague = binding.favLeagueInputEditText.getText().toString();

        try {
            mFirstInt = Double.parseDouble(binding.integerInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from first int edit text.");
        }

        try {
            mSecondInt = Integer.parseInt(binding.doubleInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from second int edit text.");
        }

    }
}