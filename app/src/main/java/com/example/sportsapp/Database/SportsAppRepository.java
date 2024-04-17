package com.example.sportsapp.Database;

import android.app.Application;
import android.util.Log;

import com.example.sportsapp.Database.entities.SportsApp;
import com.example.sportsapp.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SportsAppRepository {
    private SportsAppDAO sportsAppDAO;
    private ArrayList<SportsApp> allLogs;

    public SportsAppRepository(Application application){
        SportsAppDatabase db = SportsAppDatabase.getDatabase(application);
        this.sportsAppDAO = db.sportsAppDAO();
        this.allLogs = this.sportsAppDAO.getAllRecords();
    }

    public ArrayList<SportsApp> getAllLogs() {
        Future<ArrayList<SportsApp>> future = SportsAppDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<SportsApp>>() {
                    @Override
                    public ArrayList<SportsApp> call() throws Exception {
                        return sportsAppDAO.getAllRecords();
                    }
                }
        );
        try{
            return future.get();
        } catch(InterruptedException | ExecutionException e){
            Log.i(MainActivity.TAG, "Problem when getting all SportsApps in the repository");
        }
        return null;
    }

    public void insertSportsApp(SportsApp sportsApp){
        SportsAppDatabase.databaseWriteExecutor.execute(()->
        {
            sportsAppDAO.insert(sportsApp);
        });
    }


}
