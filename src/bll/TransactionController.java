package bll;

import dto.Transaction;

import java.util.List;

public interface TransactionController {
    List<Transaction> getListTransOfAccount(List<Integer> listTransId);
    boolean insertTransaction(Transaction newTransaction);
}
