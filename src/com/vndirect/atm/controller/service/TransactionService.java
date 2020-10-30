package com.vndirect.atm.controller.service;

import com.vndirect.atm.controller.repo.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getListTransOfAccount(List<Integer> listTransId);
    boolean insertTransaction(Transaction newTransaction);
}
