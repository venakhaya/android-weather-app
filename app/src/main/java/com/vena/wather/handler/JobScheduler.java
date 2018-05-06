package com.vena.wather.handler;

/**
 * Created by khaya on 2018/04/20.
 */

/**
 * JobScheduler Interface class used to schedule jobs.
 * Implement this class in all sub classes of BaseScheduler.
 **/
public interface JobScheduler {
    /**
     * Schedules job according to its requirements
     **/
    void scheduleJobService();
}
