package com.hdpack.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class JobTrackerClient extends Application {

    @Override
    public void start(Stage stage) {
        try {
            // 1. Load the Main Layout (Sidebar + Center Content)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hdpack/client/views/MainLayout.fxml"));
            Parent root = loader.load();

            // 2. Set up the Scene
            Scene scene = new Scene(root, 1280, 800);

            // 3. Configure the Stage (Window)
            stage.setTitle("StickerPrint Pro - Enterprise Management");
            stage.setScene(scene);
            stage.setMinWidth(1024);
            stage.setMinHeight(768);

            // 4. Add an Application Icon (Place an 'icon.png' in your resources/images folder)
            // stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/hdpack/client/images/icon.png")));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Fatal Error: Could not load MainLayout.fxml");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}