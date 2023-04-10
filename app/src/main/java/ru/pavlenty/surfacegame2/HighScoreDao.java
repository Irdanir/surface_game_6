package ru.pavlenty.surfacegame2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HighScoreDao {
    @Query("SELECT * FROM high_scores WHERE userId = :userId ORDER BY score DESC LIMIT 1")
    HighScore getHighScore(long userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveHighScore(HighScore highScore);
}
