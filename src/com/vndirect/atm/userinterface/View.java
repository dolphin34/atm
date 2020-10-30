package com.vndirect.atm.userinterface;

import com.vndirect.atm.controller.repo.entity.Account;
import com.vndirect.atm.controller.repo.entity.Transaction;
import com.vndirect.atm.controller.service.model.CardModel;
import com.vndirect.atm.controller.validate.AccountValidator;
import com.vndirect.atm.controller.validate.CardValidator;
import com.vndirect.atm.controller.validate.inpuvalidation.InputAccountValidatorImpl;
import com.vndirect.atm.controller.validate.inpuvalidation.InputCardValidatorImpl;

import java.util.List;

public interface View {

    CardValidator CARD_VALIDATOR = new InputCardValidatorImpl();
    AccountValidator ACCOUNT_VALIDATOR = new InputAccountValidatorImpl();

    void insertCard();
    void enterPin(CardModel cardModel, int time);

    void home();
    void pinChange(String... messages);
    void displayBalanceInquiry(Account account);

    void cashWithdrawal();
    void enterOtherAmountCashWithdrawal(String... messages);
    void displayResultCashWithdrawal(boolean success, int[][] result, Transaction transaction, String... messages);

    void enterAccountTransfer(String... messages);
    void enterAmountTransfer(String accountNumberReceive, String accountNameReceive, String... messages);
    void displayResultTransfer(boolean success, String message, Account currentAccount, Transaction transaction);

    void displayStatement(Account account, List<Transaction> listTransaction);
}
