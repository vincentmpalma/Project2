package com.example.sportsapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.sportsapp.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + SportsAppDatabase.USER_TABLE + " ORDER BY username")
    List<User> getAllUsers();

    @Query("DELETE from " + SportsAppDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + SportsAppDatabase.USER_TABLE + " WHERE username == :username")
    User getUserByUserName(String username);
}
