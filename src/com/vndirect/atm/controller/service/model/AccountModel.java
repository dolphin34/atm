package com.vndirect.atm.controller.service.model;

import com.vndirect.atm.controller.repo.entity.Transaction;

import java.util.List;

public class AccountModel {

    private final String number;
    private final String name;
    private final long amount;
    private final List<Transaction> listTransaction;

    public AccountModel(String number, String name, long amount, List<Transaction> listTransaction) {
        this.number = number;
        this.name = name;
        this.amount = amount;
        this.listTransaction = listTransaction;
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

    public List<Transaction> getListTransaction() {
        return listTransaction;
    }
}
