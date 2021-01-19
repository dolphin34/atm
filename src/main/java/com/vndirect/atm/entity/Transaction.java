package com.vndirect.atm.entity;

import java.time.LocalDateTime;

public class Transaction {

    public enum TransactionType {
        TRANSFER("TRANSFER"),
        CASH_WITHDRAWAL("CASH WITHDRAWAL");

        private final String text;

        TransactionType(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    private final int id;

    private final TransactionType transactionType;

    private final long amount;

    private final long fee;

    private final LocalDateTime date;

    private final String accountNumberPerform;

    private final String accountNumberTarget;

    public Transaction(int id, TransactionType transactionType, long amount, long fee, LocalDateTime date, String accountNumberPerform, String accountNumberTarget) {
        this.id = id;
        this.transactionType = transactionType;
        this.amount = amount;
        this.fee = fee;
        this.date = date;
        this.accountNumberPerform = accountNumberPerform;
        this.accountNumberTarget = accountNumberTarget;
    }

    public int getId() {
        return id;
    }

    public TransactionType getTransactionType() {
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

}
