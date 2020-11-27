package com.vndirect.atm.service;

import com.vndirect.atm.service.model.AccountModel;
import com.vndirect.atm.service.model.TransactionModel;

public interface AccountService {

    AccountModel findAccountModelByNumber(String accountNumber);
    AccountModel processCashWithdrawal(AccountModel accountModel, long amountWithdrawal);
    TransactionModel processTransfer(AccountModel transferAccount, AccountModel receivedAccount, long amountTransfer);
}
