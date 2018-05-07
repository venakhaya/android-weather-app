package com.vena.wather.handler;

import android.app.job.JobInfo;
import android.content.ComponentName;

import com.vena.wather.events.ResultsReceiverEvent;
import com.vena.wather.services.DataAccessService;
import com.vena.wather.services.WeatherJobService;

public class DaoJobScheduler extends BaseScheduler implements JobScheduler {
    private static final int JOB_ID = 11;


    public DaoJobScheduler(ResultsReceiverEvent resultsReceiverEvent) {
        WeatherJobService.setResultsReceiver(resultsReceiverEvent);
    }

    @Override
    public void scheduleJobService() {
        job =
                new JobInfo.Builder(
                        JOB_ID,
                        new ComponentName(context, DataAccessService.class))
                        .setExtras(persistableBundle)
                        .build();
        scheduler.schedule(job);

    }
}
