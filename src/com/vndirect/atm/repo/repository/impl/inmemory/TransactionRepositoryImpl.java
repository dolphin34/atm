package com.vndirect.atm.repo.repository.impl.inmemory;

import com.vndirect.atm.repo.entity.Transaction;
import com.vndirect.atm.repo.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class TransactionRepositoryImpl implements TransactionRepository {

    private static final TransactionRepositoryImpl INSTANCE = new TransactionRepositoryImpl();
    private static final List<Transaction> listTransaction = new ArrayList<>();
    private static int sizeOfListTransaction = 0;

    public static TransactionRepositoryImpl getInstance() {
        return INSTANCE;
    }

    public int getSizeOfListTransaction() {
        return sizeOfListTransaction;
    }

    public static void incrementSizeOfListTransaction() {
        sizeOfListTransaction++;
    }

    @Override
    public boolean insertTransaction(Transaction transaction) {
        incrementSizeOfListTransaction();
        return listTransaction.add(transaction);
    }

    @Override
    public List<Transaction> getListTransactionByListId(List<Integer> listTransId) {
        List<Transaction> result = new ArrayList<>();
        for (Integer transId : listTransId) {
            for (Transaction trans : listTransaction) {
                if (trans.getId() == transId) {
                    result.add(trans);
                }
            }
        }
        return result;
    }
}
