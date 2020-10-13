package ui;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import bll.AccountController;
import bll.AccountControllerImpl;
import bll.CardController;
import bll.CardControllerImpl;
import dto.Account;
import dto.Transaction;
import utils.Constants;
import utils.StringUtil;

public class ViewsImpl implements Views {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CardController cardController = new CardControllerImpl();
    private static final AccountController accountController = new AccountControllerImpl();

    @Override
    public void insertCard(String... messages) {
        System.out.println("-------------------------");
        StringUtil.printMessages(messages);
        System.out.println("Card number (8 digit and start with 0800): ");
        String cardNumber = scanner.nextLine();
        cardNumber = StringUtil.formatNumericString(cardNumber);
        boolean isValid = cardNumber.length() == 8 && cardNumber.startsWith("0800") && StringUtil.isNumericString(cardNumber);
        if (isValid) {
            cardController.checkCard(cardNumber);
        } else {
            System.out.println("Invalid card number!");
            insertCard();
        }
    }

    @Override
    public void enterPin(String userName, int time, String... message) {
        System.out.println("-------------------------");
        StringUtil.printMessages(message);
        System.out.println("Hello, " + userName);
        System.out.println("Pin (6 digits): ");
        String pin = scanner.nextLine();
        pin = StringUtil.formatNumericString(pin);
        boolean isValid = pin.length() == 6 && StringUtil.isNumericString(pin);
        if (isValid) {
            cardController.checkPin(pin, time);
        } else {
            System.out.println("Invalid pin!");
            enterPin(userName, time);
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
        String newPin = scanner.nextLine();
        newPin = StringUtil.formatNumericString(newPin);
        if (newPin.equals("0"))
            home();
        else {
            boolean isValid = newPin.length() == 6 && StringUtil.isNumericString(newPin);
            if (isValid) {
                cardController.pinChange(newPin);
            } else {
                System.out.println("Invalid pin!");
                pinChange();
            }
        }
    }

    public void balanceInquiry() {
        accountController.balanceInquiry();
    }

    @Override
    public void displayBalanceInquiry(Account account) {
        System.out.println("-------------------------");
        System.out.println("Account number: " + account.getNumber());
        System.out.println("User: " + account.getName());
        System.out.println("Balance: " + StringUtil.amountToString(account.getAmount()));
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
                accountController.cashWithdrawal(Constants.CASH_200);
                break;
            case 2:
                accountController.cashWithdrawal(Constants.CASH_500);
                break;
            case 3:
                accountController.cashWithdrawal(Constants.CASH_1M);
                break;
            case 4:
                accountController.cashWithdrawal(Constants.CASH_1M5);
                break;
            case 5:
                accountController.cashWithdrawal(Constants.CASH_2M);
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
        String number = scanner.nextLine();
        number = StringUtil.formatNumericString(number);
        if (number.equals("0"))
            home();
        else {
            if (isValidOtherAmountCashWithdrawal(number)) {
                accountController.cashWithdrawal(Long.parseLong(number));
            } else {
                System.out.println("Invalid amount number!");
                enterOtherAmountCashWithdrawal();
            }
        }
    }

    private boolean isValidOtherAmountCashWithdrawal(String number) {
        return StringUtil.isNumericString(number) && Long.parseLong(number) % 50000 == 0;
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
        String accountNumber = scanner.nextLine();
        accountNumber = StringUtil.formatNumericString(accountNumber);
        if (accountNumber.equals("0"))
            home();
        else {
            boolean isValid = accountNumber.length() == 5 && StringUtil.isNumericString(accountNumber);
            if (isValid) {
                accountController.checkReceiveAccount(accountNumber);
            } else {
                System.out.println("Invalid account number!");
                enterAccountTransfer();
            }
        }
    }

    @Override
    public void enterAmountTransfer(String accountNumberReceive, String accountNameReceive, String... messages) {
        System.out.println("-------------------------");
        System.out.println("Account receive : ");
        System.out.println("Name: " + accountNameReceive);
        System.out.println("Account number: " + accountNumberReceive + "\n");
        StringUtil.printMessages(messages);
        System.out.println("(Enter 0 to back to list options)");
        System.out.println("Enter number amount transfer: ");
        String amountTransfer = scanner.nextLine();
        amountTransfer = StringUtil.formatNumericString(amountTransfer);
        if (amountTransfer.equals("0"))
            home();
        else {
            boolean isValid = StringUtil.isNumericString(amountTransfer);
            if (isValid) {
                accountController.transfer(accountNumberReceive, Long.parseLong(amountTransfer));
            } else {
                System.out.println("Invalid amount number!");
                enterAmountTransfer(accountNumberReceive, accountNameReceive);
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
        accountController.printStatement();
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
        cardController.logout();
    }

    private void nextAction() {
        System.out.println("-----");
        System.out.println("1.List options");
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
                    System.out.println("(Enter a number in list)");
            } catch (InputMismatchException e) {
                scanner.nextLine();
                choice = -1;
                System.out.println("(Enter a number in list)");
            }
        } while (choice > quantityChoices || choice < 1);
        return choice;
    }
}
