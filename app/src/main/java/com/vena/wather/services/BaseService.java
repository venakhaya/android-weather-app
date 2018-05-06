package com.vena.wather.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;

import com.vena.wather.application.WeatherApplication;
import com.vena.wather.events.ResultsReceiverEvent;
import com.vena.wather.database.AppDatabase;
import com.vena.wather.network.WeatherRequest;

import javax.inject.Inject;

public class BaseService extends JobService {

    public static ResultsReceiverEvent resultsReceiverEvent;

    @Inject
    public AppDatabase appDatabase;
    @Inject
    public WeatherRequest weatherRequest;
    @Inject
    public Context context;



    @Override
    public void onCreate() {
        super.onCreate();
        ((WeatherApplication) getApplication()).getAppComponent().inject(this);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
