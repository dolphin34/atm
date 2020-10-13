package dto;

import utils.StringUtil;

import java.util.Date;

public class Transaction {
    public enum TransactionType {
        TRANSFER,
        CASH_WITHDRAWAL
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

    public String toStringCashWithdrawal() {
        return transType + "\t" + String.format("%25s", "-" + StringUtil.amountToString(amount + fee)) + "\t" + StringUtil.dateToString(date);
    }

    public String toStringTransferOut() {
        return transType + "\t    " + String.format("%25s", "-" + StringUtil.amountToString(amount + fee)) + "\t" + StringUtil.dateToString(date) + "\t" + "(to account: " + accountNumberTarget + " )";
    }

    public String toStringTransferIn() {
        return transType + "\t    " + String.format("%25s", "+" + StringUtil.amountToString(amount)) + "\t" + StringUtil.dateToString(date) + "\t" + "(from account: " + accountNumberPerform + " )";
    }

    public static int compareByDate(Transaction transaction, Transaction otherTransaction) {
        return otherTransaction.date.compareTo(transaction.date);
    }
}
