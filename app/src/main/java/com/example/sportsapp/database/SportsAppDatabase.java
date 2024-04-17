package com.example.sportsapp.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.sportsapp.database.entities.SportsApp;
import com.example.sportsapp.MainActivity;
import com.example.sportsapp.database.typeConverters.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//this class represents actual database, where our information is stored
@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {SportsApp.class}, version = 1, exportSchema = false)
public abstract class SportsAppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "SportsApp_database"; //name of database

    public static final String SPORTS_APP_TABLE = "sportsAppTable"; // final string for name of table

    private static volatile SportsAppDatabase INSTANCE; // once instance of database

    private static final int NUMBER_OF_THREADS = 4; // threads to run separately

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS); //put threads in a pool

    static SportsAppDatabase getDatabase(final Context context){
        if(INSTANCE == null){ //check if nothing is working on it
            synchronized (SportsAppDatabase.class){ //make sure nothing else is working on class or referencing it (locking it into a single thread)
                if(INSTANCE == null){ //check if nothing is working on it again
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            SportsAppDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()   //if something with database changes, clear database instead of crashing
                            .addCallback(addDefaultValues) // method preforms an action when database is created
                            .build();
                }
            }
        }
        return INSTANCE; //
    }

    // method preforms an action when database is created (like adding default users)
    private static final RoomDatabase.Callback  addDefaultValues = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED.");
            //TODO: add databaseWriteExecutor.execute(() -> {...}
        }
    };

    public abstract SportsAppDAO sportsAppDAO();
}
