package com.vndirect.atm.controller.service.serviceimpl;

import com.vndirect.atm.controller.repo.repository.AccountRepository;
import com.vndirect.atm.controller.repo.repository.inmemoryImpl.InMemoryAccountRepositoryImpl;
import com.vndirect.atm.controller.repo.data.Data;
import com.vndirect.atm.controller.repo.entity.Account;
import com.vndirect.atm.controller.repo.entity.Transaction;
import com.vndirect.atm.controller.service.AccountService;
import com.vndirect.atm.controller.service.TransactionService;
import com.vndirect.atm.userinterface.consoleviewImpl.ConsoleViewImpl;
import com.vndirect.atm.userinterface.View;
import com.vndirect.atm.util.Constants;
import com.vndirect.atm.util.StringUtil;

import java.util.Date;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    private static Account currentAccount;

    private static final View VIEW = new ConsoleViewImpl();
    private static final AccountRepository ACCOUNT_REPOSITORY = new InMemoryAccountRepositoryImpl();
    private static final TransactionService TRANSACTION_SERVICE = new TransactionServiceImpl();

    @Override
    public boolean isExistAccountNumber(String accountNumber) {
        return ACCOUNT_REPOSITORY.findByAccountNumber(accountNumber) != null;
    }

    @Override
    public void getLinkedAccountWithCard(String accountNumber) {
        currentAccount = ACCOUNT_REPOSITORY.findByAccountNumber(accountNumber);
    }

    @Override
    public void removeLinkedAccountWithCard() {
        currentAccount = null;
    }

    @Override
    public void balanceInquiry() {
        VIEW.displayBalanceInquiry(currentAccount);
    }

    @Override
    public void cashWithdrawal(long amountWithdrawal) {
        String message;
        boolean isValidAmount = amountWithdrawal <= currentAccount.getAmount() - Constants.MINIMUM_BALANCE;
        if (isValidAmount) {
            int[][] cashOut = payOutCash(amountWithdrawal);
            if (cashOut != null) {
                Transaction newTransaction = new Transaction(Data.listTransaction.size() + 1, Transaction.TransactionType.CASH_WITHDRAWAL,
                        amountWithdrawal, Constants.CASH_WITHDRAWAL_FEE, new Date(), currentAccount.getNumber(), null);
                boolean withdrawalSuccess = TRANSACTION_SERVICE.insertTransaction(newTransaction) && ACCOUNT_REPOSITORY.updateInfoAccount(currentAccount.getNumber(), currentAccount.getAmount() - (amountWithdrawal + Constants.CASH_WITHDRAWAL_FEE), newTransaction.getId());
                if (withdrawalSuccess) {
                    currentAccount = ACCOUNT_REPOSITORY.findByAccountNumber(currentAccount.getNumber());
                    message = "Successfully!";
                    VIEW.displayResultCashWithdrawal(true, cashOut, newTransaction, message);
                } else {
                    message = "An error occurred!";
                    VIEW.displayResultCashWithdrawal(false, null, null, message);
                }
            } else {
                message = "Not enough money in ATM!";
                VIEW.enterOtherAmountCashWithdrawal(message);
            }
        } else {
            message = "Unavailable balance!";
            String balance = "Balance: " + StringUtil.amountToString(currentAccount.getAmount());
            VIEW.enterOtherAmountCashWithdrawal(message, balance);
        }

    }

    public int[][] payOutCash(long amount) {
        if (amount <= Data.CashInAtm.sumOfCash()) {
            int[][] a = new int[3][4];
            a[0][0] = Data.CashInAtm.CASH_IN_ATM_50.getValue();
            a[0][1] = Data.CashInAtm.CASH_IN_ATM_100.getValue();
            a[0][2] = Data.CashInAtm.CASH_IN_ATM_200.getValue();
            a[0][3] = Data.CashInAtm.CASH_IN_ATM_500.getValue();
            a[1][0] = Data.CashInAtm.CASH_IN_ATM_50.getQuantity();
            a[1][1] = Data.CashInAtm.CASH_IN_ATM_100.getQuantity();
            a[1][2] = Data.CashInAtm.CASH_IN_ATM_200.getQuantity();
            a[1][3] = Data.CashInAtm.CASH_IN_ATM_500.getQuantity();

            for (int i = 3; i >= 0; i--) {
                if (amount == 0) break;
                if (a[1][i] == 0) continue;
                a[2][i] = (int) amount / a[0][i];
                //
                if (a[2][i] > a[1][i]) {
                    a[2][i] = a[1][i];
                }
                a[1][i] = a[1][i] - a[2][i];
                //
                amount = amount - (a[0][i] * a[2][i]);
            }
            if (amount != 0)
                return null;

            Data.CashInAtm.CASH_IN_ATM_50.setQuantity(a[1][0]);
            Data.CashInAtm.CASH_IN_ATM_100.setQuantity(a[1][1]);
            Data.CashInAtm.CASH_IN_ATM_200.setQuantity(a[1][2]);
            Data.CashInAtm.CASH_IN_ATM_500.setQuantity(a[1][3]);
            return a;
        } else return null;
    }

    @Override
    public void checkReceiveAccount(String accountNumberReceive) {
        String message;
        if (!accountNumberReceive.equals(currentAccount.getNumber())) {
            Account accountReceive = ACCOUNT_REPOSITORY.findByAccountNumber(accountNumberReceive);
            if (accountReceive != null) {
                VIEW.enterAmountTransfer(accountReceive.getNumber(), accountReceive.getName());
            } else {
                message = "Account number does not exist!";
                VIEW.enterAccountTransfer(message);
            }
        } else {
            message = "Receive account number must be different transfer account number!";
            VIEW.enterAccountTransfer(message);
        }
    }

    @Override
    public void transfer(String accountNumberReceive, long amountTransfer) {
        boolean isValidAmount = amountTransfer + Constants.TRANSFER_FEE <= currentAccount.getAmount() - Constants.MINIMUM_BALANCE;
        Account accountReceive = ACCOUNT_REPOSITORY.findByAccountNumber(accountNumberReceive);
        String message;
        if (isValidAmount) {
            Transaction newTransaction = new Transaction(Data.listTransaction.size() + 1, Transaction.TransactionType.TRANSFER,
                    amountTransfer, Constants.TRANSFER_FEE, new Date(), currentAccount.getNumber(), accountNumberReceive);
            boolean transferSuccess = TRANSACTION_SERVICE.insertTransaction(newTransaction)
                    && ACCOUNT_REPOSITORY.updateInfoAccount(currentAccount.getNumber(), currentAccount.getAmount() - ( amountTransfer + Constants.TRANSFER_FEE), newTransaction.getId())
                    && ACCOUNT_REPOSITORY.updateInfoAccount(accountReceive.getNumber(), accountReceive.getAmount() + amountTransfer, newTransaction.getId());
            if (transferSuccess) {
                currentAccount = ACCOUNT_REPOSITORY.findByAccountNumber(currentAccount.getNumber());
                message = "Transfer successfully!";
                VIEW.displayResultTransfer(true, message, currentAccount, newTransaction);
            } else {
                message = "An error occurred!";
                VIEW.displayResultTransfer(false, message, currentAccount, newTransaction);
            }
        } else {
            message = "Unavailable balance!";
            String balance = "Balance: " + StringUtil.amountToString(currentAccount.getAmount());
            VIEW.enterAmountTransfer(accountReceive.getNumber(), accountReceive.getName(), message, balance);
        }
    }

    @Override
    public void printStatement() {
        List<Integer> listTransId = ACCOUNT_REPOSITORY.getListTransactionId(currentAccount.getNumber());
        List<Transaction> listTransaction = TRANSACTION_SERVICE.getListTransOfAccount(listTransId);
        VIEW.displayStatement(currentAccount, listTransaction);
    }


}
