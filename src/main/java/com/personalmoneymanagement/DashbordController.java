package com.personalmoneymanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


import java.io.IOException;

public class DashbordController {
    

    public void initialise(){

    }

    public void refeshandupdate(){

    }

    @FXML
    private void handlePlusButtonClick(MouseEvent event) throws IOException {
        // Load the FXML file for the add screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addbutton.fxml"));
        Parent addScreen = loader.load();

        // Get the current stage
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        // Set the new scene
        Scene scene = new Scene(addScreen);
        stage.setScene(scene);
        
    }
}
