package com.vndirect.atm.repo;

import com.vndirect.atm.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {

    int getSizeTransactions();

    Optional<Transaction> findById(int transactionId);

    void saveTransaction(Transaction transaction);

    List<Transaction> getTransactions(List<Integer> transactionIds);
}
