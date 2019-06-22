package com.cashu.github.data.database;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.cashu.github.data.model.GithubEntity;

@Database(entities = GithubEntity.class, version = 1)
public abstract class GithubDatabase extends RoomDatabase {

    private static volatile GithubDatabase INSTANCE;

    public abstract GithubDao githubDao();

    public static GithubDatabase getDatabase(final Context context) {
        synchronized (GithubDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        GithubDatabase.class, "github.db")
                        .build();
            }
        }
        return INSTANCE;
    }

}
