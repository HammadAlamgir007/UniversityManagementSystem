package com.test.firstproject.batch;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatchScheduler {

    private final JobOperator jobOperator;
    private final Job studentJob;

    public BatchScheduler(
            JobOperator jobOperator,
            @Qualifier("studentJob") Job studentJob) {

        this.jobOperator = jobOperator;
        this.studentJob = studentJob;
    }

    @Scheduled(fixedRate = 800000000)
    public void runJob() throws Exception {

        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        // In Spring Batch 6.0, use JobOperator.start(Job, JobParameters)
        jobOperator.start(studentJob, params);
    }
}