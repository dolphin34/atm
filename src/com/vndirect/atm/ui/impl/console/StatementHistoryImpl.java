package com.vndirect.atm.ui.impl.console;

import com.vndirect.atm.service.model.AccountModel;
import com.vndirect.atm.service.model.TransactionModel;
import com.vndirect.atm.ui.StatementHistory;

public class StatementHistoryImpl extends AtmView implements StatementHistory {

    private static final StatementHistory INSTANCE = new StatementHistoryImpl();

    public static StatementHistory getView() {
        return INSTANCE;
    }

    @Override
    public void displayStatement() {
        AccountModel currentAccount = SESSION.getCurrentAccount();
        System.out.println(MULTI_DASH);
        System.out.println("List transactions :");
        for (TransactionModel transactionModel : currentAccount.getListTransactionModel()) {
            if (transactionModel.getTransType() == TransactionModel.TransactionModelType.TRANSFER) {
                if (currentAccount.getNumber().equals(transactionModel.getAccountNumberPerform())) {
                    System.out.println(transactionModel.toStringTransferOut());
                } else {
                    System.out.println(transactionModel.toStringTransferIn());
                }
            } else {
                System.out.println(transactionModel.toStringCashWithdrawal());
            }
        }

        showTwoNextAction(STRING_MENU, STRING_LOGOUT, HomeImpl.getView()::showMenu, LoginImpl.getView()::logout);
    }
}
