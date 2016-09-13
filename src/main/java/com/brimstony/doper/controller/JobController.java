package com.brimstony.doper.controller;

import com.brimstony.doper.controller.data.UpdateJob;
import com.brimstony.doper.dto.Job;
import com.brimstony.doper.service.JobManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by tbrim on 9/8/2016.
 */
@RestController
public class JobController {

    @Autowired
    JobManagementService jobManagementService;

    @RequestMapping(value = "/registerJob/", method = RequestMethod.POST)
    public Job register(@RequestBody Integer total) {
        return jobManagementService.registerJob(total);
    }

    @RequestMapping(value = "/updateJob/", method = RequestMethod.POST)
    public Integer update(@RequestBody UpdateJob updateJob) {
        Job updatedJob = jobManagementService.updateJob(updateJob);
        return updatedJob.getProgress();
    }

    @RequestMapping(value = "/job/{jobId}", method = RequestMethod.GET)
    public Job listJob(@PathVariable Integer jobId) {
        return jobManagementService.getJob(jobId);
    }

    @RequestMapping(value = "/jobs/", method = RequestMethod.GET)
    public Collection<Job> listAllJobs() {
        return jobManagementService.listJobs();
    }

}