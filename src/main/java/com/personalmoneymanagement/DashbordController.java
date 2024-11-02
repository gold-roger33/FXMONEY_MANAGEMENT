package com.personalmoneymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;
import java.io.File;

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
    private ImageView avatarImageView;




    @FXML
    public void initialize(){
        db= new Database();
        db.createTable();
        showRecentTransactions();
        updateAccountOverview();
        loadPiechart();

        avatarImageView.setOnMouseClicked(event -> openImageChooser());
        
        addHoverEffect(accountOverview, "#dfd2d2", 2.5,
                         1);
        addHoverEffect(recentTransactions, "#dfd2d2", 2.5,
                         1);
        addHoverEffect(budgetPieChart, "#dfd2d2", 2.5,
                         1);
        addHoverEffectForPieChartAndAnalysis(budgetPieChart, budgetAnalysis, "#dfd2d2", 
                         2.5, 1);
        
        
    }

    

    private void addHoverEffect(Node node, String strokeColor, double hoverStrokeWidth, double defaultStrokeWidth)
    {
    node.setOnMouseEntered(event -> node.setStyle("-fx-stroke: " + strokeColor + "; -fx-stroke-width: " + hoverStrokeWidth + ";"));
    node.setOnMouseExited(event -> node.setStyle("-fx-stroke: " + strokeColor + "; -fx-stroke-width: " + defaultStrokeWidth + ";"));
    }

    private void addHoverEffectForPieChartAndAnalysis(Node pieChart, Node analysis, String strokeColor, double hoverStrokeWidth, double defaultStrokeWidth) 
    {
        pieChart.setOnMouseEntered(event -> {
            pieChart.setStyle("-fx-stroke: " + strokeColor + "; -fx-stroke-width: " + hoverStrokeWidth + ";");
            analysis.setStyle("-fx-stroke: " + strokeColor + "; -fx-stroke-width: " + hoverStrokeWidth + ";");
        });
        
        pieChart.setOnMouseExited(event -> {
            pieChart.setStyle("-fx-stroke: " + strokeColor + "; -fx-stroke-width: " + defaultStrokeWidth + ";");
            analysis.setStyle("-fx-stroke: " + strokeColor + "; -fx-stroke-width: " + defaultStrokeWidth + ";");
        });
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
    private void handlePlusButtonClick(MouseEvent event) {

        try {
            
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addbutton.fxml"));
        Parent addScreen = loader.load();

        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(addScreen);
        stage.setScene(scene);
        } 
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        
        catch (ClassCastException e) {
            System.err.println(" Loading addbutton.fxml as fallback.");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("addbutton.fxml"));
                Parent addScreen = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(addScreen);
                stage.setScene(scene);
            } catch (IOException ex) {
                System.err.println("Error loading fallback addbutton.fxml: " + ex.getMessage());
            }
        }
        
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
        e.printStackTrace(); 
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


            data.getNode().setOnMouseEntered(event -> data.getNode().setStyle("-fx-opacity: 0.7;"));
            data.getNode().setOnMouseExited(event -> data.getNode().setStyle("-fx-opacity: 1;"));
        });

    }

    private void openImageChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        
        );

         File selectedFile = fileChooser.showOpenDialog(avatarImageView.getScene().getWindow());
        if (selectedFile != null) {
            
            Image avatarImage = new Image(selectedFile.toURI().toString());
            avatarImageView.setImage(avatarImage);

            adjustAvatarImage(avatarImage);

        }

    }
    private void adjustAvatarImage(Image avatarImage) {
        

        double desiredHeight = 50; 
        double desiredWidth = desiredHeight * (avatarImage.getWidth() / avatarImage.getHeight());

        avatarImageView.setFitWidth(desiredWidth);
        avatarImageView.setFitHeight(desiredHeight);
        avatarImageView.setPreserveRatio(true);


        Circle clip = new Circle(desiredHeight / 2);
        clip.setCenterX(desiredHeight / 2);
        clip.setCenterY(desiredHeight / 2);
        avatarImageView.setClip(clip);
    }
}
