package com.vena.wather.modules;

import com.vena.wather.network.WeatherRequest;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {
    String baseUrl;

    public NetModule(String mBaseUrl) {
        this.baseUrl = mBaseUrl;
    }


    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public WeatherRequest getWeatherRequest() {
        return provideRetrofit().create(WeatherRequest.class);
    }

}
