package com.vndirect.atm.model;

import java.time.LocalDateTime;

public class TransactionModel {

    private final String transactionType;

    private final long amount;

    private final long fee;

    private final LocalDateTime date;

    private final String accountNumberPerform;

    private final String accountNumberTarget;

    public TransactionModel(String transactionType, long amount, long fee, LocalDateTime date, String accountNumberPerform, String accountNumberTarget) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.fee = fee;
        this.date = date;
        this.accountNumberPerform = accountNumberPerform;
        this.accountNumberTarget = accountNumberTarget;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public long getAmount() {
        return amount;
    }

    public long getFee() {
        return fee;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getAccountNumberPerform() {
        return accountNumberPerform;
    }

    public String getAccountNumberTarget() {
        return accountNumberTarget;
    }

    public static int compare(TransactionModel a, TransactionModel b) {
        return b.getDate().compareTo(a.getDate());
    }
}
