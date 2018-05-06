package com.vena.wather.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.vena.wather.model.Weather;

import java.util.List;

/**
 * Created by khaya on 2018/04/16.
 */
@Dao
public interface WeatherDao {
    @Query("SELECT * FROM Weather")
    List<Weather> getAll();

    @Query("SELECT * FROM Weather where id LIKE  :id")
    Weather findById(String id);

    @Query("SELECT COUNT(*) from Weather")
    int count();

    @Insert
    void insertAll(Weather... weathers);

    @Update
    void update(Weather weather);

    @Delete
    void delete(Weather weather);
}
