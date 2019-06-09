package edu.isistan.mobileGrid.node;

import edu.isistan.mobileGrid.jobs.Job;

public interface ExecutionManager {
    /**
     * Call when a new jobs arrives
     *
     * @param job
     */
    void addJob(Job job);

    /**
     * get the number of jobs enqueue
     *
     * @return
     */
    int getJobQueueSize();

    /**
     * removes a job
     *
     * @param index
     */

    Job removeJob(int index);

    /**
     * Call when a job is finished
     *
     * @param job
     */
    void onFinishJob(Job job);

    /**
     * Call when a CPU event arrives
     *
     * @param cpuUsage
     */
    void onCPUEvent(double cpuUsage);

    /**
     * Get number of jobs
     *
     * @return
     */
    int getNumberOfJobs();

    /**
     * Get cpu mips
     *
     * @return
     */
    long getMIPS();

    /**
     * Get current cpu usage
     *
     * @return
     */
    double getCPUUsage();

    /**
     * Call when the device shutdown
     */
    void shutdown();

    void onDisconnect();
}
