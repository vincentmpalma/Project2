package com.example.sportsapp;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

import com.example.sportsapp.database.entities.User;

import org.junit.Test;

public class LoginUnitTest {
    @Test
    public void verifyTest(){
        User user= new User("bob","bobs");
        assertEquals(user,user);
        User user1 = new User("nonn","nonn");
        assertNotNull(user1);
    }
}
