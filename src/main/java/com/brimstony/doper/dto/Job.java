package com.brimstony.doper.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

/**
 * Created by tbrim on 9/8/2016.
 */
public class Job {

    private Integer id;
    private Integer jobSize;
    private Integer progress;
    private Date createdDateTime;
    private Date updatedDateTime;
    @JsonIgnore
    private ScheduledFuture<?> cancelTask;

    public Job(Integer id, Integer jobSize){
        this.id = id;
        this.jobSize = jobSize;
        this.createdDateTime = new Date();
        this.updatedDateTime = this.createdDateTime;
        this.progress = 0;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getJobSize() {
        return jobSize;
    }

    public void setJobSize(Integer jobSize) {
        this.jobSize = jobSize;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public Date getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(Date updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public ScheduledFuture<?> getCancelTask() {
        return cancelTask;
    }

    public void setCancelTask(ScheduledFuture<?> cancelTask) {
        this.cancelTask = cancelTask;
    }
}
