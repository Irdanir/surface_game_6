package ru.pavlenty.surfacegame2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public long userId;
    public String username;
    public String password;

    public long getId() {
        return this.userId;
    }
}
