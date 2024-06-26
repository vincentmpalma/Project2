package com.example.sportsapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sportsapp.database.entities.SportsApp;
import com.example.sportsapp.database.entities.User;

import java.util.List;

//this interface represents the queries that are ran in the database, (remember this is an interface!)
@Dao
public interface SportsAppDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SportsApp sportsapp);

    @Query("SELECT * FROM " + SportsAppDatabase.SPORTS_APP_TABLE)
    List<SportsApp> getAllRecords();

    @Query("SELECT * FROM " + SportsAppDatabase.SPORTS_APP_TABLE + " WHERE userId = :loggedInUserId")
    List<SportsApp> getRecordsetUserId(int loggedInUserId);

    @Query("DELETE from " + SportsAppDatabase.SPORTS_APP_TABLE + " WHERE userId = :userId AND favTeamAbv = :teamAbv")
    void deleteTeam(int userId, String teamAbv);
}
