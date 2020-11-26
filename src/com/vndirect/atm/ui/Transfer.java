package com.vndirect.atm.ui;

import com.vndirect.atm.service.model.AccountModel;

public interface Transfer {

    void enterAccountTransfer();
    void enterOtherAmountCashWithdrawal();
    void transfer(AccountModel receiveAccount, String amountTransfer);
}
