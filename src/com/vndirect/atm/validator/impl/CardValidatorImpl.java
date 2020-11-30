package com.vndirect.atm.validator.impl;

import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NullException;
import com.vndirect.atm.exception.PinWrongException;
import com.vndirect.atm.service.CardService;
import com.vndirect.atm.model.CardModel;
import com.vndirect.atm.util.StringUtils;
import com.vndirect.atm.validator.CardValidator;

import java.util.Optional;

public class CardValidatorImpl implements CardValidator {

    @Override
    public void validateCardNumber(String cardNumber) throws InvalidInputException {
        boolean isValid = StringUtils.isNumericString(cardNumber) && cardNumber.length() == 8 && cardNumber.startsWith("0800");
        if (!isValid) {
            throw new InvalidInputException();
        }
    }

    @Override
    public void existCardNumber(CardService cardService, String cardNumber) throws NullException {
        Optional<CardModel> cardModel = cardService.findByNumber(cardNumber);
        if (!cardModel.isPresent()) {
            throw new NullException("Card is not exist!");
        }
    }

    @Override
    public void validatePin(String pin) throws InvalidInputException {
        boolean isValidPin = StringUtils.isNumericString(pin) && pin.length() == 6;
        if (!isValidPin) {
            throw new InvalidInputException();
        }
    }

    @Override
    public void confirmPin(CardService cardService, CardModel cardModel, String pin) throws PinWrongException {
        cardService.checkPin(cardModel, pin);
    }
}
