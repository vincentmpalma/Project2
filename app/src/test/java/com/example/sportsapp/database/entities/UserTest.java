package com.example.sportsapp.database.entities;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

//@RunWith(MlbTeamTest.class)
public class UserTest {
    private User user1;
    private User user2;


    @Before
    public void setUp() throws Exception {
         user1 = new User("UnitTest","UnitTest");
         user2 = new User("UnitTest2","UnitTest2");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getId() {
        assertNotEquals(1,user1.getId());
        user1.setId(1);
        assertEquals(1,user1.getId());
    }

    @Test
    public void setId() {
        assertNotEquals(33,user1.getId());
        user1.setId(33);
        assertEquals(33,user1.getId());
    }

    @Test
    public void getUsername() {
        assertNotEquals(" ",user1.getUsername());
        assertEquals("UnitTest",user1.getUsername());
    }

    @Test
    public void setUsername() {
        assertNotEquals("setUsername",user1.getUsername());
        user1.setUsername("setUsername");
        assertEquals("setUsername",user1.getUsername());
    }

    @Test
    public void getPassword() {
        assertNotEquals(" ", user1.getPassword());
        assertEquals("UnitTest",user1.getPassword());
    }

    @Test
    public void setPassword() {
        assertNotEquals("setPassword",user1.getPassword());
        user1.setPassword("setPassword");
        assertEquals("setPassword",user1.getPassword());
    }

    @Test
    public void isAdmin() {
        assertNotEquals(true,user2.isAdmin());
        user2.setAdmin(true);
        assertTrue(user2.isAdmin());
    }

    @Test
    public void setAdmin() {
        assertFalse(user2.isAdmin());
        user2.setAdmin(true);
        assertTrue(user2.isAdmin());

    }
}