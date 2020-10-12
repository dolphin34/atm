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
        System.out.println("Card Number (8 digit and start with 0800): ");
        String cardNumber = scanner.nextLine();
        cardNumber = StringUtil.formatNumericString(cardNumber);
        boolean isValid = cardNumber.length() == 8 && cardNumber.startsWith("0800") && StringUtil.isNumericString(cardNumber);
        if (isValid) {
            cardController.checkCard(cardNumber);
        } else {
            System.out.println("Invalid Card Number!");
            insertCard();
        }
    }

    @Override
    public void enterPin(String userName, int time, String... message) {
        System.out.println("-------------------------");
        System.out.println("Hello, " + userName);
        StringUtil.printMessages(message);
        System.out.println("Pin (6 digits): ");
        String pin = scanner.nextLine();
        pin = StringUtil.formatNumericString(pin);
        boolean isValid = pin.length() == 6 && StringUtil.isNumericString(pin);
        if (isValid) {
            cardController.checkPin(pin, time);
        } else {
            System.out.println("Invalid Pin!");
            enterPin(userName, time);
        }
    }

    @Override
    public void home() {
        System.out.println("-------------------------");
        System.out.println("1.PIN Change");
        System.out.println("2.Balance Inquiry");
        System.out.println("3.Cash Withdrawal");
        System.out.println("4.Transfer");
        System.out.println("5.Print Statement");
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
        System.out.println("Enter your new Pin (6 digits): ");
        String newPin = scanner.nextLine();
        newPin = StringUtil.formatNumericString(newPin);
        boolean isValid = newPin.length() == 6 && StringUtil.isNumericString(newPin);
        if (isValid) {
            cardController.pinChange(newPin);
        } else {
            System.out.println("Invalid PIN!");
            pinChange();
        }
    }

    public void balanceInquiry() {
        accountController.balanceInquiry();
    }

    @Override
    public void displayBalanceInquiry(Account account) {
        System.out.println("-------------------------");
        System.out.println("Account Number: " + account.getNumber());
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
                otherAmount();
                break;
        }
    }

    public void otherAmount() {
        System.out.println("-------------------------");
        System.out.println("Enter number (multiples of 50,000): ");
        String number = scanner.nextLine();
        number = StringUtil.formatNumericString(number);
        boolean isValid = StringUtil.isNumericString(number) && checkOtherAmount(number);
        if (isValid) {
            accountController.cashWithdrawal(Long.parseLong(number));
        } else {
            System.out.println("Invalid Number!");
            otherAmount();
        }
        nextAction();
    }

    private boolean checkOtherAmount(String number) {
        double temp = Long.parseLong(number);
        return temp != 0 && temp % 50000 == 0;
    }

    @Override
    public void displayResultCashWithdrawal(boolean success, String message, int[][] result) {
        System.out.println("-------------------------");
        if (success) {
            System.out.println(message);
            System.out.println("Cash: ");
            for (int i = 3; i >= 0; i--) {
                if (result[2][i] != 0) {
                    System.out.println(result[2][i] + " - " + StringUtil.amountToString(result[0][i]));
                }
            }
        } else {
            System.out.println(message);
        }
        nextAction();
    }

    @Override
    public void enterAccountTransfer() {
        System.out.println("-------------------------");
        System.out.println("Account Number Receive (5 digits):");
        String accountNumber = scanner.nextLine();
        accountNumber = StringUtil.formatNumericString(accountNumber);
        boolean isValid = accountNumber.length() == 5 && StringUtil.isNumericString(accountNumber);
        if (isValid) {
            if (accountController.checkReceiveAccount(accountNumber)) {
                enterAmountTransfer(accountNumber);
            }
            else {
                System.out.println("Account Number does not exist or wrong!");
                enterAccountTransfer();
            }
        } else {
            System.out.println("Invalid Account Number!");
            enterAccountTransfer();
        }
        nextAction();
    }

    @Override
    public void enterAmountTransfer(String accountNumberReceive) {
        System.out.println("-------------------------");
        System.out.println("Enter number: ");
        String amountTransfer = scanner.nextLine();
        amountTransfer = StringUtil.formatNumericString(amountTransfer);
        boolean isValid = StringUtil.isNumericString(amountTransfer);
        if (isValid) {
            accountController.transfer(accountNumberReceive, Long.parseLong(amountTransfer));
        } else {
            System.out.println("Invalid Number!");
            enterAmountTransfer(accountNumberReceive);
        }
        nextAction();
    }

    @Override
    public void displayResultTransfer(boolean success, String message, Account currentAccount, String accountNumberReceive, long amountTransfer) {
        System.out.println("-------------------------");
        System.out.println(message);
        if (success) {
            System.out.println("Account Number: " + currentAccount.getNumber());
            System.out.println("User: " + currentAccount.getName());
            System.out.println("Account Number Received: " + accountNumberReceive);
            System.out.println("Amount transfer: " + "-" + StringUtil.amountToString(amountTransfer));
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
