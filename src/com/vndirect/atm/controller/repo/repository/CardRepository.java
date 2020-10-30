package com.vndirect.atm.controller.repo.repository;

import com.vndirect.atm.controller.repo.entity.Card;

public interface CardRepository {

    Card findCardByNumber(String cardNumber);
    boolean checkPin(String cardNumber, String pin);
    boolean lockCard(String cardNumber);
    boolean pinChange(String cardNumber, String newPIN);
}
