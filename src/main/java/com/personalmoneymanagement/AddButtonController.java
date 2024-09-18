package com.personalmoneymanagement;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddButtonController {
    
    @FXML
    private TextField amountField;
    
    @FXML
    private TextField typTextField;

    @FXML DatePicker datePicker;

    @FXML
    private void addIncome(){

        System.out.println("Income added: " + amountField.getText());
    }

    @FXML
    private void addExpense(){

        System.out.println("Expense added: " + amountField.getText());
    }

    @FXML
    private void cancel() throws IOException{
        System.out.println("Action canceled.");

         FXMLLoader loader = new FXMLLoader(getClass().getResource("dashbord.fxml"));
         Parent dashboardScreen = loader.load();

         Stage stage = (Stage) amountField.getScene().getWindow();
    

         Scene scene = new Scene(dashboardScreen);
         stage.setScene(scene);
         

    }
}
