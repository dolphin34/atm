package com.vndirect.atm.validator;

import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NullException;
import com.vndirect.atm.exception.PinWrongException;
import com.vndirect.atm.service.CardService;
import com.vndirect.atm.service.model.CardModel;

public interface CardValidator {

    void validateCardNumber(String cardNumber) throws InvalidInputException;
    void existCardNumber(CardService cardService, String cardNumber) throws NullException;
    void validatePin(String pin) throws InvalidInputException;
    void confirmPin(CardService cardService, CardModel cardModel, String pin) throws PinWrongException;
}
