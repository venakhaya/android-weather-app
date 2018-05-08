package com.vena.wather.modules;

import android.app.job.JobScheduler;
import android.content.Context;
import android.location.LocationManager;

import com.vena.wather.application.WeatherApplication;
import com.vena.wather.database.AppDatabase;
import com.vena.wather.handler.JobHandler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private WeatherApplication weatherApplication;

    public AppModule(WeatherApplication weatherApplication) {
        this.weatherApplication = weatherApplication;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return weatherApplication;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context) {
        return AppDatabase.getAppDatabase(context);
    }

    @Provides
    @Singleton
    public JobHandler provideHandler() {
        return new JobHandler();
    }

    @Provides
    @Singleton
    public JobScheduler provideScheduler() {
        return (JobScheduler) provideApplicationContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    @Provides
    public LocationManager provideLocationManager() {
        return (LocationManager) provideApplicationContext().getSystemService(Context.LOCATION_SERVICE);
    }

}
