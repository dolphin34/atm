package com.vndirect.atm.entity;

import java.util.List;

public class Account {

    private final String number;

    private final String name;

    private long amount;

    private List<Integer> transactionIds;

    public Account(String number, String name, long amount, List<Integer> transactionIds) {
        this.number = number;
        this.name = name;
        this.amount = amount;
        this.transactionIds = transactionIds;
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

    public List<Integer> getTransactionIds() {
        return transactionIds;
    }

    public void setTransactionIds(List<Integer> transactionIds) {
        this.transactionIds = transactionIds;
    }

    public void addTransactionId(Integer newTransactionId) {
        transactionIds.add(newTransactionId);
    }

}
