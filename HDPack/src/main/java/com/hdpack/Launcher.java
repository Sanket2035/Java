package com.hdpack;

import com.hdpack.client.JobTrackerClient;

public class Launcher {
    public static void main(String[] args) {
        // This simple main method delegates to the real JavaFX application.
        // It prevents the "JavaFX runtime components are missing" error.
        JobTrackerClient.main(args);
    }
}