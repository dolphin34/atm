package com.vndirect.atm.controller.validate.datavalidation;

import com.vndirect.atm.controller.service.CardService;
import com.vndirect.atm.controller.service.model.CardModel;
import com.vndirect.atm.controller.service.serviceimpl.CardServiceImpl;
import com.vndirect.atm.controller.validate.CardValidator;
import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.LockCardException;
import com.vndirect.atm.exception.NullCardException;
import com.vndirect.atm.exception.PinWrongException;
import javafx.fxml.LoadException;

public class DataCardValidatorImpl implements CardValidator {

    private static final CardService CARD_SERVICE = new CardServiceImpl();

    @Override
    public void checkCardNumber(String cardNumber) throws NullCardException, LockCardException {
        CardModel card = CARD_SERVICE.findCardByNumber(cardNumber);
        if (card != null) {
            VIEW.enterPin(card, 1);
        } else {
            throw new NullCardException();
        }
    }

    @Override
    public void checkPin(String pin, int time) throws PinWrongException, LockCardException {
        if (CARD_SERVICE.checkPin(pin)) {
            VIEW.home();
        } else {
            if (time > 3) {
                CARD_SERVICE.lockCard(CardServiceImpl.currentCard.getNumber());
                throw  new LockCardException();
            }
            throw new PinWrongException(time);
        }
    }
}
