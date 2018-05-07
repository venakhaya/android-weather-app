package com.vena.wather.handler;

import android.app.job.JobInfo;
import android.content.ComponentName;
import android.os.PersistableBundle;

import com.vena.wather.events.ResultsReceiverEvent;
import com.vena.wather.services.WeatherJobService;


/**
 * Created by khaya on 2018/04/19.
 */

public class WeatherJobScheduler extends BaseScheduler implements JobScheduler {

    private static final int JOB_ID = 10;
    private static final long JOB_INTERVAL = 1000 * 60 * 60;

    public WeatherJobScheduler(PersistableBundle persistableBundle) {
        super(persistableBundle);
    }

    public WeatherJobScheduler(ResultsReceiverEvent resultsReceiverEvent, PersistableBundle persistableBundle) {
        super(persistableBundle);

        WeatherJobService.setResultsReceiver(resultsReceiverEvent);
    }

    @Override
    public void scheduleJobService() {
        job =
                new JobInfo.Builder(
                        JOB_ID,
                        new ComponentName(context, WeatherJobService.class))
                        .setPeriodic(JOB_INTERVAL)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setExtras(persistableBundle)
                        .build();
        scheduler.schedule(job);
    }


}
