package com.vena.wather.services;

import android.app.job.JobParameters;
import android.os.AsyncTask;
import android.util.Log;

import com.vena.wather.R;
import com.vena.wather.events.ResultsReceiverEvent;
import com.vena.wather.model.Weather;
import com.vena.wather.network.WeatherResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by khaya on 2018/04/16.
 */

public class WeatherJobService extends BaseService {
    private static final String TAG = WeatherJobService.class.getSimpleName();
    private WeatherResponse weatherResponse;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public boolean onStartJob(JobParameters params) {
        Log.e(TAG, "Request API");
        Call<WeatherResponse> weatherResponseCall = weatherRequest.getWeather(params.getExtras().getDouble("lat"),
                params.getExtras().getDouble("lon"), context.getString(R.string.appid));
        weatherResponseCall.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {

                Log.e(TAG, "getting response");
                weatherResponse = response.body();

                if (resultsReceiverEvent != null) {
                    if (weatherResponse == null) {
                        Log.e(TAG, "Response  null");
                        resultsReceiverEvent.onFailed(context.getString(R.string.api_fail));
                        return;
                    }
                    Log.e(TAG, "Response not null");
                    resultsReceiverEvent.onSuccess(weatherResponse);
                    Log.e(TAG, "Updated results response");
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            for (Weather weather : weatherResponse.getWeatherList())
                                appDatabase.weatherDao().update(weather);
                            return null;
                        }
                    };

                } else {
                    Log.e(TAG, "resultsReceiverEvent  null");
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                if (resultsReceiverEvent != null) {
                    resultsReceiverEvent.onFailed(t.getMessage());
                }
            }
        });

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        appDatabase.destroyInstance();
    }

    public static void setResultsReceiver(ResultsReceiverEvent resultsReceiverEvent) {
        WeatherJobService.resultsReceiverEvent = resultsReceiverEvent;
    }


}
