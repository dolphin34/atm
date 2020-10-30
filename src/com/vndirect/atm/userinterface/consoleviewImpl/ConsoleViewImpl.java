package com.vndirect.atm.userinterface.consoleviewImpl;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.vndirect.atm.controller.service.AccountService;
import com.vndirect.atm.controller.service.model.CardModel;
import com.vndirect.atm.controller.service.serviceimpl.AccountServiceImpl;
import com.vndirect.atm.controller.service.CardService;
import com.vndirect.atm.controller.service.serviceimpl.CardServiceImpl;
import com.vndirect.atm.controller.repo.entity.Account;
import com.vndirect.atm.controller.repo.entity.Transaction;
import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.LockCardException;
import com.vndirect.atm.exception.NullCardException;
import com.vndirect.atm.exception.PinWrongException;
import com.vndirect.atm.userinterface.View;
import com.vndirect.atm.util.StringUtil;

public class ConsoleViewImpl implements View {

    private static final Scanner scanner = new Scanner(System.in);
    private static final CardService CARD_SERVICE = new CardServiceImpl();
    private static final AccountService ACCOUNT_SERVICE = new AccountServiceImpl();

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

    private String enterNumericString() {
        String numericString = StringUtil.formatNumericString(scanner.nextLine());
//        if (!StringUtil.isNumericString(numericString)) {
//            return "";
//        }
        return numericString;
    }

    @Override
    public void insertCard() {
        String notify = "Card number (8 digit and start with 0800): ";
        String cardNumber = input(notify);
        try {
            CARD_VALIDATOR.checkCardNumber(cardNumber);
        } catch (InvalidInputException | NullCardException | LockCardException e) {
            System.out.println(e.getMessage());

            showOptions("Try again", "Out");
            int choice = enterChoiceOfAction(2);
            switch (choice) {
                case 1:
                    insertCard();
                    break;
                case 2:
                    System.exit(0);
                    break;
            }
        }
    }

    @Override
    public void enterPin(CardModel cardModel, int time) {
        String notify = "Hello, " + cardModel.getName() + "\nPin (6 digits): ";
        String pin = input(notify);

        try {
            CARD_VALIDATOR.checkPin(pin, time);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());


        } catch (LockCardException e) {
            System.out.println(e.getMessage());
            insertCard();
        } catch (PinWrongException e) {
            System.out.println(e.getMessage());
            time++;
        }
        showOptions("Try again", "Go back");
        int choice = enterChoiceOfAction(2);
        switch (choice) {
            case 1:
                enterPin(cardModel, time);
                break;
            case 2:
                insertCard();
                break;
        }
    }

    @Override
    public void home() {
        System.out.println("-------------------------");
        System.out.println("1.PIN change");
        System.out.println("2.Balance inquiry");
        System.out.println("3.Cash withdrawal");
        System.out.println("4.Transfer");
        System.out.println("5.Print statement");
        System.out.println("6.Logout");
        int choice = enterChoiceOfAction(6);
        switch (choice) {
            case 1:
                pinChange();
                break;
            case 2:
                balanceInquiry();
                break;
            case 3:
                cashWithdrawal();
                break;
            case 4:
                enterAccountTransfer();
                break;
            case 5:
                printStatement();
                break;
            case 6:
                logout();
                break;
        }
    }

    @Override
    public void pinChange(String... messages) {
        System.out.println("-------------------------");
        StringUtil.printMessages(messages);
        System.out.println("(Enter 0 to back to list options)");
        System.out.println("Enter your new pin (6 digits): ");
        String newPin = enterNumericString();
        if (newPin.equals("0"))
            home();
        else {
            boolean isValidNewPin = newPin.length() == 6;
            if (isValidNewPin) {
                CARD_SERVICE.pinChange(newPin);
            } else {
                System.out.println("Invalid pin!");
                pinChange();
            }
        }
    }

    public void balanceInquiry() {
        ACCOUNT_SERVICE.balanceInquiry();
    }

    @Override
    public void displayBalanceInquiry(Account account) {
        System.out.println("-------------------------");
        System.out.println("Account number: " + account.getNumber());
        System.out.println("User: " + account.getName());
        System.out.println("Balance: " + StringUtil.amountToString(account.getAmount()));
        System.out.println("Time: " + StringUtil.dateToString(new Date()));
        nextAction();
    }

    @Override
    public void cashWithdrawal() {
        System.out.println("-------------------------");
        System.out.println("1. 200 000");
        System.out.println("2. 500 000");
        System.out.println("3. 1 000 000");
        System.out.println("4. 1 500 000");
        System.out.println("5. 2 000 000");
        System.out.println("6. Other ");
        int choice = enterChoiceOfAction(6);
        switch (choice) {
            case 1:
                ACCOUNT_SERVICE.cashWithdrawal(200_000);
                break;
            case 2:
                ACCOUNT_SERVICE.cashWithdrawal(500_00);
                break;
            case 3:
                ACCOUNT_SERVICE.cashWithdrawal(1_000_000);
                break;
            case 4:
                ACCOUNT_SERVICE.cashWithdrawal(1_500_000);
                break;
            case 5:
                ACCOUNT_SERVICE.cashWithdrawal(2_000_000);
                break;
            case 6:
                enterOtherAmountCashWithdrawal();
                break;
        }
    }

    @Override
    public void enterOtherAmountCashWithdrawal(String... messages) {
        System.out.println("-------------------------");
        StringUtil.printMessages(messages);
        System.out.println("(Enter 0 to back to list options)");
        System.out.println("Enter number amount (multiples of 50,000): ");
        String number = enterNumericString();
        if (number.equals("0"))
            home();
        else {
            if (isValidOtherAmountCashWithdrawal(number)) {
                ACCOUNT_SERVICE.cashWithdrawal(Long.parseLong(number));
            } else {
                System.out.println("Invalid amount number!");
                enterOtherAmountCashWithdrawal();
            }
        }
    }

    private boolean isValidOtherAmountCashWithdrawal(String number) {
        return !number.equals("") && Long.parseLong(number) > 0 && Long.parseLong(number) % 50000 == 0;
    }

    @Override
    public void displayResultCashWithdrawal(boolean success, int[][] result, Transaction transaction, String... messages) {
        System.out.println("-------------------------");
        StringUtil.printMessages(messages);
        if (success) {
            System.out.println("Cash withdrawal fee : " + StringUtil.amountToString(transaction.getFee()));
            System.out.println("Cash out: " + StringUtil.amountToString(transaction.getAmount()));
            for (int i = 3; i >= 0; i--) {
                if (result[2][i] != 0) {
                    System.out.println(result[2][i] + " - " + StringUtil.amountToString(result[0][i]));
                }
            }
        }
        nextAction();
    }

    @Override
    public void enterAccountTransfer(String... messages) {
        System.out.println("-------------------------");
        StringUtil.printMessages(messages);
        System.out.println("(Enter 0 to back to list options)");
        System.out.println("Account number receive (5 digits):");
        String accountNumber = enterNumericString();
        if (accountNumber.equals("0"))
            home();
        else {
            boolean isValid = accountNumber.length() == 5;
            if (isValid) {
                ACCOUNT_SERVICE.checkReceiveAccount(accountNumber);
            } else {
                String message = "Invalid account number!";
                enterAccountTransfer(message);
            }
        }
    }

    @Override
    public void enterAmountTransfer(String accountNumberReceive, String accountNameReceive, String... messages) {
        System.out.println("-------------------------");
        StringUtil.printMessages(messages);
        System.out.println("Account receive : ");
        System.out.println("Name: " + accountNameReceive);
        System.out.println("Account number: " + accountNumberReceive + "\n");
        System.out.println("(Enter 0 to back to list options)");
        System.out.println("Enter number amount transfer: ");
        String amountTransfer = enterNumericString();
        if (amountTransfer.equals("0"))
            home();
        else {
            boolean isValid = !amountTransfer.equals("") &&  Long.parseLong(amountTransfer) > 0;
            if (isValid) {
                ACCOUNT_SERVICE.transfer(accountNumberReceive, Long.parseLong(amountTransfer));
            } else {
                String message = "Invalid amount number!";
                enterAmountTransfer(accountNumberReceive, accountNameReceive, message);
            }
        }
    }

    @Override
    public void displayResultTransfer(boolean success, String message, Account currentAccount, Transaction transaction) {
        System.out.println("-------------------------");
        System.out.println(message);
        if (success) {
            System.out.println("Account number: " + currentAccount.getNumber());
            System.out.println("User: " + currentAccount.getName());
            System.out.println("Account number received: " + transaction.getAccountNumberTarget());
            System.out.println("Amount transfer: " + "-" + StringUtil.amountToString(transaction.getAmount()));
            System.out.println("Transfer fee: " + "-" + StringUtil.amountToString(transaction.getFee()));
            System.out.println("Transfer time: " + StringUtil.dateToString(transaction.getDate()));
            System.out.println("Balance: " + StringUtil.amountToString(currentAccount.getAmount()));
        }
        nextAction();
    }

    public void printStatement() {
        ACCOUNT_SERVICE.printStatement();
    }

    @Override
    public void displayStatement(Account account, List<Transaction> listTransaction) {
        System.out.println("-------------------------");
        System.out.println("List transactions :");
        for (Transaction transaction : listTransaction) {
            if (transaction.getTransType() == Transaction.TransactionType.TRANSFER)
                if (account.getNumber().equals(transaction.getAccountNumberPerform()))
                    System.out.println(transaction.toStringTransferOut());
                else
                    System.out.println(transaction.toStringTransferIn());
            else
                System.out.println(transaction.toStringCashWithdrawal());
        }
        nextAction();
    }

    private void logout() {
        CARD_SERVICE.logout();
    }



    private void nextAction() {
        System.out.println("-----");
        System.out.println("1.Back");
        System.out.println("2.Logout");
        int choice = enterChoiceOfAction(2);
        switch (choice) {
            case 1:
                home();
                break;
            case 2:
                logout();
                break;
        }
    }

    private int enterChoiceOfAction(int quantityChoices) {
        int choice;
        do {
            System.out.print("Your choice: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice > quantityChoices || choice < 1)
                    System.out.println("(Enter a number in list options)");
            } catch (InputMismatchException e) {
                scanner.nextLine();
                choice = -1;
                System.out.println("(Enter a number in list options)");
            }
        } while (choice > quantityChoices || choice < 1);
        return choice;
    }
}
