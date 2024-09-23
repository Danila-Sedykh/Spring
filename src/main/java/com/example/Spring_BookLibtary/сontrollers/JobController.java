package com.example.Spring_BookLibtary.сontrollers;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
public class JobController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Async
    @PostMapping("/uploadExcel")
    public ResponseEntity<String> startBatchJob(@RequestParam("file") MultipartFile file, @RequestHeader("Authorization") String token) throws Exception{
        try {

            Path tempDir = Files.createTempDirectory("batch_uploads_");


            Path tempFile = tempDir.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("filePath", tempFile.toString())
                    .addLong("time", System.currentTimeMillis())
                    .addString("token", token)
                    .toJobParameters();

            jobLauncher.run(job, jobParameters);

            messagingTemplate.convertAndSend("/topic/status", "Файл загружен, начата обработка.");

            return ResponseEntity.ok("Файл загружен, начата обработка");

        } catch (Exception e) {

            messagingTemplate.convertAndSend("/topic/status", "Ошибка при загрузке файла: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при загрузке файла: " + e.getMessage());
        }
    }
}
