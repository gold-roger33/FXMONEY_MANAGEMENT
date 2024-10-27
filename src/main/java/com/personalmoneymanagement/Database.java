package com.personalmoneymanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {
    
    private static final String DB_URL = "jdbc:sqlite:moneymanagement.db";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);

           // System.out.println("Table created"+"\n");
        
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS transactions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "transaction_type TEXT NOT NULL, " + //transation is a reserved word in sqlite
                "amount REAL NOT NULL, " +
                "name TEXT NOT NULL, "+
                "date TEXT NOT NULL)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(createTableSQL)) {
            pstmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    
    public void insertTransaction(String transaction, double amount, String name, String date) {
        String insertSQL = "INSERT INTO transactions(transaction_type, amount, name, date) VALUES(?, ?, ?, ?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, transaction);
            pstmt.setDouble(2, amount);
            pstmt.setString(3, name);
            pstmt.setString(4, date);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void view(){
        String viewSQL= "SELECT id,transaction_type,amount,name,date FROM transactions";

        
    try (
        Connection conn = this.connect();
        Statement stmt = conn.createStatement();
        
        ResultSet rs = stmt.executeQuery(viewSQL)){
            while (rs.next()) {
                System.out.println(rs.getInt("id")+"\t" +
                rs.getString("transaction_type") + "\t" +
                rs.getInt("amount") + "\t" +
                rs.getString("name") + "\t" +
                rs.getString("date")+ "\n");

        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        }
    }

    public List<String> getLastFourTransactions() {
    String sql = "SELECT transaction_type, amount ,name FROM transactions ORDER BY id DESC LIMIT 4";
    List<String> transactions = new ArrayList<>();
    
    try (Connection conn = this.connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        
        while (rs.next()) {
            String transaction = rs.getString("name") + ": " + rs.getDouble("amount");
            transactions.add(transaction);
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    
    return transactions;
}

    public double getTotalBalance(){
        String balance = "SELECT SUM(CASE WHEN transaction_type = 'Income' THEN amount " +
                 "WHEN transaction_type = 'Expense' THEN -amount ELSE 0 END) AS balance FROM transactions";
    
        try (Connection conn = this.connect();
        Statement stmt = conn.createStatement();
    
        ResultSet rs = stmt.executeQuery(balance)) {    
        if (rs.next()) {
            return rs.getDouble("balance");
        }
        } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return 0.0;
        }

    public double getsavings(){
        String savings = "SELECT SUM(CASE WHEN transaction_type = 'Income' THEN amount " +
                        "WHEN transaction_type = 'Expense' THEN -amount ELSE 0 END) AS savings FROM transactions";
        try(Connection conn = this.connect();
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(savings)){

            if (rs.next()) {
                return rs.getDouble("savings");
            }
            } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                return 0.0;

    }        

    public double getIncome(){
        String income = "SELECT SUM(amount) AS income FROM transactions WHERE transaction_type = 'Income'";
        try (Connection conn = this.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(income);
        ){
            if (rs.next()) {
                return rs.getDouble("income");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0.0;
    }

    public double getExpense(){
        String expense = "SELECT SUM(amount) AS expense FROM transactions WHERE transaction_type = 'Expense'";

        try(Connection conn = this.connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(expense)
        ) {
            if (rs.next()) {
                return rs.getDouble("expense");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0.0;
    }
    
    
    public List<Transaction> getFilteredTransactions(String type, String startDate, String endDate, String searchText) {
        List<Transaction> transactions = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT name, amount, transaction_type, date FROM transactions WHERE 1=1");
        
        if (type != null && !type.isEmpty() && !type.equals("All")) {
            query.append(" AND transaction_type = ?");
        }
        if (startDate != null && !startDate.isEmpty()) {
            query.append(" AND date >= ?");
        }
        if (endDate != null && !endDate.isEmpty()) {
            query.append(" AND date <= ?");
        }
        if (searchText != null && !searchText.isEmpty()) {
            query.append(" AND name LIKE ?");
        }


        try (Connection conn = this.connect();
        PreparedStatement pstmt = conn.prepareStatement(query.toString())) {
       
       int paramIndex = 1;

       if (type != null && !type.isEmpty() && !type.equals("All")) {
           pstmt.setString(paramIndex++, type);
       }
       if (startDate != null && !startDate.isEmpty()) {
           pstmt.setString(paramIndex++, startDate);
       }
       if (endDate != null && !endDate.isEmpty()) {
           pstmt.setString(paramIndex++, endDate);
       }
       if (searchText != null && !searchText.isEmpty()) {
           pstmt.setString(paramIndex, "%" + searchText + "%");
       }

       ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            String date = rs.getString("date");
            String name = rs.getString("name");
            double amount = rs.getDouble("amount");
            String transactionType = rs.getString("transaction_type");
            transactions.add(new Transaction(date, name, amount, transactionType));
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
    return transactions;
}
    


    public List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
    
        String query = "SELECT name, amount, transaction_type, date FROM transactions";
        
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                String date = rs.getString("date");
                String name = rs.getString("name");
                double amount = rs.getDouble("amount");
                String type = rs.getString("transaction_type");
                transactions.add(new Transaction(date,name, amount, type));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    
        return transactions;
    }
}


