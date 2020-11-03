package com.vndirect.atm.controller.validate.datavalidation;

import com.vndirect.atm.controller.repo.data.Data;
import com.vndirect.atm.controller.service.model.AccountModel;
import com.vndirect.atm.controller.service.model.TransactionModel;
import com.vndirect.atm.controller.validate.AccountValidator;
import com.vndirect.atm.exception.FailActionException;
import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NotEnoughCashException;
import com.vndirect.atm.exception.NullException;

public class DataAccountValidatorImpl implements AccountValidator {
    @Override
    public int[][] cashWithdrawal(String accountNumber, String amount) throws NotEnoughCashException, FailActionException {
        boolean isEnoughCashInAtm = Long.parseLong(amount) <= Data.CashInAtm.sumOfCash();
        if (!isEnoughCashInAtm) {
            throw new NotEnoughCashException("Not enough money in ATM!");
        }
        return ACCOUNT_SERVICE.cashWithdrawal(accountNumber, Long.parseLong(amount));
    }

    @Override
    public AccountModel checkReceiveAccountNumber(String currentAccountNumber, String receiveAccountNumber) throws NullException, InvalidInputException {
        if (currentAccountNumber.equals(receiveAccountNumber)) {
            throw new InvalidInputException("Receive account number must be difference with perform account number!");
        }
        AccountModel accountModel = ACCOUNT_SERVICE.findAccountByNumber(receiveAccountNumber);
        if (accountModel == null) {
            throw new NullException("Account does not exist!");
        }
        return accountModel;
    }

    @Override
    public TransactionModel transfer(String currentAccountNumber, String receiveAccountNumber, String amountTransfer) throws NotEnoughCashException, FailActionException {
        return ACCOUNT_SERVICE.transfer(currentAccountNumber, receiveAccountNumber, Long.parseLong(amountTransfer));
    }
}
