package bll;

import dal.TransactionDAL;
import dal.TransactionDALImpl;
import dto.Transaction;

import java.util.List;

public class TransactionControllerImpl implements TransactionController {
    private static final TransactionDAL transactionDAL = new TransactionDALImpl();

    @Override
    public List<Transaction> getListTransOfAccount(List<Integer> listTransId) {
        return transactionDAL.getListTransOfAccount(listTransId);
    }

    @Override
    public boolean insertTransaction(Transaction newTransaction) {
        return transactionDAL.insertTransaction(newTransaction);
    }
}
