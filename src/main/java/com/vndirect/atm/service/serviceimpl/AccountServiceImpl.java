package com.vndirect.atm.service.serviceimpl;

import com.vndirect.atm.exception.FailActionException;
import com.vndirect.atm.entity.Account;
import com.vndirect.atm.entity.Transaction;
import com.vndirect.atm.repo.AccountRepository;
import com.vndirect.atm.repo.TransactionRepository;
import com.vndirect.atm.repo.impl.inmemory.AccountRepositoryImpl;
import com.vndirect.atm.repo.impl.inmemory.TransactionRepositoryImpl;
import com.vndirect.atm.service.AccountService;
import com.vndirect.atm.model.AccountModel;
import com.vndirect.atm.model.TransactionModel;
import com.vndirect.atm.util.Constants;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private static final AccountRepository ACCOUNT_REPOSITORY = AccountRepositoryImpl.getInstance();

    private static final TransactionRepository TRANSACTION_REPOSITORY = TransactionRepositoryImpl.getInstance();

    @Override
    public Optional<AccountModel> findByNumber(String accountNumber) {
        Optional<Account> account = ACCOUNT_REPOSITORY.findOneByNumber(accountNumber);
        if (account.isPresent()) {
            List<Transaction> transactions = TRANSACTION_REPOSITORY.findByIds(account.get().getTransactionIds());
            List<TransactionModel> transactionModels = new ArrayList<>();
            for (Transaction transaction : transactions) {
                TransactionModel temp = new TransactionModel(transaction.getTransactionType().getText(), transaction.getAmount(), transaction.getFee(), transaction.getDate(), transaction.getAccountNumberPerform(), transaction.getAccountNumberTarget());
                transactionModels.add(temp);
            }
            return Optional.of(new AccountModel(accountNumber, account.get().getName(), account.get().getAmount(), transactionModels));
        }
        return Optional.empty();
    }

    @Override
    public TransactionModel processCashWithdrawal(AccountModel accountModel, long amountWithdrawal) throws FailActionException {
        Transaction newTransaction = new Transaction(TRANSACTION_REPOSITORY.getSizeTransactions() + 1, Transaction.TransactionType.CASH_WITHDRAWAL, amountWithdrawal, Constants.CASH_WITHDRAWAL_FEE, LocalDateTime.now(), accountModel.getNumber(), null);

        Optional<Account> account = ACCOUNT_REPOSITORY.findOneByNumber(accountModel.getNumber());
        if (account.isPresent()) {
            account.get().addTransactionId(newTransaction.getId());
            account.get().setAmount(account.get().getAmount() - (amountWithdrawal + Constants.CASH_WITHDRAWAL_FEE));
        } else {
            throw new FailActionException();
        }

        TRANSACTION_REPOSITORY.save(newTransaction);
        ACCOUNT_REPOSITORY.update(account.get());

        return new TransactionModel(newTransaction.getTransactionType().getText(), newTransaction.getAmount(), newTransaction.getFee(), newTransaction.getDate(), newTransaction.getAccountNumberPerform(), newTransaction.getAccountNumberTarget());
    }

    @Override
    public TransactionModel processTransfer(AccountModel transferAccount, AccountModel receivedAccount, long amountTransfer) throws FailActionException {
        Transaction newTransaction = new Transaction(TRANSACTION_REPOSITORY.getSizeTransactions() + 1, Transaction.TransactionType.TRANSFER, amountTransfer, Constants.TRANSFER_FEE, LocalDateTime.now(), transferAccount.getNumber(), receivedAccount.getNumber());

        Optional<Account> sendAccount = ACCOUNT_REPOSITORY.findOneByNumber(transferAccount.getNumber());
        if (sendAccount.isPresent()) {
            sendAccount.get().setAmount(sendAccount.get().getAmount() - (amountTransfer + Constants.TRANSFER_FEE));
            sendAccount.get().addTransactionId(newTransaction.getId());
        } else {
            throw new FailActionException();
        }

        Optional<Account> targetAccount = ACCOUNT_REPOSITORY.findOneByNumber(receivedAccount.getNumber());
        if (targetAccount.isPresent()) {
            targetAccount.get().setAmount(targetAccount.get().getAmount() + amountTransfer);
            targetAccount.get().addTransactionId(newTransaction.getId());
        } else {
            throw new FailActionException();
        }

        TRANSACTION_REPOSITORY.save(newTransaction);
        ACCOUNT_REPOSITORY.update(sendAccount.get());
        ACCOUNT_REPOSITORY.update(targetAccount.get());

        return new TransactionModel(newTransaction.getTransactionType().getText(), newTransaction.getAmount(), newTransaction.getFee(), newTransaction.getDate(), newTransaction.getAccountNumberPerform(), newTransaction.getAccountNumberTarget());
    }
}
