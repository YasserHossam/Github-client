package com.cashu.github.view;

import android.app.Application;
import androidx.room.Room;
import androidx.room.RoomDatabase;

public class GithubApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

//        Room.inMemoryDatabaseBuilder(
//                this, RoomDatabase.class)
//                .fallbackToDestructiveMigration()
//                .build();
    }
}
