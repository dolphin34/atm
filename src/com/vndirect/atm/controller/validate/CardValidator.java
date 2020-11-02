package com.vndirect.atm.controller.validate;

import com.vndirect.atm.controller.service.CardService;
import com.vndirect.atm.controller.service.model.CardModel;
import com.vndirect.atm.controller.service.serviceimpl.CardServiceImpl;
import com.vndirect.atm.exception.*;

public interface CardValidator {

    CardService CARD_SERVICE = new CardServiceImpl();

    CardModel checkCardNumber(String cardNumber) throws InvalidInputException, LockCardException, NullException;
    void checkPin(String cardNumber, String pin, int time) throws InvalidInputException, PinWrongException, LockCardException;
    void pinChange(String cardNumber, String newPin) throws InvalidInputException, FailActionException;
}
