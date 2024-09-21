package com.personalmoneymanagement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;

public class DashbordController {
    
    @FXML
    private Text transaction1;
    @FXML
    private Text transaction2;
    @FXML
    private Text transaction3;
    @FXML
    private Text transaction4;
    

    @FXML
    private Text totalbalance;

    @FXML
    private Text savings;

    @FXML
    private Text checking;

    private Database db;

    @FXML
    public void initialize(){
        db= new Database();
        db.createTable();
        showRecentTransactions();
        updateAccountOverview();
    }

    private void showRecentTransactions() {
    List<String> transactions = db.getLastFourTransactions();
    
    // Display transactions in the UI
    if (transactions.size() >= 1) transaction1.setText(transactions.get(0));
    if (transactions.size() >= 2) transaction2.setText(transactions.get(1));
    if (transactions.size() >= 3) transaction3.setText(transactions.get(2));
    if (transactions.size() >= 4) transaction4.setText(transactions.get(3));
    }

    private void updateAccountOverview(){

        double TotalBalanceValue = db.getTotalBalance();

        totalbalance.setText("₹ "+ String.format("%.2f", TotalBalanceValue));
        //System.out.println("TOTAL BALANCE IS \n"+totalbalance);
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
