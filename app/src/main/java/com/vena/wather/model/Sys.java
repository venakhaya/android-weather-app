package com.vena.wather.model;

import com.google.gson.annotations.SerializedName;

public class Sys {
    @SerializedName("message")
    private float message;
    @SerializedName("message")
    private String country;
    @SerializedName("message")
    private long sunrise;
    @SerializedName("message")
    private long sunset;

    public float getMessage() {
        return message;
    }

    public void setMessage(float message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
}
