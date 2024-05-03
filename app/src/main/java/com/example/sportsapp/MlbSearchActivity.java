package com.example.sportsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
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

    ActivityMlbSearchBinding binding;
    private SportsAppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMlbSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = SportsAppRepository.getRepository(getApplication());
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
}

