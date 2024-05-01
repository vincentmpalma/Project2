package com.example.sportsapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sportsapp.database.entities.MlbTeam;

import java.util.List;
@Dao
public interface MlbDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MlbTeam... mlbteam);

    @Delete
    void delete(MlbTeam mlbteam);

    @Query("SELECT * FROM " + SportsAppDatabase.MLB_TABLE + " ORDER BY fullName")
    LiveData<List<MlbTeam>> getAllTeams();

    @Query("DELETE from " + SportsAppDatabase.MLB_TABLE)
    void deleteAll();


}
