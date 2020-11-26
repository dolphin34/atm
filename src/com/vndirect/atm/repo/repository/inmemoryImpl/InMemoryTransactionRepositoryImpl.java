package com.vndirect.atm.repo.repository.inmemoryimpl;

import com.vndirect.atm.repo.repository.TransactionRepository;
import com.vndirect.atm.repo.entity.Transaction;
import com.vndirect.atm.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTransactionRepositoryImpl implements TransactionRepository {

    @Override
    public boolean insertTransaction(Transaction transaction) {
        return Constants.DATA.getListTransaction().add(transaction);
    }

    @Override
    public List<Transaction> getListTransactionByListId(List<Integer> listTransId) {
        List<Transaction> result = new ArrayList<>();
        for (Integer transId : listTransId) {
            for (Transaction trans : Constants.DATA.getListTransaction()) {
                if (trans.getId() == transId) {
                    result.add(trans);
                }
            }
        }
        result.sort(Transaction::compareByDate);
        return result;
    }
}
