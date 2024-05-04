package com.example.sportsapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.sportsapp.database.SportsAppRepository;
import com.example.sportsapp.database.entities.User;
import com.example.sportsapp.databinding.ActivityNbaBinding;

import org.json.JSONArray;
import org.json.JSONObject;

public class NbaActivity extends AppCompatActivity {

    ActivityNbaBinding binding;
    private static final String NBA_ACTIVITY_USER_ID = "com.example.sportsapp.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "com.example.sportsapp.SHARED_PREFERENCE_USERID_KEY";
    static final String SAVED_INSTANCE_STATE_USERID_KEY = "com.example.sportsapp.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final int LOGGED_OUT = -1;
    private int loggedInUserId = -1;
    private SportsAppRepository repository;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNbaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = SportsAppRepository.getRepository(getApplication());
        loginUser(savedInstanceState);

        loadData();

    }

    private void loadData() {
        LinearLayout myRoot = (LinearLayout) findViewById(R.id.my_layout);
        LinearLayout a = new LinearLayout(this);
        a.setOrientation(LinearLayout.HORIZONTAL);




        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(175, 175);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);



        String url = "https://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard";

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


                            JSONObject homeTeam = competitorsArray.getJSONObject(0);
                            JSONObject awayTeam = competitorsArray.getJSONObject(1);

                            JSONObject homeTeamJSONObject = homeTeam.getJSONObject("team");
                            JSONObject awayTeamJSONObject = awayTeam.getJSONObject("team");




                            String homeDisplayName = homeTeamJSONObject.getString("displayName");
                            String awayDisplayName = awayTeamJSONObject.getString("displayName");

                            Log.i("NBA", homeDisplayName);

                            String homeScore = homeTeam.getString("score");
                            String awayScore = awayTeam.getString("score");

                            JSONObject status = competition.getJSONObject("status");
                            JSONObject type = status.getJSONObject("type");
                            boolean isCompleted = type.getBoolean("completed");
                            int quarter = status.getInt("period");
                            String time = status.getString("displayClock");

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
                                resultString.append("Quarter: ").append(quarter).append("\n");
                            }

                            StringBuilder gameTitleString = new StringBuilder(homeDisplayName + " vs " + awayDisplayName);

                            TextView gameTitleTextView = new TextView(getApplicationContext());
                            gameTitleTextView.setLayoutParams(textParams);
                            gameTitleTextView.setTextSize(15);
                            gameTitleTextView.setGravity(Gravity.CENTER);
                            gameTitleTextView.setText(gameTitleString);


                            TextView textView = new TextView(getApplicationContext());
                            textView.setLayoutParams(textParams);
                            textView.setTextSize(25);
                            textView.setText(homeScore + "     " + awayScore);


                            LinearLayout gameTitleLinearLayout = new LinearLayout(getApplicationContext());
                            gameTitleLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            gameTitleLinearLayout.setGravity(Gravity.CENTER);
                            gameTitleLinearLayout.addView(gameTitleTextView);

                            LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            linearLayout.setGravity(Gravity.CENTER); // Set gravity to center
                            linearLayout.addView(homeImageView);
                            linearLayout.addView(textView);
                            linearLayout.addView(roadImageView);

                            TextView resultTextView = new TextView(getApplicationContext());
                            resultTextView.setLayoutParams(textParams);
                            resultTextView.setTextSize(25);
                            resultTextView.setText(resultString);

                            TextView timeTextView = new TextView(getApplicationContext());
                            timeTextView.setLayoutParams(textParams);
                            timeTextView.setTextSize(15);
                            timeTextView.setText(time);

                            LinearLayout resultLinearLayout = new LinearLayout(getApplicationContext());
                            resultLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            resultLinearLayout.setGravity(Gravity.CENTER);
                            resultLinearLayout.addView(resultTextView);

                            LinearLayout timeLinearLayout = new LinearLayout(getApplicationContext());
                            timeLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            timeLinearLayout.setGravity(Gravity.CENTER);
                            timeLinearLayout.addView(timeTextView);






                            binding.myLayout.addView(gameTitleLinearLayout);
                            binding.myLayout.addView(linearLayout);
                            binding.myLayout.addView(timeLinearLayout);
                            binding.myLayout.addView(resultLinearLayout);


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

        Volley.newRequestQueue(this).add(request);

    }

    static Intent nbaIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, NbaActivity.class);
        intent.putExtra(NBA_ACTIVITY_USER_ID, userId);
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
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(NbaActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Logout?");

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
        alertBuilder.setNeutralButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                backup();
            }
        });

        alertBuilder.create().show();
    }
    private void backup() {
        getIntent().putExtra(NBA_ACTIVITY_USER_ID,loggedInUserId);
        startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(),loggedInUserId));
    }

    private void logout() {

        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        getIntent().putExtra(NBA_ACTIVITY_USER_ID, loggedInUserId);

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
            loggedInUserId = getIntent().getIntExtra(NBA_ACTIVITY_USER_ID, LOGGED_OUT);
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