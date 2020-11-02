package com.vndirect.atm.controller.validate.inpuvalidation;

import com.vndirect.atm.controller.repo.entity.Transaction;
import com.vndirect.atm.controller.service.model.AccountModel;
import com.vndirect.atm.controller.service.model.TransactionModel;
import com.vndirect.atm.controller.validate.AccountValidator;
import com.vndirect.atm.controller.validate.datavalidation.DataAccountValidatorImpl;
import com.vndirect.atm.exception.FailActionException;
import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NotEnoughCashException;
import com.vndirect.atm.exception.NullException;
import com.vndirect.atm.util.StringUtil;

public class InputAccountValidatorImpl implements AccountValidator {

    DataAccountValidatorImpl DATA_ACCOUNT_VALIDATOR = new DataAccountValidatorImpl();

    @Override
    public int[][] cashWithdrawal(String accountNumber, String amount) throws InvalidInputException, NotEnoughCashException, FailActionException {
        amount = StringUtil.formatNumericString(amount);
        StringUtil.isNumericString(amount);

        boolean isValidAmount = Long.parseLong(amount) % 50_000 == 0;
        if (!isValidAmount) {
            throw new InvalidInputException("Number is multiples of 50,000");
        }
        return DATA_ACCOUNT_VALIDATOR.cashWithdrawal(accountNumber, amount);
    }

    @Override
    public AccountModel checkReceiveAccountNumber(String currentAccountNumber, String receiveAccountNumber) throws InvalidInputException, NullException {
        receiveAccountNumber = StringUtil.formatNumericString(receiveAccountNumber);
        StringUtil.isNumericString(receiveAccountNumber);

        boolean isValidCardNumber = receiveAccountNumber.length() == 5;
        if (!isValidCardNumber) {
            throw new InvalidInputException();
        }
        return DATA_ACCOUNT_VALIDATOR.checkReceiveAccountNumber(currentAccountNumber, receiveAccountNumber);
    }

    @Override
    public TransactionModel transfer(String currentAccountNumber, String receiveAccountNumber, String amountTransfer) throws NotEnoughCashException, FailActionException, InvalidInputException {
        amountTransfer = StringUtil.formatNumericString(amountTransfer);
        StringUtil.isNumericString(amountTransfer);

        return DATA_ACCOUNT_VALIDATOR.transfer(currentAccountNumber, receiveAccountNumber, amountTransfer);
    }
}
