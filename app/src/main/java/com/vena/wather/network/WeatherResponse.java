package com.vena.wather.network;

import com.google.gson.annotations.SerializedName;
import com.vena.wather.model.Weather;

import java.util.ArrayList;


/**
 * Created by khaya on 2018/04/19.
 */

public class WeatherResponse {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("cod")
    private int cod;
    @SerializedName("dt")
    private long date;
    @SerializedName("base")
    private String base;
    @SerializedName("weather")
    private ArrayList<Weather> weatherList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public ArrayList<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(ArrayList<Weather> weatherList) {
        this.weatherList = weatherList;
    }
}
