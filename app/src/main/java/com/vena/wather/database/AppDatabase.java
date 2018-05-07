package com.vena.wather.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.vena.wather.model.Weather;

/**
 * Created by khaya on 2018/04/16.
 */
@Database(entities = {Weather.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;

    public abstract WeatherDao weatherDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (appDatabase == null) {
            appDatabase =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "weather-database").build();
        }
        return appDatabase;
    }

    public void destroyInstance() {
        appDatabase = null;
    }
}
