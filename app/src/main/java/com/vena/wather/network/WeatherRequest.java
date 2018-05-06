package com.vena.wather.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by khaya on 2018/04/16.
 */

public interface WeatherRequest {
    @GET("data/2.5/weather")
    Call<WeatherResponse> getWeather(@Query("lat") double latitude, @Query("lon") double longitude, @Query("appid") String appId);
}
