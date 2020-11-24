package com.vndirect.atm.controller.validate.inpuvalidation;

import com.vndirect.atm.controller.service.model.AccountModel;
import com.vndirect.atm.controller.service.model.TransactionModel;
import com.vndirect.atm.controller.validate.AccountValidator;
import com.vndirect.atm.controller.validate.datavalidation.DataAccountValidatorImpl;
import com.vndirect.atm.exception.*;
import com.vndirect.atm.util.StringUtils;

import java.util.List;
import java.util.Map;

public class InputAccountValidatorImpl implements AccountValidator {

    private static final DataAccountValidatorImpl DATA_ACCOUNT_VALIDATOR = new DataAccountValidatorImpl();

    @Override
    public List<Map.Entry<Integer, Integer>> cashWithdrawal(String accountNumber, String amount) throws InvalidInputException, NotEnoughCashInAtmException, FailActionException, NotEnoughBalanceException {
        amount = StringUtils.formatNumericString(amount);
        StringUtils.isNumericString(amount);

        boolean isValidAmount = Long.parseLong(amount) % 50_000 == 0;
        if (!isValidAmount) {
            throw new InvalidInputException("Number is multiples of 50,000");
        }
        return DATA_ACCOUNT_VALIDATOR.cashWithdrawal(accountNumber, amount);
    }

    @Override
    public AccountModel checkReceiveAccountNumber(String currentAccountNumber, String receiveAccountNumber) throws InvalidInputException, NullException {
        receiveAccountNumber = StringUtils.formatNumericString(receiveAccountNumber);
        StringUtils.isNumericString(receiveAccountNumber);

        boolean isValidCardNumber = receiveAccountNumber.length() == 5;
        if (!isValidCardNumber) {
            throw new InvalidInputException();
        }
        return DATA_ACCOUNT_VALIDATOR.checkReceiveAccountNumber(currentAccountNumber, receiveAccountNumber);
    }

    @Override
    public TransactionModel transfer(String currentAccountNumber, String receiveAccountNumber, String amountTransfer) throws FailActionException, InvalidInputException, NotEnoughBalanceException {
        amountTransfer = StringUtils.formatNumericString(amountTransfer);
        StringUtils.isNumericString(amountTransfer);

        return DATA_ACCOUNT_VALIDATOR.transfer(currentAccountNumber, receiveAccountNumber, amountTransfer);
    }
}
