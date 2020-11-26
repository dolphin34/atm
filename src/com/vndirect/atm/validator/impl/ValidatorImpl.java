package com.vndirect.atm.validator.impl;

import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NotEnoughBalanceException;
import com.vndirect.atm.exception.NullException;
import com.vndirect.atm.exception.PinWrongException;
import com.vndirect.atm.service.CardService;
import com.vndirect.atm.service.model.CardModel;
import com.vndirect.atm.service.serviceimpl.CardServiceImpl;
import com.vndirect.atm.validator.AccountValidator;
import com.vndirect.atm.validator.AmountValidator;
import com.vndirect.atm.validator.CardValidator;
import com.vndirect.atm.validator.Validator;

public class ValidatorImpl implements Validator {

    private final CardService cardService = new CardServiceImpl();

    private final CardValidator cardValidator = new CardValidatorImpl();
    private final AmountValidator amountValidator = new AmountValidatorImpl();

    @Override
    public CardValidator getCardValidator() {
        return cardValidator;
    }

    @Override
    public boolean isValidCardNumber(String cardNumber) throws InvalidInputException, NullException {
        cardValidator.validateCardNumber(cardNumber);
        cardValidator.existCardNumber(cardService, cardNumber);
        return true;
    }

    @Override
    public boolean isValidPin(CardModel cardModel, String pin) throws InvalidInputException, PinWrongException {
        cardValidator.validatePin(pin);
        cardValidator.confirmPin(cardService, cardModel, pin);
        return true;
    }

    @Override
    public boolean isValidAmountCashWithdrawal(String amount) throws InvalidInputException, NotEnoughBalanceException {
        amountValidator.validateAmount(amount);
        amountValidator.confirmAmountCashWithdrawal(amount);
        return true;
    }
}
