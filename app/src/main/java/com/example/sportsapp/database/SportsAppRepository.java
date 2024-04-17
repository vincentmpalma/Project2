package com.example.sportsapp.database;

import android.app.Application;
import android.util.Log;

import com.example.sportsapp.database.entities.SportsApp;
import com.example.sportsapp.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SportsAppRepository {
    private SportsAppDAO sportsAppDAO;
    private ArrayList<SportsApp> allLogs;

    private static SportsAppRepository repository;

    private SportsAppRepository(Application application){
        SportsAppDatabase db = SportsAppDatabase.getDatabase(application);
        this.sportsAppDAO = db.sportsAppDAO();
        this.allLogs = (ArrayList<SportsApp>) this.sportsAppDAO.getAllRecords();
    }

    //return instance of repository
    public static SportsAppRepository getRepository(Application application){
        if(repository!=null){
            return repository;
        }
        Future<SportsAppRepository> future = SportsAppDatabase.databaseWriteExecutor.submit(
                new Callable<SportsAppRepository>() {
                    @Override
                    public SportsAppRepository call() throws Exception {
                         return new SportsAppRepository(application);
                    }
                }
        );
        try{
            return future.get();
        } catch(InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG, "Problem getting SportsAppRepository, thread error.");
        }
        return null;
    }


    public ArrayList<SportsApp> getAllLogs() { //returns array of SportsApp POJOs
        //Future of an ArrayList of SportsApp (2ill have future values)
        Future<ArrayList<SportsApp>> future = SportsAppDatabase.databaseWriteExecutor.submit( //submitting task to thread in SportsAppDatabase
                new Callable<ArrayList<SportsApp>>() { //information done here will go back to future ^
                    @Override
                    public ArrayList<SportsApp> call() throws Exception { //anon inner class of Callable interface, method called "call"
                        return (ArrayList<SportsApp>) sportsAppDAO.getAllRecords();
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
