package com.vndirect.atm.controller.validate.datavalidation;

import com.vndirect.atm.controller.service.model.AccountModel;
import com.vndirect.atm.controller.service.model.TransactionModel;
import com.vndirect.atm.controller.validate.AccountValidator;
import com.vndirect.atm.exception.*;

import java.util.List;
import java.util.Map;

public class DataAccountValidatorImpl implements AccountValidator {
    @Override
    public List<Map.Entry<Integer, Integer>> cashWithdrawal(String accountNumber, String amount) throws NotEnoughCashInAtmException, FailActionException, NotEnoughBalanceException {
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
    public TransactionModel transfer(String currentAccountNumber, String receiveAccountNumber, String amountTransfer) throws FailActionException, NotEnoughBalanceException {
        return ACCOUNT_SERVICE.transfer(currentAccountNumber, receiveAccountNumber, Long.parseLong(amountTransfer));
    }
}
