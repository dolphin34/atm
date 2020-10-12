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
    void displayResultCashWithdrawal(boolean success, String message, int[][] result);

    void enterAccountTransfer();
    void enterAmountTransfer(String accountNumberReceive);
    void displayResultTransfer(boolean success, String message, Account currentAccount, String accountNumberReceive, long amountTransfer);

    void displayStatement(Account account, List<Transaction> listTransaction);
}
