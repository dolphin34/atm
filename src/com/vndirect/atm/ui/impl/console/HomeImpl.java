package com.vndirect.atm.ui.impl.console;

import com.vndirect.atm.ui.Home;

public class HomeImpl extends AtmView implements Home {

    private static final Home INSTANCE = new HomeImpl();

    public static Home getView() {
        return INSTANCE;
    }

    @Override
    public void showMenu() {
        showOptions("PIN change", "Balance inquiry", "Cash withdrawal", "Transfer", "Print statement", STRING_LOGOUT);
        int choice = enterChoiceOfAction(6);
        switch (choice) {
            case 1:
                ChangPinImpl.getView().enterOldPin();
                break;
            case 2:
                BalanceInquiryImpl.getView().displayBalanceInquiry();
                break;
            case 3:
                CashWithdrawalImpl.getView().showOptionsOfCash();
                break;
            case 4:
                TransferImpl.getView().enterReceivedAccountNumber();
                break;
            case 5:
                StatementHistoryImpl.getView().displayStatement();
                break;
            case 6:
                LoginImpl.getView().logout();
                break;
            default:
                break;
        }
    }
}
