package com.vndirect.atm.model;

import java.util.List;

public class AccountModel {

    private final String number;

    private final String name;

    private long amount;

    private final List<TransactionModel> transactions;

    public AccountModel(String number, String name, long amount, List<TransactionModel> transactions) {
        this.number = number;
        this.name = name;
        this.amount = amount;
        this.transactions = transactions;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public List<TransactionModel> getTransactions() {
        return transactions;
    }

    public void addTransaction(TransactionModel transaction) {
        transactions.add(transaction);
    }
}
