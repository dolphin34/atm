package com.vndirect.atm.repo;

import com.vndirect.atm.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {

    int getSizeTransactions();

    Optional<Transaction> findOneById(int transactionId);

    void save(Transaction transaction);

    List<Transaction> findByIds(List<Integer> transactionIds);
}
