package com.example.sportsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.example.sportsapp.database.SportsAppRepository;
import com.example.sportsapp.database.entities.MlbTeam;
import com.example.sportsapp.database.entities.User;
import com.example.sportsapp.databinding.ActivityMlbSearchBinding;

import java.sql.SQLOutput;
import java.util.List;


public class MlbSearchActivity extends AppCompatActivity {

    private static final String MLB_ACTIVITY_USER_ID = "com.example.sportsapp.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.sportsapp.SHARED_PREFERENCE_USERID_KEY";
    static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.sportsapp.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;
    private SportsAppRepository repository;
    private User user;

    ActivityMlbSearchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMlbSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = SportsAppRepository.getRepository(getApplication());
        loginUser(savedInstanceState);
        displayAllTeams();


        binding.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = binding.searchEditText.getText().toString();
                if(!search.isEmpty()){
                    displaySearchTeams(search);
                } else {
                    clearLayout();
                    displayAllTeams();
                }
            }
        });

    }

    public static Intent mlbSearchIntentFacotry(Context context){
        Intent intent = new Intent(context, MlbSearchActivity.class);
        return intent;
    }

    private void displayAllTeams(){
        LiveData<List<MlbTeam>> teamObserver = repository.getAllMlbTeams(); //live data

        Observer<List<MlbTeam>> observer = new Observer<List<MlbTeam>>() {
            @Override
            public void onChanged(List<MlbTeam> mlbTeams) {
                LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(175, 175);
                LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                buttonParams.bottomMargin = 100;
                for (MlbTeam team : mlbTeams) {
                    //binding.textView.setText(binding.textView.getText().toString() + " " + team.getTeamName());

                    TextView nameTextView = new TextView(getApplicationContext());
                    nameTextView.setLayoutParams(textParams);
                    nameTextView.setTextSize(25);
                    nameTextView.setText(team.getFullName());

                    TextView divisionTextView = new TextView(getApplicationContext());
                    divisionTextView.setLayoutParams(textParams);
                    divisionTextView.setTextSize(15);
                    divisionTextView.setText(team.getLeague() + " League " + team.getDivision());

                    ImageView logoImageView = new ImageView(getApplicationContext());
                    logoImageView.setLayoutParams(imageParams);
                    Glide.with(getApplicationContext()).load(team.getLogo()).into(logoImageView);

                    Button button = new Button(getApplicationContext());
                    button.setText("Favorite");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MlbSearchActivity.this, team.getTeamName(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    button.setLayoutParams(buttonParams);




                    binding.myLayout.addView(nameTextView);
                    binding.myLayout.addView(divisionTextView);
                    binding.myLayout.addView(logoImageView);
                    binding.myLayout.addView(button);
                }
            }
        };
        teamObserver.observe(this, observer);
    }

    private void displaySearchTeams(String search){
        clearLayout();

        LiveData<List<MlbTeam>> teamObserver = repository.getTeamsBySearch(search); //live data

        Observer<List<MlbTeam>> observer = new Observer<List<MlbTeam>>() {
            @Override
            public void onChanged(List<MlbTeam> mlbTeams) {
                LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(175, 175);
                LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                buttonParams.bottomMargin = 100;
                for (MlbTeam team : mlbTeams) {
                    //binding.textView.setText(binding.textView.getText().toString() + " " + team.getTeamName());

                    TextView nameTextView = new TextView(getApplicationContext());
                    nameTextView.setLayoutParams(textParams);
                    nameTextView.setTextSize(25);
                    nameTextView.setText(team.getFullName());

                    TextView divisionTextView = new TextView(getApplicationContext());
                    divisionTextView.setLayoutParams(textParams);
                    divisionTextView.setTextSize(15);
                    divisionTextView.setText(team.getLeague() + " League " + team.getDivision());

                    ImageView logoImageView = new ImageView(getApplicationContext());
                    logoImageView.setLayoutParams(imageParams);
                    Glide.with(getApplicationContext()).load(team.getLogo()).into(logoImageView);

                    Button button = new Button(getApplicationContext());
                    button.setText("Favorite");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(MlbSearchActivity.this, team.getTeamName(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    button.setLayoutParams(buttonParams);






                    binding.myLayout.addView(nameTextView);
                    binding.myLayout.addView(divisionTextView);
                    binding.myLayout.addView(logoImageView);
                    binding.myLayout.addView(button);
                }
            }
        };
        teamObserver.observe(this, observer);
    }

    private void clearLayout(){
        int childCount = binding.myLayout.getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View childView = binding.myLayout.getChildAt(i);
            binding.myLayout.removeView(childView);
        }
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
        if (user == null) {
            return false;
        }
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

    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MlbSearchActivity.this);
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

        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(MLB_ACTIVITY_USER_ID, loggedInUserId);

        startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
    }

    private void loginUser(Bundle savedInstanceState) {
        //checked shared preference for logged in user
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_user_Id_key), LOGGED_OUT);


        if (loggedInUserId == LOGGED_OUT & savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }

        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MLB_ACTIVITY_USER_ID, LOGGED_OUT);
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

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE); //getting reference for shared preference for this app
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit(); //make it editable
        sharedPrefEditor.putInt(getString(R.string.preference_user_Id_key), loggedInUserId); //putting key and the corresponding value (LOGGED_OUT here for his logout function
        sharedPrefEditor.apply(); //apply changes
    }
}

