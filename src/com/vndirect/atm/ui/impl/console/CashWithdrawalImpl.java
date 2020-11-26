package com.vndirect.atm.ui.impl.console;

import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NotEnoughBalanceException;
import com.vndirect.atm.exception.NotEnoughCashInAtmException;
import com.vndirect.atm.ui.CashWithdrawal;
import com.vndirect.atm.util.Constants;
import com.vndirect.atm.util.StringUtils;

import java.util.List;
import java.util.Map;

public class CashWithdrawalImpl extends AtmView implements CashWithdrawal {

    private static final CashWithdrawal INSTANCE = new CashWithdrawalImpl();

    public static CashWithdrawal getView() {
        return INSTANCE;
    }

    @Override
    public void showOptionsOfCash() {
        System.out.println(MULTI_DASH);
        showOptions("200 000", "500 000", "1 000 000", "1 500 000", "2 000 000", "Other number");
        int choice = enterChoiceOfAction(6);
        switch (choice) {
            case 1:
                processCashWithdrawal(200_000);
                break;
            case 2:
                processCashWithdrawal(500_000);
                break;
            case 3:
                processCashWithdrawal(1_000_000);
                break;
            case 4:
                processCashWithdrawal(1_500_000);
                break;
            case 5:
                processCashWithdrawal(2_000_000);
                break;
            case 6:
                enterOtherAmount();
                break;
            default:
                break;
        }
    }

    @Override
    public void enterOtherAmount() {
        String notify = "Enter number amount (multiples of 50,000): ";
        String amount = input(notify);
        try {
            if (VALIDATOR.isValidAmountCashWithdrawal(amount)) {
                processCashWithdrawal(Long.parseLong(amount));
            }
        } catch (InvalidInputException | NotEnoughBalanceException e) {
            System.out.println(e.getMessage());
            showTwoNextAction(STRING_TRY_AGAIN, STRING_MENU, this::enterOtherAmount, HomeImpl.getView()::showMenu);
        }
    }

    @Override
    public void processCashWithdrawal(long amount) {
        try {
            CashInAtm.checkCashInAtm(amount);
            SESSION.setCurrentAccount(ACCOUNT_SERVICE.processCashWithdrawal(SESSION.getCurrentAccount(), amount));
            displayResult(CashInAtm.getCashOut(amount));
        } catch (NotEnoughCashInAtmException e) {
            System.out.println(e.getMessage());
            showTwoNextAction(STRING_TRY_AGAIN, STRING_MENU, this::enterOtherAmount, HomeImpl.getView()::showMenu);
        }
    }

    @Override
    public void displayResult(List<Map.Entry<Integer, Integer>> result) {
        System.out.println("Cash withdrawal fee: " + Constants.CASH_WITHDRAWAL_FEE);
        System.out.println("Cash out: ");
        result.forEach(a -> System.out.println(StringUtils.amountToString(a.getKey()) + "--  " + a.getValue()));
        showTwoNextAction(STRING_MENU, STRING_LOGOUT, HomeImpl.getView()::showMenu, LoginImpl.getView()::logout);
    }
}
