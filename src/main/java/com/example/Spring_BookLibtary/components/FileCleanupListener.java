package com.example.Spring_BookLibtary.components;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileCleanupListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        JobExecutionListener.super.beforeJob(jobExecution);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String filePath = jobExecution.getJobParameters().getString("filePath");

        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
            Files.deleteIfExists(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        JobExecutionListener.super.afterJob(jobExecution);
    }
}
