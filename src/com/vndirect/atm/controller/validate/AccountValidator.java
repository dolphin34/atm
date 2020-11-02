package com.vndirect.atm.controller.validate;

import com.vndirect.atm.controller.repo.entity.Account;
import com.vndirect.atm.controller.repo.entity.Transaction;
import com.vndirect.atm.controller.service.AccountService;
import com.vndirect.atm.controller.service.model.AccountModel;
import com.vndirect.atm.controller.service.model.TransactionModel;
import com.vndirect.atm.controller.service.serviceimpl.AccountServiceImpl;
import com.vndirect.atm.exception.FailActionException;
import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NotEnoughCashException;
import com.vndirect.atm.exception.NullException;

public interface AccountValidator {

    AccountService ACCOUNT_SERVICE = new AccountServiceImpl();
    int[][] cashWithdrawal(String accountNumber, String amount) throws InvalidInputException, NotEnoughCashException, FailActionException;
    AccountModel checkReceiveAccountNumber(String currentAccountNumber, String receiveAccountNumber) throws InvalidInputException, NullException;
    TransactionModel transfer(String currentAccountNumber, String receiveAccountNumber, String amountTransfer) throws NotEnoughCashException, FailActionException, InvalidInputException;

    default AccountModel getAccountByNumber(String accountNumber) throws NullException {
        return ACCOUNT_SERVICE.findAccountByNumber(accountNumber);
    }
}
