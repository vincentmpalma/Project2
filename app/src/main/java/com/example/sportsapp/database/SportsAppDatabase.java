package com.example.sportsapp.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.sportsapp.database.entities.MlbTeam;
import com.example.sportsapp.database.entities.SportsApp;
import com.example.sportsapp.MainActivity;
import com.example.sportsapp.database.entities.User;
import com.example.sportsapp.database.typeConverters.LocalDateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//test commit

//this class represents actual database, where our information is stored
@TypeConverters(LocalDateTypeConverter.class)
@Database(entities = {SportsApp.class, User.class, MlbTeam.class}, version = 3, exportSchema = false)
public abstract class SportsAppDatabase extends RoomDatabase {

    public static  final String MLB_TABLE = "mlbtable";
    public static final String USER_TABLE = "usertable";
    private static final String DATABASE_NAME = "SportsAppDatabase"; //name of database

    public static final String SPORTS_APP_TABLE = "sportsAppTable"; // final string for name of table

    private static volatile SportsAppDatabase INSTANCE; // once instance of database

    private static final int NUMBER_OF_THREADS = 4; // threads to run separately

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS); //put threads in a pool

    static SportsAppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) { //check if nothing is working on it
            synchronized (SportsAppDatabase.class) { //make sure nothing else is working on class or referencing it (locking it into a single thread)
                if (INSTANCE == null) { //check if nothing is working on it again
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
    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.TAG, "DATABASE CREATED.");
            databaseWriteExecutor.execute(() -> {
                //on create...
                UserDAO dao = INSTANCE.userDAO(); //make new dao instance
                dao.deleteAll(); //delete all previous records

                User admin = new User("admin2", "admin2"); //making default admin
                admin.setAdmin(true); //making it have admin boolean
                dao.insert(admin); //inserting admin to database

                //now same for regular user...
                User testUser1 = new User("testuser1", "testuser1");
                dao.insert(testUser1);



                MlbDAO mlbdao = INSTANCE.mlbDAO();
                mlbdao.deleteAll();
                MlbTeam mlbTeam = new MlbTeam("Chicago White Sox", "White Sox", "Guaranteed Rate Field", "CHW", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/chw.png", "American", "Central");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Cleveland Guardians", "Guardians", "Progressive Field", "CLE", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/cle.png", "American", "Central");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Detroit Tigers", "Tigers", "Comerica Park", "DET", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/det.png", "American", "Central");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Minnesota Twins", "Twins", "Target Field", "MIN", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/min.png", "American", "Central");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Kansas City Royals", "Royals", "Kauffman Stadium", "KC", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/kc.png", "American", "Central");
                mlbdao.insert(mlbTeam);


                mlbTeam = new MlbTeam("Baltimore Orioles", "Orioles", "Oriole Park at Camden Yards", "BAL", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/bal.png", "American", "East");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Boston Red Sox", "Red Sox", "Fenway Park", "BOS", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/bos.png", "American", "East");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("New York Yankees", "Yankees", "Yankee Stadium", "NYY", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/nyy.png", "American", "East");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Tampa Bay Rays", "Rays", "Tropicana Field", "TB", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/tb.png", "American", "East");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Toronto Blue Jays", "Blue Jays", "Rogers Centre", "TOR", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/tor.png", "American", "East");
                mlbdao.insert(mlbTeam);

                mlbTeam = new MlbTeam("Houston Astros", "Astros", "Minute Maid Park", "HOU", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/hou.png", "American", "West");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Los Angeles Angels", "Angels", "Angel Stadium", "LAA", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/laa.png", "American", "West");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Oakland Athletics", "Athletics", "Oakland Coliseum", "OAK", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/oak.png", "American", "West");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Seattle Mariners", "Mariners", "T-Mobile Park", "SEA", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/sea.png", "American", "West");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Texas Rangers", "Rangers", "Globe Life Field", "TEX", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/tex.png", "American", "West");
                mlbdao.insert(mlbTeam);

                mlbTeam = new MlbTeam("Chicago Cubs", "Cubs", "Wrigley Field", "CHC", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/chc.png", "National", "Central");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Cincinnati Reds", "Reds", "Great American Ball Park", "CIN", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/cin.png", "National", "Central");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Milwaukee Brewers", "Brewers", "American Family Field", "MIL", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/mil.png", "National", "Central");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Pittsburgh Pirates", "Pirates", "PNC Park", "PIT", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/pit.png", "National", "Central");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("St. Louis Cardinals", "Cardinals", "Busch Stadium", "STL", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/stl.png", "National", "Central");
                mlbdao.insert(mlbTeam);

                mlbTeam = new MlbTeam("Atlanta Braves", "Braves", "Truist Park", "ATL", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/atl.png", "National", "East");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Miami Marlins", "Marlins", "LoanDepot Park", "MIA", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/mia.png", "National", "East");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("New York Mets", "Mets", "Citi Field", "NYM", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/nym.png", "National", "East");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Philadelphia Phillies", "Phillies", "Citizens Bank Park", "PHI", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/phi.png", "National", "East");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Washington Nationals", "Nationals", "Nationals Park", "WSH", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/wsh.png", "National", "East");
                mlbdao.insert(mlbTeam);

                mlbTeam = new MlbTeam("Arizona Diamondbacks", "Diamondbacks", "Chase Field", "ARI", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/ari.png", "National", "West");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Colorado Rockies", "Rockies", "Coors Field", "COL", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/col.png", "National", "West");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("Los Angeles Dodgers", "Dodgers", "Dodger Stadium", "LAD", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/LAD.png", "National", "West");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("San Diego Padres", "Padres", "Petco Park", "SD", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/sd.png", "National", "West");
                mlbdao.insert(mlbTeam);
                mlbTeam = new MlbTeam("San Francisco Giants", "Giants", "Oracle Park", "SF", "https://a.espncdn.com/i/teamlogos/mlb/500/scoreboard/sf.png", "National", "West");
                mlbdao.insert(mlbTeam);
            });
        }
    };



    public abstract SportsAppDAO sportsAppDAO();

    public abstract UserDAO userDAO();

    public abstract MlbDAO mlbDAO();
}
