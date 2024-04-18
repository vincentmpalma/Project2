package com.example.sportsapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sportsapp.database.entities.SportsApp;

import java.util.ArrayList;
import java.util.List;

//this interface represents the queries that are ran in the database, (remember this is an interface!)
@Dao
public interface SportsAppDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SportsApp sportsapp);

    @Query("SELECT * FROM " + SportsAppDatabase.SPORTS_APP_TABLE + " ORDER BY date DESC")
    List<SportsApp> getAllRecords();
}
