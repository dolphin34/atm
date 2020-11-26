package com.vndirect.atm.ui.impl.console;

import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NullException;
import com.vndirect.atm.exception.PinWrongException;
import com.vndirect.atm.ui.Login;

public class LoginImpl extends AtmView implements Login {

    private static final Login INSTANCE = new LoginImpl();

    public static Login getView() {
        return INSTANCE;
    }

    @Override
    public void insertCard() {
        String notify = "Card number (8 digit and start with 0800): ";
        String cardNumber = input(notify);
        try {
            if (VALIDATOR.isValidCardNumber(cardNumber) && SESSION.getCurrentCard().isActive()) {
                enterPin(1);
            } else {
                System.out.println("Your card is locked!");
                insertCard();
            }
        } catch (InvalidInputException | NullException e) {
            System.out.println(e.getMessage());

            showTwoNextAction(STRING_TRY_AGAIN, "Out", this::insertCard, () -> System.exit(0));
        }
    }

    @Override
    public void enterPin(int time) {
        String notify = "Hello, " + SESSION.getCurrentCard().getName() + "\nPin (6 digits): ";
        String pin = input(notify);
        int timeWrong;
        try {
            if (VALIDATOR.isValidPin(SESSION.getCurrentCard(), pin)) {
                String accountNumber = SESSION.getCurrentCard().getAccountNumber();
                SESSION.setCurrentAccount(ACCOUNT_SERVICE.findAccountModelByNumber(accountNumber));
                HomeImpl.getView().showMenu();
            }
        } catch (InvalidInputException | PinWrongException e) {
            if (time > 3 && CARD_SERVICE.lockCard(SESSION.getCurrentCard())) {
                SESSION.getCurrentCard().setActive(false);
                System.out.println("Your card is locked!");
                insertCard();
            } else {
                System.out.println("Pin wrong " + time + " time! (card wil be locked over 3 time)");
                timeWrong = ++time;
                showTwoNextAction(STRING_TRY_AGAIN, "Go back", () -> enterPin(timeWrong), this::insertCard);
            }
        }
    }

    @Override
    public void logout() {
        SESSION.setCurrentCard(null);
        SESSION.setCurrentAccount(null);
        insertCard();
    }
}
