package com.vena.wather.handler;

/**
 * JobHandler This class is to execute all background processes including Async and sync tasks
 * It is an adapter between business logic and ui.
 **/
public class JobHandler {

    public void execute(JobScheduler jobScheduler) {
        jobScheduler.scheduleJobService();
    }
}
