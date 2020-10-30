package com.vndirect.atm.controller.repo.entity;

public class Card {

    private final String number;
    private String pin;
    private final String name;
    private final String accountNumber;
    private boolean active;

    public Card(String number, String pin, String name, String accountNumber, boolean active) {
        this.number = number;
        this.pin = pin;
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
