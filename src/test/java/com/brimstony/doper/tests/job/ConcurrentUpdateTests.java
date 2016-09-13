package com.brimstony.doper.tests.job;

import com.brimstony.doper.controller.data.UpdateJob;
import com.brimstony.doper.dto.Job;
import com.brimstony.doper.service.JobManagementService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

/**
 * Created by tbrim on 9/9/2016.
 */
public class ConcurrentUpdateTests {
    JobManagementService jobManagementService = new JobManagementService();
    @Before
    public void setUp() {
        jobManagementService.clearAllJobs();
    }

    @Test
    public void concurrentUpdateTests() throws Exception {
        jobManagementService.registerJob(10000);

        ExecutorService executor = Executors.newFixedThreadPool(50);
        Collection<Callable<Integer>> taskList = new ArrayList<>();

        //Create 5000 tasks (to be executed in 50 concurrent threads) to update job progress
        for (int i = 0; i < 5000; i++) {
            taskList.add(createUpdateJobTask(jobManagementService));
        }
        executor.invokeAll(taskList).forEach((task) -> {
            try {
                task.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        executor.shutdown();
        //Wait until all jobs are complete
        while (!executor.isTerminated()) {}

        Job job = jobManagementService.getJob(1);

        //Verify that job has progressed to 5000
        assertEquals(5000, job.getProgress().intValue());
    }

    private Callable<Integer> createUpdateJobTask(JobManagementService jobManagementService){
        Callable updateJobTask = () -> {
            UpdateJob updateJob = new UpdateJob(1,1);
            Job job = jobManagementService.updateJob(updateJob);
            return job.getProgress();
        };
        return updateJobTask;
    }
}

