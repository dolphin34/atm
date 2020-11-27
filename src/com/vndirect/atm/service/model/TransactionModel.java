package com.vndirect.atm.service.model;

import com.vndirect.atm.util.StringUtils;

import java.util.Date;

public class TransactionModel {

    public enum TransactionModelType {
        TRANSFER,
        CASH_WITHDRAWAL
    }

    private final TransactionModelType transType;
    private final long amount;
    private final long fee;
    private final Date date;
    private final String accountNumberPerform;
    private final String accountNumberTarget;

    public TransactionModel(TransactionModelType transType, long amount, long fee, Date date, String accountNumberPerform, String accountNumberTarget) {
        this.transType = transType;
        this.amount = amount;
        this.fee = fee;
        this.date = date;
        this.accountNumberPerform = accountNumberPerform;
        this.accountNumberTarget = accountNumberTarget;
    }

    public TransactionModelType getTransType() {
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

    public static int compare(TransactionModel a, TransactionModel b) {
        return b.getDate().compareTo(a.getDate());
    }

    public String toStringCashWithdrawal() {
        return transType + "\t" + String.format("%25s", "-" + StringUtils.amountToString(amount + fee)) + "\t" + StringUtils.dateToString(date);
    }

    public String toStringTransferOut() {
        return transType + "\t    " + String.format("%25s", "-" + StringUtils.amountToString(amount + fee)) + "\t" + StringUtils.dateToString(date) + "\t" + "(to account: " + accountNumberTarget + " )";
    }

    public String toStringTransferIn() {
        return transType + "\t    " + String.format("%25s", "+" + StringUtils.amountToString(amount)) + "\t" + StringUtils.dateToString(date) + "\t" + "(from account: " + accountNumberPerform + " )";
    }
}
