package com.vndirect.atm.controller.service.model;

import com.vndirect.atm.controller.repo.entity.Transaction;
import com.vndirect.atm.util.StringUtil;

import java.util.Date;

public class TransactionModel {
    private final Transaction.TransactionType transType;
    private final long amount;
    private final long fee;
    private final Date date;
    private final String accountNumberPerform;
    private final String accountNumberTarget;

    public TransactionModel(Transaction.TransactionType transType, long amount, long fee, Date date, String accountNumberPerform, String accountNumberTarget) {
        this.transType = transType;
        this.amount = amount;
        this.fee = fee;
        this.date = date;
        this.accountNumberPerform = accountNumberPerform;
        this.accountNumberTarget = accountNumberTarget;
    }

    public Transaction.TransactionType getTransType() {
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

    public String toStringCashWithdrawal() {
        return transType + "\t" + String.format("%25s", "-" + StringUtil.amountToString(amount + fee)) + "\t" + StringUtil.dateToString(date);
    }

    public String toStringTransferOut() {
        return transType + "\t    " + String.format("%25s", "-" + StringUtil.amountToString(amount + fee)) + "\t" + StringUtil.dateToString(date) + "\t" + "(to account: " + accountNumberTarget + " )";
    }

    public String toStringTransferIn() {
        return transType + "\t    " + String.format("%25s", "+" + StringUtil.amountToString(amount)) + "\t" + StringUtil.dateToString(date) + "\t" + "(from account: " + accountNumberPerform + " )";
    }
}
