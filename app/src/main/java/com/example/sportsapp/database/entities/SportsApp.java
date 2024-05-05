package com.example.sportsapp.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.sportsapp.database.SportsAppDatabase;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = SportsAppDatabase.SPORTS_APP_TABLE) //entity means this POJO will be stored in the database
public class SportsApp {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String favTeamAbv;
    private String league;
    private int userId;

    public SportsApp(String favTeamAbv, String league, int userId) {
        this.favTeamAbv = favTeamAbv;
        this.league = league;
        this.userId = userId;
    }

    @NonNull
    @Override
    public String toString() {
        return  favTeamAbv + '\n' +
                "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SportsApp sportsApp = (SportsApp) o;
        return id == sportsApp.id && userId == sportsApp.userId && Objects.equals(favTeamAbv, sportsApp.favTeamAbv) && Objects.equals(league, sportsApp.league);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, favTeamAbv, league, userId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFavTeamAbv() {
        return favTeamAbv;
    }

    public void setFavTeamAbv(String favTeamAbv) {
        this.favTeamAbv = favTeamAbv;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


}
