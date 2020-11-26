package com.vndirect.atm.validator;

import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NotEnoughBalanceException;
import com.vndirect.atm.exception.NullException;
import com.vndirect.atm.exception.PinWrongException;
import com.vndirect.atm.service.model.CardModel;

public interface Validator {

    CardValidator getCardValidator();
    boolean isValidCardNumber(String cardNumber) throws InvalidInputException, NullException;
    boolean isValidPin(CardModel cardModel, String pin) throws InvalidInputException, PinWrongException;
    boolean isValidAmountCashWithdrawal(String amount) throws InvalidInputException, NotEnoughBalanceException;
}
