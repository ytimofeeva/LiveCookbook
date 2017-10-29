package com.example.julia.livecookbook.data.storage.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.julia.livecookbook.data.storage.entities.ReceipeHeaderDB;
import com.example.julia.livecookbook.data.storage.entities.ReceipeStepDB;

/**
 * Created by julia on 29.10.17.
 */
@Database(version = 1, entities = {ReceipeHeaderDB.class, ReceipeStepDB.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract ReceipeDao receipeDao();

    private static AppDatabase database;

    public static AppDatabase getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context, AppDatabase.class, "cookbookDb")
                    .build();
        }
        return database;
    }

}
