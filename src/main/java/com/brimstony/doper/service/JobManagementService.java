package com.brimstony.doper.service;

import com.brimstony.doper.controller.data.UpdateJob;
import com.brimstony.doper.dto.Job;
import com.brimstony.doper.dto.Sequence;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.*;

/**
 * Created by tbrim on 9/9/2016.
 */

@Service
public class JobManagementService {

    private ConcurrentHashMap<Integer, Job> jobStorage = new ConcurrentHashMap<>();
    private ScheduledExecutorService evictionExecutor = Executors.newScheduledThreadPool(1);
    private Integer timeout = 60;

    private Sequence jobIdSequence = new Sequence();



    public Job registerJob(Integer jobSize){
        Job job = new Job(jobIdSequence.nextVal(), jobSize);
        jobStorage.putIfAbsent(job.getId(), job);

        ScheduledFuture<?> future = evictionExecutor.schedule(createEvictionTask(job), timeout, TimeUnit.SECONDS);
        job.setCancelTask(future);

        return job;
    }

    public Job updateJob(UpdateJob updateJob){

        return jobStorage.compute(updateJob.getJobId(), (k, job)-> {
            if(job.getProgress() >= job.getJobSize()){
                return job;
            }
            job.setProgress(job.getProgress() + updateJob.getProgress());
            job.setUpdatedDateTime(new Date());
            job.getCancelTask().cancel(true);
            ScheduledFuture<?> future = evictionExecutor.schedule(createEvictionTask(job), timeout, TimeUnit.SECONDS);
            job.setCancelTask(future);
            return job;
        });
    }

    public Collection<Job> listJobs(){
        return jobStorage.values();
    }

    public Job getJob(Integer jobId){
        return jobStorage.get(jobId);
    }

    public void clearAllJobs(){
        jobStorage.clear();
        jobIdSequence.resetSequence();
    }

    private Runnable createEvictionTask(Job job){
        Runnable evictionTask = () -> {
            //System.out.println(String.format("Evicting job %d", job.getId()));
            jobStorage.remove(job.getId());
        };
        return evictionTask;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }
}
