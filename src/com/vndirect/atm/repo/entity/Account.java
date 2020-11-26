package com.vndirect.atm.repo.entity;

import java.util.List;

public class Account {

    private final String number;
    private final String name;
    private long amount;
    private List<Integer> listTransactionId;

    public Account(String number, String name, long amount, List<Integer> listTransactionId) {
        this.number = number;
        this.name = name;
        this.amount = amount;
        this.listTransactionId = listTransactionId;
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

    public List<Integer> getListTransactionsId() {
        return listTransactionId;
    }

    public void setListTransactionId(List<Integer> listTransactionId) {
        this.listTransactionId = listTransactionId;
    }

    public List<Integer> addTransactionsId(Integer newTransactionId) {
        listTransactionId.add(newTransactionId);
        return listTransactionId;
    }

}
