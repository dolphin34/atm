package com.vndirect.atm.validator.impl;

import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NullException;
import com.vndirect.atm.service.AccountService;
import com.vndirect.atm.util.StringUtils;
import com.vndirect.atm.validator.AccountValidator;

public class AccountValidatorImpl implements AccountValidator {
    @Override
    public void validateAccountNumber(String accountNumber) throws InvalidInputException {
        boolean isValid = StringUtils.isNumericString(accountNumber) ;
        if(!isValid) {
            throw new InvalidInputException();
        }
    }

    @Override
    public void existAccountNumber(AccountService accountService, String accountNumber) throws NullException {
        if (accountService.findAccountModelByNumber(accountNumber) == null) {
            throw new NullException("Account does not exist!");
        }
    }

//    public AccountModel checkReceiveAccountNumber(String currentAccountNumber, String receiveAccountNumber) throws InvalidInputException, NullException {
//        receiveAccountNumber = receiveAccountNumber.trim();
//        StringUtils.isNumericString(receiveAccountNumber);
//
//        boolean isValidCardNumber = receiveAccountNumber.length() == 5;
//        if (!isValidCardNumber) {
//            throw new InvalidInputException();
//        }
//        return DATA_ACCOUNT_VALIDATOR.checkReceiveAccountNumber(currentAccountNumber, receiveAccountNumber);
//    }
//
//    @Override
//    public TransactionModel transfer(String currentAccountNumber, String receiveAccountNumber, String amountTransfer) throws FailActionException, InvalidInputException, NotEnoughBalanceException {
//        amountTransfer = amountTransfer.trim();
//        StringUtils.isNumericString(amountTransfer);
//
//        return DATA_ACCOUNT_VALIDATOR.transfer(currentAccountNumber, receiveAccountNumber, amountTransfer);
//    }


//    public AccountModel checkReceiveAccountNumber(String currentAccountNumber, String receiveAccountNumber) throws NullException, InvalidInputException {
//        if (currentAccountNumber.equals(receiveAccountNumber)) {
//            throw new InvalidInputException("Receive account number must be difference with perform account number!");
//        }
//        AccountModel accountModel = ACCOUNT_SERVICE.findAccountByNumber(receiveAccountNumber);
//        if (accountModel == null) {
//            throw new NullException("Account does not exist!");
//        }
//        return accountModel;
//    }
//
//    @Override
//    public TransactionModel transfer(String currentAccountNumber, String receiveAccountNumber, String amountTransfer) throws FailActionException, NotEnoughBalanceException {
//        return ACCOUNT_SERVICE.transfer(currentAccountNumber, receiveAccountNumber, Long.parseLong(amountTransfer));
//    }
}
