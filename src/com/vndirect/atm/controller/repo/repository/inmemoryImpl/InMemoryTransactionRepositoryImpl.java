package com.vndirect.atm.controller.repo.repository.inmemoryImpl;

import com.vndirect.atm.controller.repo.data.Data;
import com.vndirect.atm.controller.repo.repository.TransactionRepository;
import com.vndirect.atm.controller.repo.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTransactionRepositoryImpl implements TransactionRepository {
    @Override
    public boolean insertTransaction(Transaction transaction) {
        return Data.listTransaction.add(transaction);
    }

    @Override
    public List<Transaction> getListTransOfAccount(List<Integer> listTransId) {
        List<Transaction> result = new ArrayList<>();
        for (Integer transId : listTransId) {
            for (Transaction trans : Data.listTransaction) {
                if (trans.getId() == transId) {
                    result.add(trans);
                }
            }
        }
        result.sort(Transaction::compareByDate);
        return result;
    }
}
