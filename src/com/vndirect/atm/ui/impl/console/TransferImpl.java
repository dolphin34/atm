package com.vndirect.atm.ui.impl.console;

import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NotEnoughBalanceException;
import com.vndirect.atm.exception.NullException;
import com.vndirect.atm.service.model.AccountModel;
import com.vndirect.atm.service.model.TransactionModel;
import com.vndirect.atm.ui.Transfer;
import com.vndirect.atm.util.Constants;
import com.vndirect.atm.util.StringUtils;

public class TransferImpl extends AtmView implements Transfer {

    private static final Transfer INSTANCE = new TransferImpl();

    public static Transfer getView() {
        return INSTANCE;
    }

    @Override
    public void enterReceivedAccountNumber() {
        String notify = "Account number receive (5 digits): ";
        String receiveAccountNumber = input(notify);
        try {
            if (VALIDATOR.isValidReceivedAccountNumber(receiveAccountNumber)) {
                enterAmountTransfer(ACCOUNT_SERVICE.findAccountModelByNumber(receiveAccountNumber));
            }
        } catch (InvalidInputException | NullException e) {
            System.out.println(e.getMessage());

            showTwoNextAction(STRING_TRY_AGAIN, "Menu", this::enterReceivedAccountNumber, this::enterReceivedAccountNumber);
        }
    }

    @Override
    public void enterAmountTransfer(AccountModel receivedAccount) {
        System.out.println(MULTI_DASH);
        System.out.println("Account receive : ");
        System.out.println("Name: " + receivedAccount.getName());
        System.out.println("Account number: " + receivedAccount.getNumber());
        String notify = "Amount transfer: ";
        String amountTransfer = input(notify);
        try {
            if (VALIDATOR.isValidAmountTransfer(amountTransfer)) {
                processTransfer(receivedAccount, Long.parseLong(amountTransfer));
            }
        } catch (InvalidInputException | NotEnoughBalanceException e) {
            System.out.println(e.getMessage());
            showTwoNextAction(STRING_TRY_AGAIN, STRING_MENU, () -> enterAmountTransfer(receivedAccount), HomeImpl.getView()::showMenu);
        }
    }

    @Override
    public void processTransfer(AccountModel receiveAccount, long amountTransfer) {
        TransactionModel transactionTransfer = ACCOUNT_SERVICE.processTransfer(SESSION.getCurrentAccount(), receiveAccount, amountTransfer);
        if (transactionTransfer != null) {
            SESSION.getCurrentAccount().setAmount(SESSION.getCurrentAccount().getAmount() - (amountTransfer + Constants.TRANSFER_FEE));
            SESSION.getCurrentAccount().addTransaction(transactionTransfer);
            displayResult(receiveAccount, transactionTransfer);
        } else {
            System.out.println("Transfer fail!");
            showTwoNextAction(STRING_TRY_AGAIN, STRING_MENU, () -> enterAmountTransfer(receiveAccount), HomeImpl.getView()::showMenu);
        }

    }

    @Override
    public void displayResult(AccountModel receiveAccount, TransactionModel transactionModel) {
        AccountModel currentAccount = SESSION.getCurrentAccount();
        System.out.println(MULTI_DASH);
        System.out.println("Transfer successfully!");
        System.out.println("Account number performed: " + currentAccount.getNumber());
        System.out.println("User name: " + currentAccount.getName());
        System.out.println("Account number received: " + receiveAccount.getNumber());
        System.out.println("User name: " + receiveAccount.getName());
        System.out.println("Amount transfer: " + "-" + StringUtils.amountToString(transactionModel.getAmount()));
        System.out.println("Transfer fee: " + "-" + StringUtils.amountToString(transactionModel.getFee()));
        System.out.println("Transfer time: " + StringUtils.dateToString(transactionModel.getDate()));
        System.out.println("Balance: " + StringUtils.amountToString(currentAccount.getAmount()));

        showTwoNextAction("Menu", STRING_LOGOUT, HomeImpl.getView()::showMenu, LoginImpl.getView()::logout);
    }
}
