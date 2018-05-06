package com.vena.wather.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Main implements Serializable {

    @SerializedName("temp")
    float temp;
    @SerializedName("pressure")
    float pressure;
    @SerializedName("humidity")
    float humidity;
    @SerializedName("temp_min")
    float minTemp;
    @SerializedName("temp_max")
    float maxTemp;
    @SerializedName("sea_level")
    float seaLevel;
    @SerializedName("grnd_level")
    float groundLevel;


    public float getTempreture() {
        return maxTemp;
    }

    public void setTempreture(float temp) {
        this.maxTemp = temp;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    public float getMaximumTempreture() {
        return maxTemp;
    }

    public void setMaximumTempreture(float maxTemp) {
        this.maxTemp = maxTemp;
    }

    public float getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(float seaLevel) {
        this.seaLevel = seaLevel;
    }

    public float getGroundLevel() {
        return groundLevel;
    }

    public void setGroundLevel(float groundLevel) {
        this.groundLevel = groundLevel;
    }
}
