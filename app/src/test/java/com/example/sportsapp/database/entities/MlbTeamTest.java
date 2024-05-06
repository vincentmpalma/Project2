package com.example.sportsapp.database.entities;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class MlbTeamTest {

    private MlbTeam mlb;

    @Before
    public void setUp() throws Exception {
        mlb = new MlbTeam("MOCK TEAM FULL NAME", "MOCK NAME", "MOCK LOCATION", "MCK", "MOCK.LOGO.COM", "MOCK LEAGUE", "MOCK DIVISION");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getLeague() {
        assertEquals("MOCK LEAGUE", mlb.getLeague());
        assertNotEquals("LEAGUE MOCK", mlb.getLeague());
        assertNotEquals("mock league", mlb.getLeague());
    }

    @Test
    public void setLeague() {
        assertEquals("MOCK LEAGUE", mlb.getLeague());
        mlb.setLeague("LEAGUE MOCK");
        assertEquals("LEAGUE MOCK", mlb.getLeague());
        assertNotEquals("MOCK LEAGUE",mlb.getLeague());
    }

    @Test
    public void getDivision() {
        assertEquals("MOCK DIVISION", mlb.getDivision());
        assertNotEquals("DIVISION MOCK", mlb.getDivision());
    }

    @Test
    public void setDivision() {
        assertEquals("MOCK DIVISION", mlb.getDivision());
        mlb.setDivision("DIVISION MOCK");
        assertEquals("DIVISION MOCK", mlb.getDivision());
        assertNotEquals("MOCK DIVISION", mlb.getDivision());
    }

    @Test
    public void getFullName() {
        assertEquals("MOCK TEAM FULL NAME", mlb.getFullName());
        assertNotEquals("NAME FULL TEAM MOCK", mlb.getFullName());
    }

    @Test
    public void setFullName() {
        assertEquals("MOCK TEAM FULL NAME", mlb.getFullName());
        mlb.setFullName("FAKE NAME");
        assertEquals("FAKE NAME", mlb.getFullName());
        assertNotEquals("MOCK TEAM FULL NAME", mlb.getFullName());
    }

    @Test
    public void getTeamName() {
        assertEquals("MOCK NAME", mlb.getTeamName());
        assertNotEquals("NAME METHOD", mlb.getTeamName());
    }

    @Test
    public void setTeamName() {
        assertEquals("MOCK NAME", mlb.getTeamName());
        mlb.setTeamName("NAME TEAM");
        assertEquals("NAME TEAM", mlb.getTeamName());
        assertNotEquals("MOCK NAME", mlb.getTeamName());

    }

    @Test
    public void getLocation() {
        assertEquals("MOCK LOCATION", mlb.getLocation());
        assertNotEquals("LOCATION MOCK", mlb.getLocation());
    }

    @Test
    public void setLocation() {
        assertEquals("MOCK LOCATION", mlb.getLocation());
        mlb.setLocation("TAHITI");
        assertEquals("TAHITI", mlb.getLocation());
        assertNotEquals("MOCK LOCATION",mlb.getLocation());
    }

    @Test
    public void getAcronym() {
        assertEquals("MCK", mlb.getAcronym());
        assertNotEquals("KCM",mlb.getAcronym());
    }

    @Test
    public void setAcronym() {
        assertEquals("MCK", mlb.getAcronym());
        mlb.setAcronym("LOL");
        assertEquals("LOL",mlb.getAcronym());
        assertNotEquals("MCK", mlb.getAcronym());
    }

    @Test
    public void getLogo() {
        assertEquals("MOCK.LOGO.COM", mlb.getLogo());
        assertNotEquals("google.com", mlb.getLogo());
    }

    @Test
    public void setLogo() {
        assertEquals("MOCK.LOGO.COM", mlb.getLogo());
        mlb.setLogo("google.com");
        assertEquals("google.com", mlb.getLogo());
        assertNotEquals("MOCK.LOGO.COM", mlb.getLogo());

    }


}