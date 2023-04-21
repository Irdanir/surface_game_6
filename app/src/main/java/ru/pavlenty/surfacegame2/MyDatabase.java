package ru.pavlenty.surfacegame2;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.pavlenty.surfacegame2.HighScore;
import ru.pavlenty.surfacegame2.HighScoreDao;
import ru.pavlenty.surfacegame2.User;
import ru.pavlenty.surfacegame2.UserDao;

@Database(entities = {User.class, HighScore.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    private static final String DB_NAME = "my_db";
    private static MyDatabase instance;

    public static synchronized MyDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract UserDao userDao();
    public abstract HighScoreDao highScoreDao();
}

