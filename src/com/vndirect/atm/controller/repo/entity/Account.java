package com.vndirect.atm.controller.repo.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Account {

    private final String number;
    private final String name;
    private long amount;
    private final List<Integer> listTransactionId;

    public Account(String number, String name, long amount, Integer... listTransactionId) {
        this.number = number;
        this.name = name;
        this.amount = amount;
        this.listTransactionId = new ArrayList<>();
        this.listTransactionId.addAll(Arrays.asList(listTransactionId));
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

    public void addTransaction(int transactionId) {
        listTransactionId.add(transactionId);
    }

    public List<Integer> getListTransactionsId() {
        return listTransactionId;
    }
}
