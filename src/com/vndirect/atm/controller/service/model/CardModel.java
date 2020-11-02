package com.vndirect.atm.controller.service.model;

public class CardModel {

    private final String number;
    private final String name;
    private final String accountNumber;

    public CardModel(String number, String name, String accountNumber) {
        this.number = number;
        this.name = name;
        this.accountNumber = accountNumber;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}
