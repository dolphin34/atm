package ui;

import dto.Account;
import dto.Transaction;

import java.util.List;

public interface Views {
    void insertCard(String... messages);
    void enterPin(String userName, int time, String... messages);

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
