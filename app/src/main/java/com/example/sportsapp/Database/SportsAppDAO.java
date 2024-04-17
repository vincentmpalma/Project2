package com.example.sportsapp.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sportsapp.Database.entities.SportsApp;

import java.util.ArrayList;
import java.util.List;

//this interface represents the queries that are ran in the database, (remember this is an interface!)
@Dao
public interface SportsAppDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SportsApp sportsapp);

    @Query("SELECT * FROM " + SportsAppDatabase.SPORTS_APP_TABLE)
    ArrayList<SportsApp> getAllRecords();
}
