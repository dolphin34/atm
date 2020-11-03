package com.vndirect.atm.controller.service.model;

public class CardModel {

    private final String number;
    private final String name;
    private final String accountNumber;
    private final boolean active;

    public CardModel(String number, String name, String accountNumber, boolean active) {
        this.number = number;
        this.name = name;
        this.accountNumber = accountNumber;
        this.active = active;
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

    public boolean isActive() {
        return active;
    }
}
