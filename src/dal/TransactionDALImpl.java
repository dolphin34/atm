package dal;

import database.Data;
import dto.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionDALImpl implements TransactionDAL {
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
