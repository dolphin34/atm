package com.vndirect.atm.repo.repository;

import com.vndirect.atm.repo.entity.Transaction;

import java.util.List;

public interface TransactionRepository {

    int getSizeOfListTransaction();
    boolean insertTransaction(Transaction transaction);
    List<Transaction> getListTransactionByListId(List<Integer> listTransId);
}
