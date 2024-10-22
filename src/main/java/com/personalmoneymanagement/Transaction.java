package com.personalmoneymanagement;

public class Transaction {
    private String date;
    private String name;
    private double amount;
    private String transactionType; // "Income" or "Expense"

    // Constructor
    public Transaction(String date,String name, double amount, String transactionType) {
        this.date =date;
        this.name = name;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    // Getter methods

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

   
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
