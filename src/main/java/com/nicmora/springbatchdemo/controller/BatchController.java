package com.nicmora.springbatchdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/batch")
@RequiredArgsConstructor
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job job;

    @GetMapping
    public ResponseEntity<String> batch() {
        try {
            jobLauncher.run(job, new JobParametersBuilder().addDate("date", new Date()).toJobParameters());
            return ResponseEntity.ok("Job completed");
        } catch (JobExecutionException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
