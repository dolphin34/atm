package com.vndirect.atm.ui.impl.console;

import com.vndirect.atm.model.AccountModel;
import com.vndirect.atm.model.TransactionModel;
import com.vndirect.atm.ui.Amount;
import com.vndirect.atm.ui.StatementHistory;
import com.vndirect.atm.ui.impl.console.amount.VndAmountImpl;
import com.vndirect.atm.util.StringUtils;

import java.util.List;

public class StatementHistoryImpl extends AtmView implements StatementHistory {

    private static final StatementHistory INSTANCE = new StatementHistoryImpl();

    private static final Amount amountObj = new VndAmountImpl();

    public static StatementHistory getView() {
        return INSTANCE;
    }

    @Override
    public void displayStatement() {
        AccountModel currentAccount = SESSION.getCurrentAccount();
        List<TransactionModel> transactionModelList = currentAccount.getTransactions();
        transactionModelList.sort(TransactionModel::compare);

        System.out.println(MULTI_DASH);
        System.out.println("List transactions :");
        System.out.printf("%-15s \t %20s \t %10s \t %s %n", "TYPE", "AMOUNT ", "FEE", "TIME");
        for (TransactionModel t : transactionModelList) {
            String sign = "-";
            long fee = t.getFee();
            if (t.getTransactionType().equals("TRANSFER") && currentAccount.getNumber().equals(t.getAccountNumberTarget())) {
                sign = "+";
                fee = 0;
            }
            System.out.printf("%-15s \t %20s \t %10s \t %s %n", t.getTransactionType(), sign + amountObj.toString(t.getAmount()), "-" + amountObj.toString(fee), StringUtils.dateToString(t.getDate()));
        }

        showTwoNextAction(STRING_MENU, STRING_LOGOUT, HomeImpl.getView()::showMenu, LoginImpl.getView()::logout);
    }
}
