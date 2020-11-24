package com.vndirect.atm.controller.service;

import com.vndirect.atm.controller.service.model.AccountModel;
import com.vndirect.atm.controller.service.model.TransactionModel;
import com.vndirect.atm.exception.FailActionException;
import com.vndirect.atm.exception.NotEnoughBalanceException;
import com.vndirect.atm.exception.NotEnoughCashInAtmException;
import com.vndirect.atm.exception.NullException;

import java.util.List;
import java.util.Map;

public interface AccountService {

    AccountModel findAccountByNumber(String accountNumber) throws NullException;
    List<Map.Entry<Integer, Integer>> cashWithdrawal(String accountNumber, long amount) throws FailActionException, NotEnoughCashInAtmException, NotEnoughBalanceException;
    TransactionModel transfer(String currentAccountNumber, String receiveAccountNumber, long amountTransfer) throws FailActionException, NotEnoughBalanceException;
}
