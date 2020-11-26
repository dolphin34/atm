package com.vndirect.atm.validator;

import com.vndirect.atm.service.AccountService;
import com.vndirect.atm.service.model.AccountModel;
import com.vndirect.atm.service.model.TransactionModel;
import com.vndirect.atm.service.serviceimpl.AccountServiceImpl;
import com.vndirect.atm.exception.*;

import java.util.List;
import java.util.Map;

public interface AccountValidator {

//    AccountService ACCOUNT_SERVICE = new AccountServiceImpl();
//    List<Map.Entry<Integer, Integer>> cashWithdrawal(String accountNumber, String amount) throws InvalidInputException, NotEnoughCashInAtmException, FailActionException, NotEnoughBalanceException;
//    AccountModel checkReceiveAccountNumber(String currentAccountNumber, String receiveAccountNumber) throws InvalidInputException, NullException;
//    TransactionModel transfer(String currentAccountNumber, String receiveAccountNumber, String amountTransfer) throws FailActionException, InvalidInputException, NotEnoughBalanceException;
//
//    default AccountModel getAccountByNumber(String accountNumber) throws NullException {
//        AccountModel accountModel = ACCOUNT_SERVICE.findAccountByNumber(accountNumber);
//        if (accountModel == null) {
//            throw new NullException("This card doesn't link with any account!");
//        }
//        return accountModel;
//    }

}
