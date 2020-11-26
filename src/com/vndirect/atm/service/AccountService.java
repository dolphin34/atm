package com.vndirect.atm.service;

import com.vndirect.atm.service.model.AccountModel;
import com.vndirect.atm.service.model.TransactionModel;
import com.vndirect.atm.exception.FailActionException;
import com.vndirect.atm.exception.NotEnoughBalanceException;
import com.vndirect.atm.exception.NotEnoughCashInAtmException;
import com.vndirect.atm.exception.NullException;

import java.util.List;
import java.util.Map;

public interface AccountService {

    AccountModel findAccountModelByNumber(String accountNumber);
    AccountModel processCashWithdrawal(AccountModel accountModel, long amountWithdrawal);
    TransactionModel transfer(String currentAccountNumber, String receiveAccountNumber, long amountTransfer) throws FailActionException, NotEnoughBalanceException;
}
