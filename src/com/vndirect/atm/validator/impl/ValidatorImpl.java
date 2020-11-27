package com.vndirect.atm.validator.impl;

import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NotEnoughBalanceException;
import com.vndirect.atm.exception.NullException;
import com.vndirect.atm.exception.PinWrongException;
import com.vndirect.atm.service.AccountService;
import com.vndirect.atm.service.CardService;
import com.vndirect.atm.service.model.CardModel;
import com.vndirect.atm.service.serviceimpl.AccountServiceImpl;
import com.vndirect.atm.service.serviceimpl.CardServiceImpl;
import com.vndirect.atm.validator.AccountValidator;
import com.vndirect.atm.validator.AmountValidator;
import com.vndirect.atm.validator.CardValidator;
import com.vndirect.atm.validator.Validator;

import static com.vndirect.atm.ui.impl.console.AtmView.SESSION;

public class ValidatorImpl implements Validator {

    private final CardService cardService = new CardServiceImpl();
    private final AccountService accountService = new AccountServiceImpl();

    private final CardValidator cardValidator = new CardValidatorImpl();
    private final AmountValidator amountValidator = new AmountValidatorImpl();
    private final AccountValidator accountValidator = new AccountValidatorImpl();

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

    @Override
    public boolean isValidReceivedAccountNumber(String accountNumber) throws InvalidInputException, NullException {
        accountValidator.validateAccountNumber(accountNumber);
        if (accountNumber.equals(SESSION.getCurrentAccount().getNumber())) {
            throw new InvalidInputException("Transfer account can not be receive account!");
        }
        accountValidator.existAccountNumber(accountService, accountNumber);
        return true;
    }

    @Override
    public boolean isValidAmountTransfer(String amountTransfer) throws InvalidInputException, NotEnoughBalanceException {
        amountValidator.validateAmount(amountTransfer);
        amountValidator.confirmAmountTransfer(amountTransfer);
        return true;
    }
}
