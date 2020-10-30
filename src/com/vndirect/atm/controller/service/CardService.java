package com.vndirect.atm.controller.service;

import com.vndirect.atm.controller.service.model.CardModel;
import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.LockCardException;
import com.vndirect.atm.exception.NullCardException;

public interface CardService {

    CardModel findCardByNumber(String cardNumber) throws LockCardException;
    boolean checkPin(String pin);
    boolean lockCard(String cardNumber);
    void pinChange(String newPin);
    void logout();
}
