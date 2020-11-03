package com.vndirect.atm.controller.service.serviceimpl;

import com.vndirect.atm.controller.repo.data.Data;
import com.vndirect.atm.controller.repo.entity.Account;
import com.vndirect.atm.controller.repo.entity.Transaction;
import com.vndirect.atm.controller.repo.repository.AccountRepository;
import com.vndirect.atm.controller.repo.repository.TransactionRepository;
import com.vndirect.atm.controller.repo.repository.inmemoryImpl.InMemoryAccountRepositoryImpl;
import com.vndirect.atm.controller.repo.repository.inmemoryImpl.InMemoryTransactionRepositoryImpl;
import com.vndirect.atm.controller.service.AccountService;
import com.vndirect.atm.controller.service.model.AccountModel;
import com.vndirect.atm.controller.service.model.TransactionModel;
import com.vndirect.atm.exception.FailActionException;
import com.vndirect.atm.exception.NotEnoughCashException;
import com.vndirect.atm.util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    private static final AccountRepository ACCOUNT_REPOSITORY = new InMemoryAccountRepositoryImpl();
    private static final TransactionRepository TRANSACTION_REPOSITORY = new InMemoryTransactionRepositoryImpl();

    @Override
    public AccountModel findAccountByNumber(String accountNumber) {
        AccountModel accountModel = null;
        Account account = ACCOUNT_REPOSITORY.findByAccountNumber(accountNumber);
        if (account != null) {
            List<Transaction> listTransaction = TRANSACTION_REPOSITORY.getListTransactionByListId(account.getListTransactionsId());
            List<TransactionModel> listTransactionModel = new ArrayList<>();
            for (Transaction tran : listTransaction) {
                TransactionModel.TransactionModelType transferType = null;
                if (tran.getTransType().getCode() == 1) {
                    transferType = TransactionModel.TransactionModelType.TRANSFER;
                }
                if (tran.getTransType().getCode() == 2) {
                    transferType = TransactionModel.TransactionModelType.CASH_WITHDRAWAL;
                }

                TransactionModel temp = new TransactionModel(transferType, tran.getAmount(), tran.getFee(), tran.getDate(), tran.getAccountNumberPerform(), tran.getAccountNumberTarget());
                listTransactionModel.add(temp);
            }
            accountModel = new AccountModel(accountNumber, account.getName(), account.getAmount(), listTransactionModel);
        }
        return accountModel;
    }

    @Override
    public int[][] cashWithdrawal(String accountNumber, long amountWithdrawal) throws FailActionException, NotEnoughCashException {
        Account account = ACCOUNT_REPOSITORY.findByAccountNumber(accountNumber);

        boolean isValidAmount = amountWithdrawal <= account.getAmount() - Constants.MINIMUM_BALANCE;
        if (!isValidAmount) {
            throw new NotEnoughCashException("Unavailable balance!");
        }

        int[][] cashOut = cashOut(amountWithdrawal);
        if (cashOut == null) {
            throw new NotEnoughCashException("Not enough money in ATM!");
        }

        Transaction newTransaction = new Transaction(Data.listTransaction.size() + 1, Transaction.TransactionType.CASH_WITHDRAWAL,
                amountWithdrawal, Constants.CASH_WITHDRAWAL_FEE, new Date(), accountNumber, null);
        account = new Account(account.getNumber(), account.getName(), account.getAmount() - (amountWithdrawal + Constants.CASH_WITHDRAWAL_FEE), account.addTransactionsId(newTransaction.getId()));

        boolean withdrawalSuccess = ACCOUNT_REPOSITORY.updateInfoAccount(account) && TRANSACTION_REPOSITORY.insertTransaction(newTransaction);
        if (!withdrawalSuccess) {
            throw new FailActionException("Cash Withdrawal fail!");
        }

        Data.CashInAtm.updateQuantity(cashOut);
        return cashOut;
    }

    public int[][] cashOut(long amount) {
        int[][] a = Data.CashInAtm.getCashInATM();
        for (int i = 0; i < 4; i++) {
            if (amount == 0) break;
            if (a[1][i] == 0) continue;
            a[2][i] = (int) amount / a[0][i];
            if (a[2][i] > a[1][i]) {
                a[2][i] = a[1][i];
            }
            a[1][i] = a[1][i] - a[2][i];
            amount = amount - (a[0][i] * a[2][i]);
        }
        if (amount != 0) {
            return null;
        }
        return a;
    }

    @Override
    public TransactionModel transfer(String currentAccountNumber, String receiveAccountNumber, long amountTransfer) throws NotEnoughCashException, FailActionException {
        Account currentAccount = ACCOUNT_REPOSITORY.findByAccountNumber(currentAccountNumber);
        Account receiveAccount = ACCOUNT_REPOSITORY.findByAccountNumber(receiveAccountNumber);

        boolean isValidAmount = amountTransfer + Constants.TRANSFER_FEE <= currentAccount.getAmount() - Constants.MINIMUM_BALANCE;
        if (!isValidAmount) {
            throw new NotEnoughCashException("Unavailable balance!");
        }

        Transaction newTransaction = new Transaction(Data.listTransaction.size() + 1, Transaction.TransactionType.TRANSFER,
                amountTransfer, Constants.TRANSFER_FEE, new Date(), currentAccount.getNumber(), receiveAccount.getNumber());
        TransactionModel transactionModel = new TransactionModel(TransactionModel.TransactionModelType.TRANSFER, newTransaction.getAmount(),
                newTransaction.getFee(), newTransaction.getDate(), newTransaction.getAccountNumberPerform(), newTransaction.getAccountNumberTarget());
        currentAccount = new Account(currentAccount.getNumber(), currentAccount.getName(), currentAccount.getAmount() - ( amountTransfer + Constants.TRANSFER_FEE), currentAccount.addTransactionsId(newTransaction.getId()));
        receiveAccount = new Account(receiveAccount.getNumber(), receiveAccount.getName(), receiveAccount.getAmount() + amountTransfer, receiveAccount.addTransactionsId(newTransaction.getId()));

        boolean transferSuccess = ACCOUNT_REPOSITORY.updateInfoAccount(currentAccount)
                && ACCOUNT_REPOSITORY.updateInfoAccount(receiveAccount)
                && TRANSACTION_REPOSITORY.insertTransaction(newTransaction);
        if (!transferSuccess) {
           throw new FailActionException("Transfer fail!");
        }
        return transactionModel;
    }
}
