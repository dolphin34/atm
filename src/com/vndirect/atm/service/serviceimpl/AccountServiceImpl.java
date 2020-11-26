package com.vndirect.atm.service.serviceimpl;

import com.vndirect.atm.repo.data.Data;
import com.vndirect.atm.repo.entity.Account;
import com.vndirect.atm.repo.entity.Transaction;
import com.vndirect.atm.repo.repository.AccountRepository;
import com.vndirect.atm.repo.repository.TransactionRepository;
import com.vndirect.atm.repo.repository.inmemoryimpl.InMemoryAccountRepositoryImpl;
import com.vndirect.atm.repo.repository.inmemoryimpl.InMemoryTransactionRepositoryImpl;
import com.vndirect.atm.service.AccountService;
import com.vndirect.atm.service.model.AccountModel;
import com.vndirect.atm.service.model.TransactionModel;
import com.vndirect.atm.exception.FailActionException;
import com.vndirect.atm.exception.NotEnoughBalanceException;
import com.vndirect.atm.exception.NotEnoughCashInAtmException;
import com.vndirect.atm.util.Constants;

import java.util.*;

public class AccountServiceImpl implements AccountService {

    private static final AccountRepository ACCOUNT_REPOSITORY = new InMemoryAccountRepositoryImpl();
    private static final TransactionRepository TRANSACTION_REPOSITORY = new InMemoryTransactionRepositoryImpl();

    @Override
    public AccountModel findAccountModelByNumber(String accountNumber) {
        AccountModel accountModel = null;
        Account account = ACCOUNT_REPOSITORY.findAccountByNumber(accountNumber);
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
    public AccountModel processCashWithdrawal(AccountModel accountModel, long amountWithdrawal){
        Transaction newTransaction = new Transaction(Constants.DATA.getListTransaction().size() + 1, Transaction.TransactionType.CASH_WITHDRAWAL,
                amountWithdrawal, Constants.CASH_WITHDRAWAL_FEE, new Date(), accountModel.getNumber(), null);

        Account account = ACCOUNT_REPOSITORY.findAccountByNumber(accountModel.getNumber());
        account.addTransactionsId(newTransaction.getId());
        account.setAmount(account.getAmount() - (amountWithdrawal + Constants.CASH_WITHDRAWAL_FEE));

        boolean withdrawalSuccess = ACCOUNT_REPOSITORY.updateInfoAccount(account) && TRANSACTION_REPOSITORY.insertTransaction(newTransaction);
        if (withdrawalSuccess) {
            accountModel = findAccountModelByNumber(account.getNumber());
        }
        return accountModel;
    }



    @Override
    public TransactionModel transfer(String currentAccountNumber, String receiveAccountNumber, long amountTransfer) throws FailActionException, NotEnoughBalanceException {
        Account currentAccount = ACCOUNT_REPOSITORY.findAccountByNumber(currentAccountNumber);
        Account receiveAccount = ACCOUNT_REPOSITORY.findAccountByNumber(receiveAccountNumber);

        boolean isValidAmount = amountTransfer + Constants.TRANSFER_FEE <= currentAccount.getAmount() - Constants.MINIMUM_BALANCE;
        if (!isValidAmount) {
            throw new NotEnoughBalanceException();
        }

        Transaction newTransaction = new Transaction(Constants.DATA.getListTransaction().size() + 1, Transaction.TransactionType.TRANSFER,
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
