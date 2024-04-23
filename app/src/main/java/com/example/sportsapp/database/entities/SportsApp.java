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

    private String favLeague;
    //private double firstNum;
   // private int secondNum;
    private LocalDateTime date;
    private int userId;

    public SportsApp(String favLeague, int userId) {
        this.favLeague = favLeague;
        this.userId = userId;
        date = LocalDateTime.now();
    }

    @NonNull
    @Override
    public String toString() {
        return  favLeague + '\n' +
                "Date = " + date.toString() + '\n' +
                "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SportsApp sportsApp = (SportsApp) o;
        return id == sportsApp.id && userId == sportsApp.userId && Objects.equals(favLeague, sportsApp.favLeague) && Objects.equals(date, sportsApp.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, favLeague, date, userId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFavLeague() {
        return favLeague;
    }

    public void setFavLeague(String favLeague) {
        this.favLeague = favLeague;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
