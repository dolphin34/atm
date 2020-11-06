package com.vndirect.atm.controller.validate;

import com.vndirect.atm.controller.service.AccountService;
import com.vndirect.atm.controller.service.model.AccountModel;
import com.vndirect.atm.controller.service.model.TransactionModel;
import com.vndirect.atm.controller.service.serviceimpl.AccountServiceImpl;
import com.vndirect.atm.exception.*;

public interface AccountValidator {

    AccountService ACCOUNT_SERVICE = new AccountServiceImpl();
    int[][] cashWithdrawal(String accountNumber, String amount) throws InvalidInputException, NotEnoughCashInAtmException, FailActionException, NotEnoughBalanceException;
    AccountModel checkReceiveAccountNumber(String currentAccountNumber, String receiveAccountNumber) throws InvalidInputException, NullException;
    TransactionModel transfer(String currentAccountNumber, String receiveAccountNumber, String amountTransfer) throws FailActionException, InvalidInputException, NotEnoughBalanceException;

    default AccountModel getAccountByNumber(String accountNumber) throws NullException {
        AccountModel accountModel = ACCOUNT_SERVICE.findAccountByNumber(accountNumber);
        if (accountModel == null) {
            throw new NullException("This card doesn't link with any account!");
        }
        return accountModel;
    }
}
