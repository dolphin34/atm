package dal;

import dto.Transaction;

import java.util.List;

public interface TransactionDAL {
    boolean insertTransaction(Transaction transaction);
    List<Transaction> getListTransOfAccount(List<Integer> listTransId);
}
