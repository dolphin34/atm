package com.vndirect.atm.userinterface.consoleviewimpl;

import com.vndirect.atm.controller.service.model.AccountModel;
import com.vndirect.atm.controller.service.model.CardModel;
import com.vndirect.atm.controller.service.model.TransactionModel;
import com.vndirect.atm.exception.*;
import com.vndirect.atm.userinterface.View;
import com.vndirect.atm.util.Constants;
import com.vndirect.atm.util.StringUtils;

import java.util.*;

public class ConsoleViewImpl implements View {

    private static final Scanner scanner = new Scanner(System.in);

    private CardModel currentCard;
    private AccountModel currentAccount;

    @FunctionalInterface
    interface Action {
        void run();
    }

    private static final String MULTI_DASH = "--------------------";
    private static final String STRING_TRY_AGAIN = "Try again";
    private static final String STRING_LOGOUT = "Logout";

    private String input(String notifyString) {
        System.out.println(MULTI_DASH);
        System.out.println(notifyString);
        return scanner.nextLine();
    }

    private void showOptions(String... options) {
        System.out.println(MULTI_DASH);
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
            default:
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

            showTwoNextAction(STRING_TRY_AGAIN, "Out", this::insertCard, () -> System.exit(0));
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
            showTwoNextAction(STRING_TRY_AGAIN, "Go back", () -> enterPin(timeWrong), this::insertCard);
        } catch (PinWrongException e) {
            System.out.println(e.getMessage());

            timeWrong = ++time;
            showTwoNextAction(STRING_TRY_AGAIN, "Go back", () -> enterPin(timeWrong), this::insertCard);
        } catch (LockCardException e) {
            System.out.println(e.getMessage());

            insertCard();
        }
    }

    @Override
    public void showMenu() {
        System.out.println(MULTI_DASH);
        showOptions("PIN change", "Balance inquiry", "Cash withdrawal", "Transfer", "Print statement", STRING_LOGOUT);
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
            default:
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

            showTwoNextAction(STRING_TRY_AGAIN, "Menu", this::pinChange, this::showMenu);
        }
    }

    @Override
    public void displayBalanceInquiry() {
        System.out.println(MULTI_DASH);
        System.out.println("Account number: " + currentAccount.getNumber());
        System.out.println("User: " + currentAccount.getName());
        System.out.println("Balance: " + StringUtils.amountToString(currentAccount.getAmount()));
        System.out.println("Time: " + StringUtils.dateToString(new Date()));

        showTwoNextAction("Menu", STRING_LOGOUT, this::showMenu, this::logout);
    }

    @Override
    public void showOptionsCashWithdrawal() {
        System.out.println(MULTI_DASH);
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
            default:
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
            List<Map.Entry<Integer, Integer>> result = ACCOUNT_VALIDATOR.cashWithdrawal(currentAccount.getNumber(), amount);
            getCurrentAccount();

            System.out.println("Cash withdrawal fee: " + Constants.CASH_WITHDRAWAL_FEE);
            System.out.println("Cash out: ");
            result.forEach(a -> System.out.println(StringUtils.amountToString(a.getKey()) + "--  " + a.getValue()));

            showTwoNextAction("Menu", STRING_LOGOUT, this::showMenu, this::logout);
        } catch (InvalidInputException| NotEnoughBalanceException | FailActionException  e) {
            System.out.println(e.getMessage());

            showTwoNextAction(STRING_TRY_AGAIN, "Menu", this::enterOtherAmountCashWithdrawal, this::showMenu);
        } catch (NotEnoughCashInAtmException e) {
            System.out.println(e.getMessage());

            showTwoNextAction("Menu", STRING_LOGOUT, this::showMenu, this::logout);
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

            showTwoNextAction(STRING_TRY_AGAIN, "Menu", this::enterAccountTransfer, this::showMenu);
        }
    }

    public void enterAmountTransfer(AccountModel receiveAccount) {
        System.out.println(MULTI_DASH);
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
            System.out.println("Amount transfer: " + "-" + StringUtils.amountToString(transactionModel.getAmount()));
            System.out.println("Transfer fee: " + "-" + StringUtils.amountToString(transactionModel.getFee()));
            System.out.println("Transfer time: " + StringUtils.dateToString(transactionModel.getDate()));
            System.out.println("Balance: " + StringUtils.amountToString(currentAccount.getAmount()));

            showTwoNextAction("Menu", STRING_LOGOUT, this::showMenu, this::logout);
        } catch (InvalidInputException | NotEnoughBalanceException | FailActionException e) {
            System.out.println(e.getMessage());

            showTwoNextAction(STRING_TRY_AGAIN, "Menu", () -> enterAmountTransfer(receiveAccount), this::showMenu);
        }
    }

    @Override
    public void displayStatement() {
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

        showTwoNextAction("Menu", STRING_LOGOUT, this::showMenu, this::logout);
    }

    @Override
    public void logout() {
        currentCard = null;
        currentAccount = null;
        insertCard();
    }
}
