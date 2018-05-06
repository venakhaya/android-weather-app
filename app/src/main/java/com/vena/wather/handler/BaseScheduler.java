package com.vena.wather.handler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.os.PersistableBundle;

import javax.inject.Inject;

public class BaseScheduler {

    @Inject
    public Context context;
    @Inject
    public JobScheduler scheduler;

    protected PersistableBundle persistableBundle;
    protected JobInfo job;

    /**
     * @Params persistableBundle
     * persistableBundle Extras for job info
     **/
    public BaseScheduler(PersistableBundle persistableBundle) {
        this.persistableBundle = persistableBundle;
    }

    /**
     * Default constructor without params no expected return results,
     * No extras Params
     **/
    public BaseScheduler() {
        persistableBundle = new PersistableBundle();
    }
}
