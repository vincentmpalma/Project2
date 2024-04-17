package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sportsapp.database.SportsAppRepository;
import com.example.sportsapp.database.entities.SportsApp;
import com.example.sportsapp.databinding.ActivityMainBinding;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private SportsAppRepository repository;

    public static final String TAG = "DAC_SPORTS_APP";

    String mLeague = "";
    double mFirstInt = 0.0;
    int mSecondInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = SportsAppRepository.getRepository(getApplication());

        binding.sportsAppDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        binding.logButton.setOnClickListener(new View.OnClickListener() { //making on click listener for Submit button
            @Override
            public void onClick(View v) {
                getInfoFromDisplay(); //gets values from edit text and stores them in m variables
                insertSportsAppRecord(); //insert records from inputs into the database
                updateDisplay(); //updates the display
            }
        });
    }

    private void insertSportsAppRecord(){
        SportsApp app = new SportsApp(mLeague, mFirstInt, mSecondInt); //,make a new POJO
        repository.insertSportsApp(app); //inserting pojo we just created into the database through the repo
    }

    private void updateDisplay(){
        String currentInfo = binding.sportsAppDisplayTextView.getText().toString();
        String newDisplay = String.format(Locale.US, "Favorite League:%s%nFirstDouble:%.2f%nSecondInt:%d%n=-=-=-=-=-=%n%s", mLeague, mFirstInt,mSecondInt,currentInfo);
        binding.sportsAppDisplayTextView.setText(newDisplay);
        Log.i(TAG, repository.getAllLogs().toString());
    }

    private void getInfoFromDisplay() {
        mLeague = binding.favLeagueInputEditText.getText().toString();

        try {
            mFirstInt = Double.parseDouble(binding.integerInputEditText.getText().toString());
        } catch(NumberFormatException e){
            Log.d(TAG, "Error reading value from first int edit text.");
        }

        try {
            mSecondInt = Integer.parseInt(binding.doubleInputEditText.getText().toString());
        } catch(NumberFormatException e){
            Log.d(TAG, "Error reading value from second int edit text.");
        }

    }
}