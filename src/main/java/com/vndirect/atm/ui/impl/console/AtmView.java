package com.vndirect.atm.ui.impl.console;

import com.vndirect.atm.service.AccountService;
import com.vndirect.atm.service.CardService;
import com.vndirect.atm.service.serviceimpl.AccountServiceImpl;
import com.vndirect.atm.service.serviceimpl.CardServiceImpl;
import com.vndirect.atm.session.Session;
import com.vndirect.atm.validator.Validator;
import com.vndirect.atm.validator.impl.ValidatorImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AtmView {

    private static final Scanner scanner = new Scanner(System.in);

    public static final Session SESSION = new Session();

    protected static final Validator VALIDATOR = new ValidatorImpl();

    protected static final CardService CARD_SERVICE = new CardServiceImpl();

    protected static final AccountService ACCOUNT_SERVICE= new AccountServiceImpl();

    protected static final String MULTI_DASH = "--------------------";

    protected static final String STRING_TRY_AGAIN = "Try again";

    protected static final String STRING_MENU = "Menu";

    protected static final String STRING_LOGOUT = "Logout";

    @FunctionalInterface
    interface Action {
        void run();
    }

    public void start() {
        LoginImpl.getView().insertCard();
    }

    protected String input(String notifyString) {
        System.out.println(MULTI_DASH);
        System.out.println(notifyString);
        return scanner.nextLine();
    }

    protected void showOptions(String... options) {
        System.out.println(MULTI_DASH);
        System.out.println("Options :");
        for (int i = 0; i < options.length; i++) {
            System.out.println(i+1 + ". " + options[i]);
        }
    }

    protected int enterChoiceOfAction(int quantityChoices) {
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

    protected void showTwoNextAction(String nameOfAction1, String nameOfAction2, Action action1, Action action2) {
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
}
