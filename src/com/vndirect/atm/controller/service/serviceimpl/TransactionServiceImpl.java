package com.vndirect.atm.controller.service.serviceimpl;

import com.vndirect.atm.controller.repo.repository.TransactionRepository;
import com.vndirect.atm.controller.repo.repository.inmemoryImpl.InMemoryTransactionRepositoryImpl;
import com.vndirect.atm.controller.repo.entity.Transaction;
import com.vndirect.atm.controller.service.TransactionService;

import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private static final TransactionRepository TRANSACTION_REPOSITORY = new InMemoryTransactionRepositoryImpl();

    @Override
    public List<Transaction> getListTransOfAccount(List<Integer> listTransId) {
        return TRANSACTION_REPOSITORY.getListTransOfAccount(listTransId);
    }

    @Override
    public boolean insertTransaction(Transaction newTransaction) {
        return TRANSACTION_REPOSITORY.insertTransaction(newTransaction);
    }
}
