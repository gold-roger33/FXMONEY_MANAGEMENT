package com.personalmoneymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
    private Rectangle accountOverview;

    @FXML 
    private Rectangle recentTransactions;

    @FXML
    private Rectangle budgetAnalysis;




    @FXML
    public void initialize(){
        db= new Database();
        db.createTable();
        showRecentTransactions();
        updateAccountOverview();
        loadPiechart();

        accountOverview.setOnMouseEntered(event -> 
        accountOverview.setStyle("-fx-stroke: #dfd2d2; -fx-stroke-width: 2.5;"));
        
        accountOverview.setOnMouseExited(event -> 
        accountOverview.setStyle("-fx-stroke: #dfd2d2; -fx-stroke-width: 1;"));

        recentTransactions.setOnMouseEntered(event ->
        recentTransactions.setStyle("-fx-stroke: #dfd2d2; -fx-stroke-width: 2.5;"));      

        recentTransactions.setOnMouseExited(event ->
        recentTransactions.setStyle("-fx-stroke: #dfd2d2; -fx-stroke-width: 1;")); 
        
        budgetPieChart.setOnMouseEntered(event ->
        budgetAnalysis.setStyle("-fx-stroke: #dfd2d2; -fx-stroke-width: 2.5;"));      

        budgetPieChart.setOnMouseExited(event ->
        budgetAnalysis.setStyle("-fx-stroke: #dfd2d2; -fx-stroke-width: 1;")); 


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

    
    @FXML
    private void transactionsButtonClicked(MouseEvent event) {
        try{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("transactionspage.fxml"));
        Parent settingsScreen = loader.load();

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(settingsScreen);
        stage.setScene(scene);
        
    }catch (IOException e) {
        System.err.println("Error loading settings page: " + e.getMessage());
        e.printStackTrace(); 
        }
    }




     private void loadPiechart(){

        List<Transaction> transactions = db.getTransactions(); // Fetch all transactions

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Transaction transaction : transactions) {
           
            PieChart.Data data = new PieChart.Data(transaction.getName() + "  " +
             transaction.getTransactionType() +" " , 
             transaction.getAmount());
            
             System.out.println(data);
            pieChartData.add(data);
            
          
        }

        budgetPieChart.setData(pieChartData);
        budgetPieChart.setLabelsVisible(false);
        budgetPieChart.setLegendVisible(false);
         

        budgetPieChart.getData().forEach(data -> {
          //  String transactionType = data.getName().contains("Income") ? "Income" : "Expense";
            String label = data.getName() +  ": ₹" + String.format("%.2f", data.getPieValue());
    
           
            Tooltip tooltip = new Tooltip(label);
            Tooltip.install(data.getNode(), tooltip);


            
    
            // Add hover effect
            data.getNode().setOnMouseEntered(event -> data.getNode().setStyle("-fx-opacity: 0.7;"));
            data.getNode().setOnMouseExited(event -> data.getNode().setStyle("-fx-opacity: 1;"));
        });

    }
   
}
