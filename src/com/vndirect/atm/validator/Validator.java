package com.vndirect.atm.validator;

import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NotEnoughBalanceException;
import com.vndirect.atm.exception.NullException;
import com.vndirect.atm.exception.PinWrongException;
import com.vndirect.atm.model.CardModel;

public interface Validator {

    CardValidator getCardValidator();

    void isValidCardNumber(String cardNumber) throws InvalidInputException, NullException;

    void isValidPin(CardModel cardModel, String pin) throws InvalidInputException, PinWrongException;

    void isValidAmountCashWithdrawal(String amount) throws InvalidInputException, NotEnoughBalanceException;

    void isValidReceivedAccountNumber(String accountNumber) throws InvalidInputException, NullException;

    void isValidAmountTransfer(String amountTransfer) throws InvalidInputException, NotEnoughBalanceException;
}
