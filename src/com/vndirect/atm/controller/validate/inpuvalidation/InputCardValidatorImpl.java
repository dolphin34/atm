package com.vndirect.atm.controller.validate.inpuvalidation;

import com.vndirect.atm.controller.validate.CardValidator;
import com.vndirect.atm.controller.validate.datavalidation.DataCardValidatorImpl;
import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.LockCardException;
import com.vndirect.atm.exception.NullCardException;
import com.vndirect.atm.exception.PinWrongException;
import com.vndirect.atm.util.StringUtil;

public class InputCardValidatorImpl implements CardValidator {

    public static final CardValidator DATA_CARD_VALIDATOR = new DataCardValidatorImpl();

    @Override
    public void checkCardNumber(String cardNumber) throws InvalidInputException, NullCardException, LockCardException {
        cardNumber = StringUtil.formatNumericString(cardNumber);
        StringUtil.isNumericString(cardNumber);

        boolean isValidCardNumber = cardNumber.length() == 8 && cardNumber.startsWith("0800");
        if (isValidCardNumber) {
            DATA_CARD_VALIDATOR.checkCardNumber(cardNumber);
        } else {
            throw new InvalidInputException();
        }
    }

    @Override
    public void checkPin(String pin, int time) throws InvalidInputException, PinWrongException, LockCardException {
        pin = StringUtil.formatNumericString(pin);
        StringUtil.isNumericString(pin);

        if (pin.length() == 6) {
            DATA_CARD_VALIDATOR.checkPin(pin, time);
        } else {
            throw new InvalidInputException();
        }
    }
}
