package com.example.sportsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.sportsapp.databinding.ActivityMlbBinding;
import com.example.sportsapp.databinding.ActivityMlbPopUpBinding;

public class mlbPopUpActivity extends AppCompatActivity {

    ActivityMlbPopUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMlbPopUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.9), (int) (height*.8));


        displayData();



    }

    static Intent mlbPopUpIntentFactory(Context context, String homeTeamName, String roadTeamName, String homeLogoUrl, String awayLogoUrl, String homeScore, String awayScore, String homeHits, String awayHits, String homeErrors, String awayErrors) {
        Intent intent = new Intent(context, mlbPopUpActivity.class);
        intent.putExtra("homeTeamName",homeTeamName);
        intent.putExtra("awayTeamName",roadTeamName);
        intent.putExtra("homeLogoUrl",homeLogoUrl);
        intent.putExtra("awayLogoUrl",awayLogoUrl);
        intent.putExtra("homeScore",homeScore);
        intent.putExtra("awayScore",awayScore);
        intent.putExtra("homeHits",homeHits);
        intent.putExtra("awayHits",awayHits);
        intent.putExtra("homeErrors", homeErrors);
        intent.putExtra("awayErrors", awayErrors);
        return intent;
    }

    private void displayData(){
        Intent intent = getIntent();
        binding.homeTeamNameTextView.setText(intent.getStringExtra("homeTeamName"));
        binding.awayTeamNameTextView.setText(intent.getStringExtra("awayTeamName"));

        String homeLogoUrl = intent.getStringExtra("homeLogoUrl");
        String awayLogoUrl = intent.getStringExtra("awayLogoUrl");
        Glide.with(this).load(homeLogoUrl).into(binding.homeTeamLogo);
        Glide.with(this).load(awayLogoUrl).into(binding.awayTeamLogo);

        binding.homeRunScore.setText(intent.getStringExtra("homeScore"));
        binding.awayRunScore.setText(intent.getStringExtra("awayScore"));

        binding.homeHits.setText(intent.getStringExtra(("homeHits")));
        binding.awayHits.setText(intent.getStringExtra(("awayHits")));

        binding.homeErrors.setText(intent.getStringExtra("homeErrors"));
        binding.awayErrors.setText(intent.getStringExtra("awayErrors"));


    }




}