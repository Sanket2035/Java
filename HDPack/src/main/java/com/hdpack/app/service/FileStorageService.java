package com.hdpack.app.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    // Secret: Store this path in application.yml so you can change it later
    // For now, let's point it to your 2TB drive
    private final Path rootLocation = Paths.get("D:/MongoDB_Data/Job_Templates");

    public FileStorageService() {
        try {
            // Automatically create the folder if it doesn't exist
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage location", e);
        }
    }

    public String saveFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }

            // Secret: Never use the original filename (e.g., "label.jpg").
            // If two customers send "label.jpg", one will overwrite the other.
            // Use UUID to generate a random, unique name.
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + extension;

            // Copy the file to the 2TB drive
            Path destinationFile = this.rootLocation.resolve(newFilename);
            Files.copy(file.getInputStream(), destinationFile);

            // Return the RELATIVE path (easier for the frontend to load later)
            return "/job-templates/" + newFilename;

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }
}