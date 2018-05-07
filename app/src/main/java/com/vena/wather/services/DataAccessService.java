package com.vena.wather.services;

import android.app.job.JobParameters;

import com.vena.wather.R;
import com.vena.wather.model.Weather;

import java.util.List;

public class DataAccessService extends BaseService {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        List<Weather> weathers = appDatabase.weatherDao().getAll();
        if (weathers != null && weathers.size() > 0) {
            resultsReceiverEvent.onSuccess(weathers);
        } else {
            resultsReceiverEvent.onFailed(context.getString(R.string.api_fail));
        }
        return super.onStartJob(params);
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return super.onStopJob(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        appDatabase = null;
    }
}
