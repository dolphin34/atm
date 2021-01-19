package com.vndirect.atm.ui;

import com.vndirect.atm.model.AccountModel;
import com.vndirect.atm.model.TransactionModel;

public interface Transfer {

    void enterReceivedAccountNumber();

    void enterAmountTransfer(AccountModel receivedAccount);

    void processTransfer(AccountModel receiveAccount, long amountTransfer);

    void displayResult(AccountModel receiveAccount, TransactionModel transactionModel);
}
