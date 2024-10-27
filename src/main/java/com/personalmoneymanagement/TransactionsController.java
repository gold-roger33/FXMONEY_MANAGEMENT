package com.personalmoneymanagement;

import java.io.IOException;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class TransactionsController {

    @FXML
    private ComboBox<String> categoryfilter;
    @FXML 
    private DatePicker startDate;
    @FXML 
    private DatePicker endDate;
    @FXML
    private TextField searchField;


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

        categoryfilter.setItems(FXCollections.observableArrayList("All","Income", "Expense"));
        categoryfilter.setValue("All");

        loadTransactions();

        categoryfilter.setOnAction(event -> filterTransactions());
        startDate.valueProperty().addListener((observable, oldValue, newValue) -> filterTransactions());
        endDate.valueProperty().addListener((observable,oldValue,newValue) -> filterTransactions());
        searchField.textProperty().addListener((observable,oldValue,newValue) -> filterTransactions());
    }
    @FXML
    private void filterTransactions(){
        
        String category = categoryfilter.getValue();
        String startDate = this.startDate.getValue() != null ? this.startDate.getValue().toString() : "";
        String endDate = this.endDate.getValue() != null ? this.endDate.getValue().toString() : "";
        String searchText = searchField.getText();

        List <Transaction> filteredTransactions = database.getFilteredTransactions(category, startDate, endDate, searchText);;

        ObservableList<Transaction> observableTransactions = FXCollections.observableArrayList(filteredTransactions);
        transactionsTable.setItems(observableTransactions);

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
