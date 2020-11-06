package com.vndirect.atm.userinterface.consoleviewImpl;

import com.vndirect.atm.controller.service.model.AccountModel;
import com.vndirect.atm.controller.service.model.CardModel;
import com.vndirect.atm.controller.service.model.TransactionModel;
import com.vndirect.atm.exception.*;
import com.vndirect.atm.userinterface.View;
import com.vndirect.atm.util.Constants;
import com.vndirect.atm.util.StringUtil;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleViewImpl implements View {

    private static final Scanner scanner = new Scanner(System.in);

    public static CardModel currentCard;
    public static AccountModel currentAccount;

    @FunctionalInterface
    interface Action {
        void run();
    }

    private String input(String notifyString) {
        System.out.println("-------------------------");
        System.out.println(notifyString);
        return scanner.nextLine();
    }

    private void showOptions(String... options) {
        System.out.println("----------");
        System.out.println("Options :");
        for (int i = 0; i < options.length; i++) {
            System.out.println(i+1 + ". " + options[i]);
        }
    }

    private int enterChoiceOfAction(int quantityChoices) {
        int choice;
        do {
            System.out.print("Your choice: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice > quantityChoices || choice < 1) {
                    System.out.println("(Enter a number in list options)");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                choice = -1;
                System.out.println("(Enter a number in list options)");
            }
        } while (choice > quantityChoices || choice < 1);
        return choice;
    }

    private void showTwoNextAction(String nameOfAction1, String nameOfAction2, Action action1, Action action2) {
        showOptions(nameOfAction1, nameOfAction2);
        int choice = enterChoiceOfAction(2);
        switch (choice) {
            case 1:
                action1.run();
                break;
            case 2:
                action2.run();
                break;
        }
    }

    private void getCurrentAccount() throws FailActionException {
        try {
            currentAccount = ACCOUNT_VALIDATOR.getAccountByNumber(currentCard.getAccountNumber());
        } catch (NullException e) {
            throw new FailActionException(e.getMessage());
        }
    }

    @Override
    public void insertCard() {
        String notify = "Card number (8 digit and start with 0800): ";
        String cardNumber = input(notify);
        try {
            currentCard = CARD_VALIDATOR.checkCardNumber(cardNumber);
            enterPin(1);
        } catch (InvalidInputException | NullException | LockCardException e) {
            System.out.println(e.getMessage());

            showTwoNextAction("Try again", "Out", this::insertCard, () -> System.exit(0));
        }
    }

    @Override
    public void enterPin(int time) {
        String notify = "Hello, " + currentCard.getName() + "\nPin (6 digits): ";
        String pin = input(notify);
        int timeWrong;
        try {
            CARD_VALIDATOR.checkPin(currentCard.getNumber(), pin, time);
            getCurrentAccount();
            showMenu();
        } catch (InvalidInputException | FailActionException e) {
            System.out.println(e.getMessage());

            timeWrong = time;
            showTwoNextAction("Try again", "Go back", () -> enterPin(timeWrong), this::insertCard);
        } catch (PinWrongException e) {
            System.out.println(e.getMessage());

            timeWrong = ++time;
            showTwoNextAction("Try again", "Go back", () -> enterPin(timeWrong), this::insertCard);
        } catch (LockCardException e) {
            System.out.println(e.getMessage());

            insertCard();
        }
    }

    @Override
    public void showMenu() {
        System.out.println("-------------------------");
        showOptions("PIN change", "Balance inquiry", "Cash withdrawal", "Transfer", "Print statement", "Logout");
        int choice = enterChoiceOfAction(6);
        switch (choice) {
            case 1:
                pinChange();
                break;
            case 2:
                displayBalanceInquiry();
                break;
            case 3:
                showOptionsCashWithdrawal();
                break;
            case 4:
                enterAccountTransfer();
                break;
            case 5:
                displayStatement();
                break;
            case 6:
                logout();
                break;
        }
    }

    @Override
    public void pinChange() {
        String notify = "Enter your new pin (6 digits): ";
        String newPin = input(notify);

        try {
            CARD_VALIDATOR.pinChange(currentCard.getNumber(), newPin);
            System.out.println("Pin change success! Login again!");
            enterPin(1);
        } catch (InvalidInputException | FailActionException e) {
            System.out.println(e.getMessage());

            showTwoNextAction("Try again", "Menu", this::pinChange, this::showMenu);
        }
    }

    @Override
    public void displayBalanceInquiry() {
        System.out.println("-------------------------");
        System.out.println("Account number: " + currentAccount.getNumber());
        System.out.println("User: " + currentAccount.getName());
        System.out.println("Balance: " + StringUtil.amountToString(currentAccount.getAmount()));
        System.out.println("Time: " + StringUtil.dateToString(new Date()));

        showTwoNextAction("Menu", "Logout", this::showMenu, this::logout);
    }

    @Override
    public void showOptionsCashWithdrawal() {
        System.out.println("-------------------------");
        showOptions("200 000", "500 000", "1 000 000", "1 500 000", "2 000 000", "Other number");
        int choice = enterChoiceOfAction(6);
        switch (choice) {
            case 1:
                cashWithdrawal("200 000");
                break;
            case 2:
                cashWithdrawal("500 000");
                break;
            case 3:
                cashWithdrawal("1 000 000");
                break;
            case 4:
                cashWithdrawal("1 500 000");
                break;
            case 5:
                cashWithdrawal("2 000 000");
                break;
            case 6:
                enterOtherAmountCashWithdrawal();
                break;
        }
    }

    @Override
    public void enterOtherAmountCashWithdrawal() {
        String notify = "Enter number amount (multiples of 50,000): ";
        String amount = input(notify);
        cashWithdrawal(amount);
    }

    @Override
    public void cashWithdrawal(String amount) {
        try {
            int[][] result = ACCOUNT_VALIDATOR.cashWithdrawal(currentAccount.getNumber(), amount);
            getCurrentAccount();

            System.out.println("Cash withdrawal fee: " + Constants.CASH_WITHDRAWAL_FEE);
            System.out.println("Cash out: ");
            for (int i = 0; i < 4; i++) {
                if (result[2][i] != 0) {
                    System.out.println(result[2][i] + " - " + StringUtil.amountToString(result[0][i]));
                }
            }

            showTwoNextAction("Menu", "Logout", this::showMenu, this::logout);
        } catch (InvalidInputException| NotEnoughBalanceException | FailActionException  e) {
            System.out.println(e.getMessage());

            showTwoNextAction("Try again", "Menu", this::enterOtherAmountCashWithdrawal, this::showMenu);
        } catch (NotEnoughCashInAtmException e) {
            System.out.println(e.getMessage());

            showTwoNextAction("Menu", "Logout", this::showMenu, this::logout);
        }
    }

    @Override
    public void enterAccountTransfer() {
        String notify = "Account number receive (5 digits): ";
        String accountNumber = input(notify);
        try {
            AccountModel receiveAccount = ACCOUNT_VALIDATOR.checkReceiveAccountNumber(currentAccount.getNumber(), accountNumber);
            enterAmountTransfer(receiveAccount);
        } catch (InvalidInputException | NullException e) {
            System.out.println(e.getMessage());

            showTwoNextAction("Try again", "Menu", this::enterAccountTransfer, this::showMenu);
        }
    }

    public void enterAmountTransfer(AccountModel receiveAccount) {
        System.out.println("-------------------------");
        System.out.println("Account receive : ");
        System.out.println("Name: " + receiveAccount.getName());
        System.out.println("Account number: " + receiveAccount.getNumber());
        String notify = "Amount transfer: ";
        String amountTransfer = input(notify);

        transfer(receiveAccount, amountTransfer);
    }

    @Override
    public void transfer(AccountModel receiveAccount, String amountTransfer) {
        try {
            TransactionModel transactionModel = ACCOUNT_VALIDATOR.transfer(currentAccount.getNumber(), receiveAccount.getNumber(), amountTransfer);
            getCurrentAccount();

            System.out.println("-------------------------");
            System.out.println("Transfer successfully!");
            System.out.println("Account number performed: " + currentAccount.getNumber());
            System.out.println("User name: " + currentAccount.getName());
            System.out.println("Account number received: " + receiveAccount.getNumber());
            System.out.println("User name: " + receiveAccount.getName());
            System.out.println("Amount transfer: " + "-" + StringUtil.amountToString(transactionModel.getAmount()));
            System.out.println("Transfer fee: " + "-" + StringUtil.amountToString(transactionModel.getFee()));
            System.out.println("Transfer time: " + StringUtil.dateToString(transactionModel.getDate()));
            System.out.println("Balance: " + StringUtil.amountToString(currentAccount.getAmount()));

            showTwoNextAction("Menu", "Logout", this::showMenu, this::logout);
        } catch (InvalidInputException | NotEnoughBalanceException | FailActionException e) {
            System.out.println(e.getMessage());

            showTwoNextAction("Try again", "Menu", () -> enterAmountTransfer(receiveAccount), this::showMenu);
        }
    }

    @Override
    public void displayStatement() {
        System.out.println("-------------------------");
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

        showTwoNextAction("Menu", "Logout", this::showMenu, this::logout);
    }

    @Override
    public void logout() {
        currentCard = null;
        currentAccount = null;
        insertCard();
    }
}
