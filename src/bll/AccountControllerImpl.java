package bll;

import dal.AccountDAL;
import dal.AccountDALImpl;
import database.Data;
import dto.Account;
import dto.Transaction;
import ui.ViewsImpl;
import ui.Views;
import utils.Constants;

import java.util.Date;
import java.util.List;

public class AccountControllerImpl implements AccountController {
    private static Account currentAccount;

    private static final Views views = new ViewsImpl();
    private static final AccountDAL accountDAL = new AccountDALImpl();
    private static final TransactionController transactionController = new TransactionControllerImpl();

    @Override
    public boolean findByAccountNumber(String accountNumber) {
        return accountDAL.findByAccountNumber(accountNumber) != null;
    }

    @Override
    public void getLinkedAccountWithCard(String accountNumber) {
        currentAccount = accountDAL.findByAccountNumber(accountNumber);
    }

    @Override
    public void removeLinkedAccountWithCard() {
        currentAccount = null;
    }

    @Override
    public void balanceInquiry() {
        views.displayBalanceInquiry(currentAccount);
    }

    @Override
    public void cashWithdrawal(long amountWithdrawal) {
        String message;
        boolean isValidAmount = amountWithdrawal <= currentAccount.getAmount() - Constants.CASH_50;
        if (isValidAmount) {
            int[][] cashOut = payOutCash(amountWithdrawal);
            if (cashOut != null) {
                Transaction newTransaction = new Transaction(Data.listTransaction.size() + 1, Transaction.TransactionType.CASH_WITHDRAWAL,
                        amountWithdrawal, new Date(), currentAccount.getNumber(), null);
                boolean withdrawalSuccess = transactionController.insertTransaction(newTransaction) && accountDAL.updateInfoAccount(currentAccount.getNumber(), currentAccount.getAmount() - amountWithdrawal, newTransaction.getId());
                if (withdrawalSuccess) {
                    currentAccount = accountDAL.findByAccountNumber(currentAccount.getNumber());
                    message = "Successfully!";
                    views.displayResultCashWithdrawal(true, message, cashOut);
                } else {
                    message = "An Error Occurred!";
                    views.displayResultCashWithdrawal(false, message, null);
                }
            } else {
                message = "Not enough money in ATM!";
                views.displayResultCashWithdrawal(false, message, null);
            }
        } else {
            message = "Unavailable Balance!";
            views.displayResultCashWithdrawal(false, message, null);
        }

    }

    public int[][] payOutCash(long amount) {
        int[][] a = new int[3][4];
        if (amount <= Data.CashInAtm.sumOfCash()) {
            a[0][0] = Data.CashInAtm.CASH_IN_ATM_50.getValue();
            a[0][1] = Data.CashInAtm.CASH_IN_ATM_100.getValue();
            a[0][2] = Data.CashInAtm.CASH_IN_ATM_200.getValue();
            a[0][3] = Data.CashInAtm.CASH_IN_ATM_500.getValue();

            a[1][0] = Data.CashInAtm.CASH_IN_ATM_50.getQuantity();
            a[1][1] = Data.CashInAtm.CASH_IN_ATM_100.getQuantity();
            a[1][2] = Data.CashInAtm.CASH_IN_ATM_200.getQuantity();
            a[1][3] = Data.CashInAtm.CASH_IN_ATM_500.getQuantity();
            for (int i = 3; i >= 0; i--) {
                if (amount == 0) break;
                if (a[1][i] == 0) continue;
                a[2][i] = (int) amount / a[0][i];
                //
                if (a[2][i] > a[1][i]) {
                    a[2][i] = a[1][i];
                }
                a[1][i] = a[1][i] - a[2][i];
                //
                amount = amount - (a[0][i] * a[2][i]);
            }
            if (amount != 0)
                return null;

            Data.CashInAtm.CASH_IN_ATM_50.setQuantity(a[1][0]);
            Data.CashInAtm.CASH_IN_ATM_100.setQuantity(a[1][1]);
            Data.CashInAtm.CASH_IN_ATM_200.setQuantity(a[1][2]);
            Data.CashInAtm.CASH_IN_ATM_500.setQuantity(a[1][3]);
            return a;
        } else return null;
    }

    @Override
    public boolean checkReceiveAccount(String accountNumberReceive) {
        return findByAccountNumber(accountNumberReceive) && !accountNumberReceive.equals(currentAccount.getNumber());
    }

    @Override
    public void transfer(String accountNumberReceive, long amountTransfer) {
        boolean isValidAmount = amountTransfer <= currentAccount.getAmount() - Constants.CASH_50;
        String message;
        if (isValidAmount) {
            Transaction newTransaction = new Transaction(Data.listTransaction.size() + 1, Transaction.TransactionType.TRANSFER,
                    amountTransfer, new Date(), currentAccount.getNumber(), accountNumberReceive);
            boolean transferSuccess = transactionController.insertTransaction(newTransaction)
                    && accountDAL.updateInfoAccount(currentAccount.getNumber(), currentAccount.getAmount() - amountTransfer, newTransaction.getId())
                    && accountDAL.updateInfoAccount(accountNumberReceive, accountDAL.findByAccountNumber(accountNumberReceive).getAmount() + amountTransfer, newTransaction.getId());
            if (transferSuccess) {
                currentAccount = accountDAL.findByAccountNumber(currentAccount.getNumber());
                message = "Transfer Successfully!";
                views.displayResultTransfer(true, message, currentAccount, accountNumberReceive, amountTransfer);
            } else {
                message = "An Error Occurred!";
                views.displayResultTransfer(false, message, currentAccount, accountNumberReceive, amountTransfer);
            }
        } else {
            message = "Unavailable Balance!";
            views.displayResultTransfer(false, message, currentAccount, accountNumberReceive, amountTransfer);
        }
    }

    @Override
    public void printStatement() {
        List<Integer> listTransId = accountDAL.getListTransactionId(currentAccount.getNumber());
        List<Transaction> listTransaction = transactionController.getListTransOfAccount(listTransId);
        views.displayStatement(currentAccount, listTransaction);
    }


}
