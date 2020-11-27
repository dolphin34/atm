package com.vndirect.atm.validator.impl;

import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NullException;
import com.vndirect.atm.exception.PinWrongException;
import com.vndirect.atm.service.CardService;
import com.vndirect.atm.service.model.CardModel;
import com.vndirect.atm.util.StringUtils;
import com.vndirect.atm.validator.CardValidator;

import static com.vndirect.atm.ui.impl.console.AtmView.SESSION;

public class CardValidatorImpl implements CardValidator {

    @Override
    public void validateCardNumber(String cardNumber) throws InvalidInputException {
        boolean isValid = StringUtils.isNumericString(cardNumber) && cardNumber.length() == 8 && cardNumber.startsWith("0800");
        if(!isValid) {
            throw new InvalidInputException();
        }
    }

    @Override
    public void existCardNumber(CardService cardService, String cardNumber) throws NullException {
        CardModel cardModel = cardService.findCardByNumber(cardNumber);
        if (cardModel == null) {
            throw new NullException("Card does not exist!");
        }
        SESSION.setCurrentCard(cardModel);
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
        if (!cardService.checkPin(cardModel, pin)) {
            throw new PinWrongException();
        }
        SESSION.getCurrentCard().setPin(pin);
    }
}
