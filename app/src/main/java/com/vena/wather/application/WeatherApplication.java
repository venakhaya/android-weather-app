package com.vena.wather.application;

import android.app.Application;

import com.vena.wather.R;
import com.vena.wather.modules.AppComponent;
import com.vena.wather.modules.AppModule;
import com.vena.wather.modules.DaggerAppComponent;
import com.vena.wather.modules.NetModule;


public class WeatherApplication extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(new AppModule(this))
                .netModule(new NetModule(getString(R.string.host))).build();
        appComponent.inject(this);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
