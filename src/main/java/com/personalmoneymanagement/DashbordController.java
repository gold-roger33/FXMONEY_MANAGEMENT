package com.personalmoneymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;

public class DashbordController {

    
    private Database db;
    
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
    private Text expenses;

    @FXML 
    private PieChart budgetPieChart;



    @FXML
    public void initialize(){
        db= new Database();
        db.createTable();
        showRecentTransactions();
        updateAccountOverview();
       // loadPiechart();
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
        double SavingsValue = db.getsavings();
        double ExpenseValue = db.getExpense();

        totalbalance.setText("₹ "+ String.format("%.2f", TotalBalanceValue));
        //System.out.println("TOTAL BALANCE IS \n"+TotalBalanceValue);

        savings.setText("₹"+ String.format("%.2f", SavingsValue));
        //System.out.println("TOTAL SAVINGS IS \t"+SavingsValue);
    
        expenses.setText("₹"+ String.format("-%.2f", ExpenseValue));
        //System.out.println("TOTAL SAVINGS IS \t"+  "-"+ExpenseValue);
    
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
    @FXML
    private void settingsButtonClicked(MouseEvent event) {
        try{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("settingspage.fxml"));
        Parent settingsScreen = loader.load();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(settingsScreen);
        stage.setScene(scene);
        
    }catch (IOException e) {
        System.err.println("Error loading settings page: " + e.getMessage());
        e.printStackTrace(); // This will help in identifying the issue
    }
}


     private void loadPiechart(){

        List<Transaction> transactions = db.getTransactions(); // Fetch all transactions

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Transaction transaction : transactions) {
           
            
            pieChartData.add(new PieChart.Data(transaction.getName() + " (" + transaction.getTransactionType() + ")", transaction.getAmount()));
            String label = transaction.getName() + " (" + transaction.getTransactionType() + ")";
            //System.out.println("Adding to chart: " + label + " -> Amount: " + transaction.getAmount());
            
          
        }

        budgetPieChart.setData(pieChartData);
       // budgetPieChart.setMinSize(400, 400); 

    }
   
}
