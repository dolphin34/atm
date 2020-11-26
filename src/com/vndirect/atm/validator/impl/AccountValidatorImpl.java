package com.vndirect.atm.validator.impl;

import com.vndirect.atm.validator.AccountValidator;

public class AccountValidatorImpl implements AccountValidator {

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
