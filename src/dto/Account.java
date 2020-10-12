package dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Account {
    private final String number;
    private final String name;
    private long amount;
    private final List<Integer> listTransactionsId;

    public Account(String number, String name, long amount, Integer... listTransactionId) {
        this.number = number;
        this.name = name;
        this.amount = amount;
        this.listTransactionsId = new ArrayList<>();
        this.listTransactionsId.addAll(Arrays.asList(listTransactionId));
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void addTransaction(int transactionId) {
        listTransactionsId.add(transactionId);
    }

    public List<Integer> getListTransactionsId() {
        return listTransactionsId;
    }
}
