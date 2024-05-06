package com.example.sportsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.sportsapp.database.SportsAppRepository;
import com.example.sportsapp.database.entities.MlbTeam;
import com.example.sportsapp.database.entities.SportsApp;
import com.example.sportsapp.database.entities.User;
import com.example.sportsapp.databinding.ActivityFavortieTeamsBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class favortieTeams extends AppCompatActivity {

    private static final String MLB_ACTIVITY_USER_ID = "com.example.sportsapp.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.sportsapp.SHARED_PREFERENCE_USERID_KEY";
    static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.sportsapp.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;
    private SportsAppRepository repository;
    private User user;

    ActivityFavortieTeamsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavortieTeamsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        repository = SportsAppRepository.getRepository(getApplication());
        loginUser(savedInstanceState);

        displayTeams();
    }

    private void displayTeams() {
        ArrayList<SportsApp> list = repository.getAllLogsByUserId(loggedInUserId);


        LiveData<List<MlbTeam>> teamObserver = repository.getAllMlbTeams(); //live data

        Observer<List<MlbTeam>> observer = new Observer<List<MlbTeam>>() {
            @Override
            public void onChanged(List<MlbTeam> mlbTeams) {
                LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(175, 175);
                LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                buttonParams.bottomMargin = 100;
                String test = "";
                for (MlbTeam team : mlbTeams) {
                    //binding.textView.setText(binding.textView.getText().toString() + " " + team.getTeamName());

                    for (int i = 0; i < list.size(); i++) {

                        if (team.getAcronym().equalsIgnoreCase(list.get(i).getFavTeamAbv())) {

                            test += team.getAcronym();

                            TextView nameTextView = new TextView(getApplicationContext());
                            nameTextView.setLayoutParams(textParams);
                            nameTextView.setTextSize(15);
                            nameTextView.setText(team.getFullName());
//
                            TextView divisionTextView = new TextView(getApplicationContext());
                            divisionTextView.setLayoutParams(textParams);
                            divisionTextView.setTextSize(10);
                            divisionTextView.setText(team.getLeague() + " League " + team.getDivision());

                            TextView locationTextView = new TextView(getApplicationContext());
                            locationTextView.setLayoutParams(textParams);
                            locationTextView.setTextSize(10);
                            locationTextView.setText(team.getLocation());

//
                            ImageView logoImageView = new ImageView(getApplicationContext());
                            logoImageView.setLayoutParams(imageParams);
                            Glide.with(getApplicationContext()).load(team.getLogo()).into(logoImageView);




                            Button button = new Button(getApplicationContext());
                            button.setText("Unfavorite");
                            button.setLayoutParams(buttonParams);
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(favortieTeams.this, "Deleted " + team.getTeamName(), Toast.LENGTH_SHORT).show();
                                    repository.deleteTeam(loggedInUserId, team.getAcronym().toUpperCase());
                                    clearLayout();
                                    displayTeams();
                                }
                            });


                            binding.myLayout.addView(nameTextView);
                            binding.myLayout.addView(divisionTextView);
                            binding.myLayout.addView(locationTextView);
                            binding.myLayout.addView(logoImageView);
                            binding.myLayout.addView(button);



                            String url = "https://site.api.espn.com/apis/site/v2/sports/baseball/mlb/scoreboard";

                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        JSONArray eventsArray = response.getJSONArray("events");
                                        StringBuilder resultBuilder = new StringBuilder();

                                        for (int i = 0; i < eventsArray.length(); i++) {
                                            JSONObject eventObject = eventsArray.getJSONObject(i);
                                            JSONArray competitionsArray = eventObject.getJSONArray("competitions");

                                            for (int j = 0; j < competitionsArray.length(); j++) {
                                                JSONObject competition = competitionsArray.getJSONObject(j);
                                                JSONArray competitorsArray = competition.getJSONArray("competitors");


                                                JSONObject venueObject = competition.getJSONObject("venue");
                                                String venue = venueObject.getString("fullName");
                                                JSONObject address = venueObject.getJSONObject("address");


                                                JSONObject homeTeam = competitorsArray.getJSONObject(0);
                                                JSONObject awayTeam = competitorsArray.getJSONObject(1);

                                                JSONObject homeTeamJSONObject = homeTeam.getJSONObject("team");
                                                JSONObject awayTeamJSONObject = awayTeam.getJSONObject("team");


                                                String homeDisplayName = homeTeamJSONObject.getString("displayName");
                                                String awayDisplayName = awayTeamJSONObject.getString("displayName");

                                                String homeScore = homeTeam.getString("score");
                                                String awayScore = awayTeam.getString("score");

                                                String homeName = homeTeamJSONObject.getString("abbreviation");
                                                String awayName = awayTeamJSONObject.getString("abbreviation");

                                                if (homeName.equalsIgnoreCase(team.getAcronym()) || awayName.equalsIgnoreCase(team.getAcronym())) {

                                                    String homeHits = homeTeam.getString("hits");
                                                    String awayHits = awayTeam.getString("hits");

                                                    String homeErrors = homeTeam.getString("errors");
                                                    String awayErrors = awayTeam.getString("errors");


                                                    JSONObject status = competition.getJSONObject("status");
                                                    JSONObject type = status.getJSONObject("type");
                                                    boolean isCompleted = type.getBoolean("completed");
                                                    int inning = status.getInt("period");

                                                    String homeLogoUrl = homeTeamJSONObject.getString("logo"); // Assuming 'logo' is the key for the logo URL
                                                    String awayLogoUrl = awayTeamJSONObject.getString("logo");

                                                    ImageView homeImageView = new ImageView(getApplicationContext());
                                                    homeImageView.setLayoutParams(imageParams);


                                                    ImageView roadImageView = new ImageView(getApplicationContext());
                                                    roadImageView.setLayoutParams(imageParams);

                                                    Glide.with(getApplicationContext()).load(homeLogoUrl).into(homeImageView);
                                                    Glide.with(getApplicationContext()).load(awayLogoUrl).into(roadImageView);

                                                    StringBuilder resultString = new StringBuilder();

                                                    if (isCompleted) {
                                                        resultString.append("FINAL").append("\n");
                                                    } else {
                                                        resultString.append("Inning: ").append(inning).append("\n");
                                                    }

                                                    int outs = 0;
                                                    StringBuilder gameTitleString = new StringBuilder(homeDisplayName + " vs " + awayDisplayName);
                                                    if (competition.has("situation")) {
                                                        JSONObject situation = competition.getJSONObject("situation");
                                                        if (situation.has("outs")) {
                                                            outs = situation.getInt("outs");


                                                        }
                                                    }
                                                    int finalOuts = outs;

                                                    logoImageView.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Intent intent = mlbPopUpActivity.mlbPopUpIntentFactory(getApplicationContext(), homeName, awayName, homeLogoUrl, awayLogoUrl, homeScore, awayScore, homeHits, awayHits, homeErrors, awayErrors, isCompleted, inning,finalOuts,venue);
                                                            startActivity(intent);
                                                        }
                                                    });

                                                }

                                            }

                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //movieData.setValue("Error occurred.");
                                }
                            });

                            //Volley.newRequestQueue(this).add(request);
                            Volley.newRequestQueue(getApplicationContext()).add(request);

                        }
                    }
                }
                //binding.textView3.setText(test);
            }
        };

        teamObserver.observe(this, observer);

    }

    public static Intent favoriteTeamsIntentFactory(Context context) {
        Intent intent = new Intent(context, favortieTeams.class);
        return intent;
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
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(favortieTeams.this);
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

    private void clearLayout() {
        int childCount = binding.myLayout.getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View childView = binding.myLayout.getChildAt(i);
            binding.myLayout.removeView(childView);
        }
    }
}