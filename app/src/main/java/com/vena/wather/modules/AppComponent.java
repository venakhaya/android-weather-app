package com.vena.wather.modules;

import com.vena.wather.application.WeatherApplication;
import com.vena.wather.handler.BaseScheduler;
import com.vena.wather.services.BaseService;
import com.vena.wather.ui.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, NetModule.class})
@Singleton
public interface AppComponent {
    void inject(WeatherApplication weatherApplication);

    void inject(BaseActivity baseActivity);

    void inject(BaseService baseService);

    void inject(BaseScheduler baseScheduler);
}
