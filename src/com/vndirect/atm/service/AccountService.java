package com.vndirect.atm.service;

import com.vndirect.atm.exception.FailActionException;
import com.vndirect.atm.model.AccountModel;
import com.vndirect.atm.model.TransactionModel;

import java.util.Optional;

public interface AccountService {

    Optional<AccountModel> findByNumber(String accountNumber);

    TransactionModel processCashWithdrawal(AccountModel accountModel, long amountWithdrawal) throws FailActionException;

    TransactionModel processTransfer(AccountModel transferAccount, AccountModel receivedAccount, long amountTransfer) throws FailActionException;
}
