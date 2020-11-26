package com.vndirect.atm.ui.impl.console;

import com.vndirect.atm.service.model.AccountModel;
import com.vndirect.atm.ui.Transfer;

public class TransferImpl implements Transfer {
    @Override
    public void enterAccountTransfer() {
//        String notify = "Account number receive (5 digits): ";
//        String accountNumber = input(notify);
//        try {
//            AccountModel receiveAccount = ACCOUNT_VALIDATOR.checkReceiveAccountNumber(currentAccount.getNumber(), accountNumber);
//            enterAmountTransfer(receiveAccount);
//        } catch (InvalidInputException | NullException e) {
//            System.out.println(e.getMessage());
//
//            showTwoNextAction(STRING_TRY_AGAIN, "Menu", this::enterAccountTransfer, this::showMenu);
//        }
    }

    @Override
    public void enterOtherAmountCashWithdrawal() {
//        System.out.println(MULTI_DASH);
//        System.out.println("Account receive : ");
//        System.out.println("Name: " + receiveAccount.getName());
//        System.out.println("Account number: " + receiveAccount.getNumber());
//        String notify = "Amount transfer: ";
//        String amountTransfer = input(notify);
//
//        transfer(receiveAccount, amountTransfer);
    }

    @Override
    public void transfer(AccountModel receiveAccount, String amountTransfer) {
//        try {
//            TransactionModel transactionModel = ACCOUNT_VALIDATOR.transfer(currentAccount.getNumber(), receiveAccount.getNumber(), amountTransfer);
//            getCurrentAccount();
//
//            System.out.println("-------------------------");
//            System.out.println("Transfer successfully!");
//            System.out.println("Account number performed: " + currentAccount.getNumber());
//            System.out.println("User name: " + currentAccount.getName());
//            System.out.println("Account number received: " + receiveAccount.getNumber());
//            System.out.println("User name: " + receiveAccount.getName());
//            System.out.println("Amount transfer: " + "-" + StringUtils.amountToString(transactionModel.getAmount()));
//            System.out.println("Transfer fee: " + "-" + StringUtils.amountToString(transactionModel.getFee()));
//            System.out.println("Transfer time: " + StringUtils.dateToString(transactionModel.getDate()));
//            System.out.println("Balance: " + StringUtils.amountToString(currentAccount.getAmount()));
//
//            showTwoNextAction("Menu", STRING_LOGOUT, this::showMenu, this::logout);
//        } catch (InvalidInputException | NotEnoughBalanceException | FailActionException e) {
//            System.out.println(e.getMessage());
//
//            showTwoNextAction(STRING_TRY_AGAIN, "Menu", () -> enterAmountTransfer(receiveAccount), this::showMenu);
//        }
    }
}
