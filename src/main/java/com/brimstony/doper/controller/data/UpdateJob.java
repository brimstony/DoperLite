package com.brimstony.doper.controller.data;

/**
 * Created by tbrim on 9/9/2016.
 */
public class UpdateJob {
    private Integer jobId;
    private Integer progress;

    public UpdateJob(){}

    public UpdateJob(Integer jobId, Integer progress){
        this.jobId = jobId;
        this.progress = progress;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
}
