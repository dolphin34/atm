package com.vndirect.atm.controller.validate.inpuvalidation;

import com.vndirect.atm.controller.service.model.CardModel;
import com.vndirect.atm.controller.validate.CardValidator;
import com.vndirect.atm.controller.validate.datavalidation.DataCardValidatorImpl;
import com.vndirect.atm.exception.*;
import com.vndirect.atm.util.StringUtil;

public class InputCardValidatorImpl implements CardValidator {

    public static final CardValidator DATA_CARD_VALIDATOR = new DataCardValidatorImpl();

    @Override
    public CardModel checkCardNumber(String cardNumber) throws InvalidInputException, NullException, LockCardException {
        cardNumber = StringUtil.formatNumericString(cardNumber);
        StringUtil.isNumericString(cardNumber);

        boolean isValidCardNumber = cardNumber.length() == 8 && cardNumber.startsWith("0800");
        if (!isValidCardNumber) {
            throw new InvalidInputException();
        }
        return DATA_CARD_VALIDATOR.checkCardNumber(cardNumber);
    }

    @Override
    public void checkPin(String cardNumber, String pin, int time) throws InvalidInputException, PinWrongException, LockCardException {
        pin = StringUtil.formatNumericString(pin);
        StringUtil.isNumericString(pin);

        if (pin.length() != 6) {
            throw new InvalidInputException();
        }
        DATA_CARD_VALIDATOR.checkPin(cardNumber, pin, time);
    }

    @Override
    public void pinChange(String cardNumber, String newPin) throws InvalidInputException, FailActionException {
        newPin = StringUtil.formatNumericString(newPin);
        StringUtil.isNumericString(newPin);

        if (newPin.length() != 6) {
            throw new InvalidInputException();
        }
        DATA_CARD_VALIDATOR.pinChange(cardNumber, newPin);
    }
}
