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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

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
                for (MlbTeam team : mlbTeams) {
                    binding.textView.setText(binding.textView.getText().toString() + " " + team.getTeamName());
                }
            }
        };
        teamObserver.observe(this, observer);
    }
}

