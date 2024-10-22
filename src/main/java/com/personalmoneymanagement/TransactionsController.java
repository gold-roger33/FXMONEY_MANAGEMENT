package com.personalmoneymanagement;

import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;



public class TransactionsController {

    @FXML
    private TableView<Transaction> transactionsTable;
    @FXML
    private TableColumn<Transaction, String> dateColumn;
    @FXML
    private TableColumn<Transaction, String> nameColumn;
    @FXML
    private TableColumn<Transaction, String> transactiontypeColumn;
    @FXML
    private TableColumn<Transaction, Double> amountColumn;


    private Database database = new Database();

    @FXML
    public void initialize() {

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        transactiontypeColumn.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));

        loadTransactions();
    }

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
        e.printStackTrace(); // This will help in identifying the issue
        }
    }

    private void loadTransactions(){

        List<Transaction> transactions = database.getTransactions();
        ObservableList<Transaction> observableTransactions = FXCollections.observableArrayList(transactions);
        transactionsTable.setItems(observableTransactions); 
    }
}
