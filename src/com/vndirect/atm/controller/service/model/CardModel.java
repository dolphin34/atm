package com.vndirect.atm.controller.service.model;

public class CardModel {

    private final String number;
    private final String name;

    public CardModel(String number, String name) {
        this.number = number;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }
}
