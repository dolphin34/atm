package com.vndirect.atm.ui.impl.console;

import com.vndirect.atm.exception.FailActionException;
import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NullException;
import com.vndirect.atm.exception.PinWrongException;
import com.vndirect.atm.model.AccountModel;
import com.vndirect.atm.model.CardModel;
import com.vndirect.atm.ui.Login;

import java.util.Optional;

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
            VALIDATOR.isValidCardNumber(cardNumber);
            Optional<CardModel> card = CARD_SERVICE.findByNumber(cardNumber);
            card.ifPresent(SESSION::setCurrentCard);
            if (SESSION.getCurrentCard().isActive()) {
                enterPin(1);
            } else {
                System.out.println("Your card is locked!");
                showTwoNextAction(STRING_TRY_AGAIN, "Out", this::insertCard, () -> System.exit(0));
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
            VALIDATOR.isValidPin(SESSION.getCurrentCard(), pin);
            SESSION.getCurrentCard().setPin(pin);
            Optional<AccountModel> account = ACCOUNT_SERVICE.findByNumber(SESSION.getCurrentCard().getAccountNumber());
            if (account.isPresent()) {
                SESSION.setCurrentAccount(account.get());
                HomeImpl.getView().showMenu();
            } else  {
                System.out.println("Account is not exist!");
            }
        } catch (InvalidInputException | PinWrongException e) {
            if (time > 3) {
                try {
                    CARD_SERVICE.lockCard(SESSION.getCurrentCard());
                    SESSION.getCurrentCard().setActive(false);
                    System.out.println("Your card is locked!");
                    insertCard();
                } catch (FailActionException e1) {
                    System.out.println("Lock card fail!");
                }
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
