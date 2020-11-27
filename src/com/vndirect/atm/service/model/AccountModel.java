package com.vndirect.atm.service.model;

import java.util.List;

public class AccountModel {

    private final String number;
    private final String name;
    private long amount;
    private final List<TransactionModel> listTransactionModel;

    public AccountModel(String number, String name, long amount, List<TransactionModel> listTransactionModel) {
        this.number = number;
        this.name = name;
        this.amount = amount;
        this.listTransactionModel = listTransactionModel;
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

    public List<TransactionModel> getListTransactionModel() {
        return listTransactionModel;
    }

    public void addTransaction(TransactionModel transactionModel) {
        listTransactionModel.add(transactionModel);
    }
}
