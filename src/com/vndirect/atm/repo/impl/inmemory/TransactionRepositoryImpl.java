package com.vndirect.atm.repo.impl.inmemory;

import com.vndirect.atm.entity.Transaction;
import com.vndirect.atm.repo.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionRepositoryImpl implements TransactionRepository {

    private static final TransactionRepositoryImpl INSTANCE = new TransactionRepositoryImpl();

    private static final List<Transaction> TRANSACTIONS = new ArrayList<>();

    private static int sizeTransactions = 0;

    public static TransactionRepositoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public int getSizeTransactions() {
        return sizeTransactions;
    }

    @Override
    public Optional<Transaction> findById(int transactionId) {
        return TRANSACTIONS.stream()
                        .filter(transaction -> transaction.getId() == transactionId)
                        .findFirst();
    }

    public static void incrementSizeOfListTransaction() {
        sizeTransactions++;
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        incrementSizeOfListTransaction();
        TRANSACTIONS.add(transaction);
    }

    @Override
    public List<Transaction> getTransactions(List<Integer> transactionIds) {
        return transactionIds.stream()
                            .map(this::findById)
                            .map(Optional::get)
                            .collect(Collectors.toList());
    }
}
