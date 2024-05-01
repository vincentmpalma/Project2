package com.example.sportsapp.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.sportsapp.database.SportsAppDatabase;

import java.util.Objects;

@Entity(tableName = SportsAppDatabase.MLB_TABLE)
public class MlbTeam {


    @PrimaryKey(autoGenerate = true)
    private int id;
    private String fullName;
    private String teamName;
    private String location;
    private String acronym;
    private String logo;
    private String league;
    private String division;

    public MlbTeam(String fullName, String teamName, String location, String acronym, String logo, String league, String division) {
        this.fullName = fullName;
        this.teamName = teamName;
        this.location = location;
        this.acronym = acronym;
        this.league = league;
        this.division = division;
        this.logo = logo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MlbTeam mlbTeam = (MlbTeam) o;
        return id == mlbTeam.id && Objects.equals(fullName, mlbTeam.fullName) && Objects.equals(teamName, mlbTeam.teamName) && Objects.equals(location, mlbTeam.location) && Objects.equals(acronym, mlbTeam.acronym) && Objects.equals(logo, mlbTeam.logo) && Objects.equals(league, mlbTeam.league) && Objects.equals(division, mlbTeam.division);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, teamName, location, acronym, logo, league, division);
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
