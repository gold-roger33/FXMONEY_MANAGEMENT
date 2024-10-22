package com.personalmoneymanagement;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SettingsController {

    @FXML
    private void dashbordButtonClicked(MouseEvent event) {
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashbord.fxml"));
        Parent dashboardScreen = loader.load();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(dashboardScreen);
        stage.setScene(scene);
        
    }catch (IOException e) {
        System.err.println("Error loading settings page: " + e.getMessage());
        e.printStackTrace(); 
        }
    }
    
    
}
