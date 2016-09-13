package com.brimstony.doper.tests.job;

import com.brimstony.doper.controller.data.UpdateJob;
import com.brimstony.doper.dto.Job;
import com.brimstony.doper.service.JobManagementService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by tbrim on 9/12/2016.
 */
public class EvictionTests {

    JobManagementService jobManagementService = new JobManagementService();

    @Before
    public void setUp() {
        jobManagementService.setTimeout(5);
        jobManagementService.clearAllJobs();

    }

    @Test
    public void testEviction() throws Exception{
        jobManagementService.registerJob(10000);
        Thread.sleep(6000);
        Job job = jobManagementService.getJob(1);
        assertEquals(job, null);
    }

    @Test
    public void testDelayedEviction() throws Exception{
        Job registeredJob = jobManagementService.registerJob(10000);
        Thread.sleep(3000);

        //Update job to reset eviction timer
        UpdateJob updateJob = new UpdateJob(registeredJob.getId(), 1);
        jobManagementService.updateJob(updateJob);


        Thread.sleep(3000);
        //6 seconds after job was registered, 3 after last update. Job should still be valid.
        Job job = jobManagementService.getJob(registeredJob.getId());
        assertNotNull(job);

        //9 seconds after job was registered, 6 after last update. Job should be removed.
        Thread.sleep(3000);
        job = jobManagementService.getJob(registeredJob.getId());
        assertNull(job);
    }
}
