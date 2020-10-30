package com.vndirect.atm.controller.repo.repository;

import com.vndirect.atm.controller.repo.entity.Transaction;

import java.util.List;

public interface TransactionRepository {
    boolean insertTransaction(Transaction transaction);
    List<Transaction> getListTransOfAccount(List<Integer> listTransId);
}
