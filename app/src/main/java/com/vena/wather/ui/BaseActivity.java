package com.vena.wather.ui;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vena.wather.application.WeatherApplication;
import com.vena.wather.handler.JobHandler;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity {
    @Inject
    public Context context;
    @Inject
    public JobHandler jobHandler;
    @Inject
    public LocationManager locationManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((WeatherApplication) getApplication()).getAppComponent().inject(this);
    }

}
