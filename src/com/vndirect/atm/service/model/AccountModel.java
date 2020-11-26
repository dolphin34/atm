package com.vndirect.atm.service.model;

import java.util.List;

public class AccountModel {

    private final String number;
    private final String name;
    private final long amount;
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

    public List<TransactionModel> getListTransactionModel() {
        return listTransactionModel;
    }
}
