package com.vndirect.atm.controller.validate.datavalidation;

import com.vndirect.atm.controller.service.model.CardModel;
import com.vndirect.atm.controller.validate.CardValidator;
import com.vndirect.atm.exception.*;

public class DataCardValidatorImpl implements CardValidator {

    @Override
    public CardModel checkCardNumber(String cardNumber) throws NullException, LockCardException {
        CardModel cardModel = CARD_SERVICE.findCardByNumber(cardNumber);
        if (cardModel == null) {
            throw new NullException("Card does not exist!");
        }
        if (!cardModel.isActive()) {
            throw new LockCardException();
        }
        return cardModel;
    }

    @Override
    public void checkPin(String cardNumber, String pin, int time) throws PinWrongException, LockCardException, FailActionException {
        if (time > 3) {
            if (!CARD_SERVICE.lockCard(cardNumber)){
                throw new FailActionException("Lock card fail!");
            }
            throw new LockCardException();
        }
        if (!CARD_SERVICE.checkPin(cardNumber, pin)) {
            throw new PinWrongException(time);
        }
    }

    @Override
    public void pinChange(String cardNumber, String newPin) throws InvalidInputException, FailActionException {
        if (CARD_SERVICE.checkPin(cardNumber, newPin)) {
            throw new InvalidInputException("New pin can not the same old pin!");
        }
        if (!CARD_SERVICE.pinChange(cardNumber, newPin)) {
            throw new FailActionException("Pin change fail!");
        }
    }
}
