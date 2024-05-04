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
import com.example.sportsapp.databinding.ActivityNbaPopUpBinding;

public class nbaPopUpActivity extends AppCompatActivity {

    ActivityNbaPopUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNbaPopUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.9), (int) (height*.8));

        displayData();

    }

    public static Intent nbaPopUpIntentFactory(Context context, String homeTeamName, String roadTeamName, String homeLogoUrl, String awayLogoUrl, String homeScore, String awayScore, String homeRebounds, String awayRebounds, String homeAssists, String awayAssists, String homeFGA, String awayFGA, String homeFGM, String awayFGM, String homeFGP, String awayFGP, String homeFTA, String awayFTA, String homeFTM, String awayFTM, String homeFTP, String awayFTP, String homeTPA, String awayTPA, String homeTPM, String awayTPM, String homeTPP, String awayTPP ) {
        Intent intent = new Intent(context, nbaPopUpActivity.class);
        intent.putExtra("homeTeamName",homeTeamName);
        intent.putExtra("awayTeamName",roadTeamName);
        intent.putExtra("homeLogoUrl",homeLogoUrl);
        intent.putExtra("awayLogoUrl",awayLogoUrl);
        intent.putExtra("homeScore",homeScore);
        intent.putExtra("awayScore",awayScore);
        intent.putExtra("homeRebounds", homeRebounds);
        intent.putExtra("awayRebounds", awayRebounds);
        intent.putExtra("homeAssists", homeAssists);
        intent.putExtra("awayAssists", awayAssists);
        intent.putExtra("homeFGA", homeFGA);
        intent.putExtra("awayFGA",awayFGA );
        intent.putExtra("homeFGM",homeFGM );
        intent.putExtra("awayFGM",awayFGM );
        intent.putExtra("homeFGP", homeFGP);
        intent.putExtra("awayFGP", awayFGP);
        intent.putExtra("homeFTA", homeFTA);
        intent.putExtra("awayFTA",awayFTA );
        intent.putExtra("homeFTM", homeFTM);
        intent.putExtra("awayFTM",awayFTM );
        intent.putExtra("homeFTP", homeFTP);
        intent.putExtra("awayFTP", awayFTP);
        intent.putExtra("homeTPA", homeTPA);
        intent.putExtra("homeTPM",homeTPM );
        intent.putExtra("homeTPP", homeTPP);
        intent.putExtra("awayTPA", awayTPA);
        intent.putExtra("awayTPM", awayTPM);
        intent.putExtra("awayTPP", awayTPP);

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

        binding.homeHits.setText(intent.getStringExtra("homeAssists"));
        binding.awayHits.setText(intent.getStringExtra("awayAssists"));

        binding.homeRebounds.setText(intent.getStringExtra("homeRebounds"));
        binding.awayRebounds.setText(intent.getStringExtra("awayRebounds"));

        String homeFGM = intent.getStringExtra("homeFGM");
        String homeFGA = intent.getStringExtra("homeFGA");
        String homeFGP = intent.getStringExtra("homeFGP");
        String homeFG = homeFGM + "/" + homeFGA + " (" + homeFGP + "%)";

        String awayFGM = intent.getStringExtra("awayFGM");
        String awayFGA = intent.getStringExtra("awayFGA");
        String awayFGP = intent.getStringExtra("awayFGP");
        String awayFG = awayFGM + "/" + awayFGA + " (" + awayFGP + "%)";

        binding.homeErrors.setText(homeFG);
        binding.awayErrors.setText(awayFG);

        String homeTPM = intent.getStringExtra("homeTPM");
        String homeTPA = intent.getStringExtra("homeTPA");
        String homeTPP = intent.getStringExtra("homeTPP");
        String homeTP = homeTPM + "/" + homeTPA + " (" + homeTPP + "%)";

        String awayTPM = intent.getStringExtra("awayTPM");
        String awayTPA = intent.getStringExtra("awayTPA");
        String awayTPP = intent.getStringExtra("awayTPP");
        String awayTP = awayTPM + "/" + awayTPA + " (" + awayTPP + "%)";

        binding.homeThreePointers.setText(homeTP);
        binding.awayThreePointers.setText(awayTP);

        String homeFTM = intent.getStringExtra("homeFTM");
        String homeFTA = intent.getStringExtra("homeFTA");
        String homeFTP = intent.getStringExtra("homeFTP");
        String homeFT = homeFTM + "/" + homeFTA + " (" + homeFTP + "%)";

        String awayFTM = intent.getStringExtra("awayFTM");
        String awayFTA = intent.getStringExtra("awayFTA");
        String awayFTP = intent.getStringExtra("awayFTP");
        String awayFT = awayFTM + "/" + awayFTA + " (" + awayFTP + "%)";

        binding.homeFreeThrows.setText(homeFT);
        binding.awayFreeThrows.setText(awayFT);





    }
}