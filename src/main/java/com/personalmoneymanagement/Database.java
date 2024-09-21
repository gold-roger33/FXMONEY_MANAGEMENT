package com.personalmoneymanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    
    private static final String DB_URL = "jdbc:sqlite:moneymanagement.db";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);

            System.out.println("Table created");
        
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

   
}
