package com.vndirect.atm.controller.service;

import com.vndirect.atm.controller.service.model.CardModel;
import com.vndirect.atm.exception.LockCardException;
import com.vndirect.atm.exception.NullException;

public interface CardService {

    CardModel findCardByNumber(String cardNumber) throws LockCardException, NullException;
    boolean checkPin(String cardNumber, String pin);
    boolean lockCard(String cardNumber);
    boolean pinChange(String cardNumber, String newPin);
}
