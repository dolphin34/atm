package com.vndirect.atm.userinterface;

import com.vndirect.atm.controller.service.model.AccountModel;
import com.vndirect.atm.controller.validate.AccountValidator;
import com.vndirect.atm.controller.validate.CardValidator;
import com.vndirect.atm.controller.validate.inpuvalidation.InputAccountValidatorImpl;
import com.vndirect.atm.controller.validate.inpuvalidation.InputCardValidatorImpl;

public interface View {

    CardValidator CARD_VALIDATOR = new InputCardValidatorImpl();
    AccountValidator ACCOUNT_VALIDATOR = new InputAccountValidatorImpl();

    void insertCard();
    void enterPin(int time);
    void showMenu();
    void pinChange();
    void displayBalanceInquiry();
    void showOptionsCashWithdrawal();
    void cashWithdrawal(String amount);
    void enterAccountTransfer();
    void transfer(AccountModel receiveAccount, String amountTransfer);
    void displayStatement();
    void logout();
}
