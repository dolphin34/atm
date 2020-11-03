package com.vndirect.atm.controller.repo.entity;

import java.util.Date;

public class Transaction {
    public enum TransactionType {
        TRANSFER(1),
        CASH_WITHDRAWAL(2);

        private final int code;

        TransactionType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    private final int id;
    private final TransactionType transType;
    private final long amount;
    private final long fee;
    private final Date date;
    private final String accountNumberPerform;
    private final String accountNumberTarget;

    public Transaction(int id, TransactionType transType, long amount, long fee, Date date, String accountNumberPerform, String accountNumberTarget) {
        this.id = id;
        this.transType = transType;
        this.amount = amount;
        this.fee = fee;
        this.date = date;
        this.accountNumberPerform = accountNumberPerform;
        this.accountNumberTarget = accountNumberTarget;
    }

    public int getId() {
        return id;
    }

    public TransactionType getTransType() {
        return transType;
    }

    public long getAmount() {
        return amount;
    }

    public long getFee() {
        return fee;
    }

    public Date getDate() {
        return date;
    }

    public String getAccountNumberPerform() {
        return accountNumberPerform;
    }

    public String getAccountNumberTarget() {
        return accountNumberTarget;
    }

    public static int compareByDate(Transaction transaction, Transaction otherTransaction) {
        return otherTransaction.date.compareTo(transaction.date);
    }
}
