package ru.pavlenty.surfacegame2;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;
import androidx.room.Query;

@Entity(tableName = "high_scores")
public class HighScore {
    @PrimaryKey(autoGenerate = true)
    public long userId;
    public int score;
}

