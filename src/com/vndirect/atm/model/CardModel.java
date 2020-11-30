package com.vndirect.atm.model;

public class CardModel {

    private final String number;

    private String pin;

    private final String name;

    private final String accountNumber;

    private boolean active;

    public CardModel(String number, String name, String accountNumber, boolean active) {
        this.number = number;
        this.name = name;
        this.accountNumber = accountNumber;
        this.active = active;
    }

    public String getNumber() {
        return number;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
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

    public void setActive(boolean active) {
        this.active = active;
    }
}
