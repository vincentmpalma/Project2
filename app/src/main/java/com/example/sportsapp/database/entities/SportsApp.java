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
    private double firstNum;
    private int secondNum;
    private LocalDateTime date;

    public SportsApp(String favLeague, double firstNum, int secondNum) {
        this.favLeague = favLeague;
        this.firstNum = firstNum;
        this.secondNum = secondNum;
        date = LocalDateTime.now();
    }

    @NonNull
    @Override
    public String toString() {
        return  favLeague + '\n' +
                "First Number Entered = " + firstNum + '\n' +
                "Second Number Entered = " + secondNum + '\n' +
                "Date = " + date.toString() + '\n' +
                "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SportsApp sportsApp = (SportsApp) o;
        return id == sportsApp.id && Double.compare(firstNum, sportsApp.firstNum) == 0 && secondNum == sportsApp.secondNum && Objects.equals(favLeague, sportsApp.favLeague) && Objects.equals(date, sportsApp.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, favLeague, firstNum, secondNum, date);
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

    public double getFirstNum() {
        return firstNum;
    }

    public void setFirstNum(double firstNum) {
        this.firstNum = firstNum;
    }

    public int getSecondNum() {
        return secondNum;
    }

    public void setSecondNum(int secondNum) {
        this.secondNum = secondNum;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
