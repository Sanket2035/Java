package com.hdpack.app.controller;

import com.hdpack.app.model.Job;
import com.hdpack.app.repository.JobRepository;
import com.hdpack.app.service.FileStorageService;

import tools.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/jobs")
//@CrossOrigin(origins = "*")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private FileStorageService fileStorageService;

    // We use ObjectMapper to convert the JSON String back to a Java Object manually
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/create")
    public Job createJob(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jobData") String jobDataJson // The Job JSON as a String
    ) throws IOException {

        // 1. Convert the JSON String to the Job Object
        Job job = objectMapper.readValue(jobDataJson, Job.class);

        // 2. Save the File to the 2TB Drive
        String imagePath = fileStorageService.saveFile(file);

        // 3. AUTOMATICALLY set the path in the Job object
        job.setImagePath(imagePath);

        // 4. Save the Job to MongoDB
        return jobRepository.save(job);
    }
}