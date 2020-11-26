package com.vndirect.atm.ui.impl.console;

import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.ui.ChangePin;

public class ChangPinImpl extends AtmView implements ChangePin {

    private static final ChangePin INSTANCE = new ChangPinImpl();

    public static ChangePin getView() {
        return INSTANCE;
    }

    @Override
    public void enterOldPin() {
        String notify = "Enter your old pin (6 digits): ";
        String oldPin = input(notify);
        if (SESSION.getCurrentCard().getPin().equals(oldPin)) {
            enterNewPin();
        } else {
            System.out.println("Old pin is wrong!");
            showTwoNextAction(STRING_TRY_AGAIN, STRING_MENU, this::enterOldPin, HomeImpl.getView()::showMenu);
        }
    }

    @Override
    public void enterNewPin() {
        String notify = "Enter your new pin (6 digits): ";
        String newPin = input(notify);
        try {
            if (!newPin.equals(SESSION.getCurrentCard().getPin())) {
                VALIDATOR.getCardValidator().validatePin(newPin);
                confirmNewPin(newPin);
            } else {
                System.out.println("New pin can not the same with old pin!");
                showTwoNextAction(STRING_TRY_AGAIN, STRING_MENU, this::enterNewPin, HomeImpl.getView()::showMenu);
            }
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
            showTwoNextAction(STRING_TRY_AGAIN, STRING_MENU, this::enterNewPin, HomeImpl.getView()::showMenu);
        }
    }

    @Override
    public void confirmNewPin(String newPin) {
        String notify = "Enter your new pin again (6 digits): ";
        String confirmPin = input(notify);
        if (confirmPin.equals(newPin)) {
            if (CARD_SERVICE.changePin(SESSION.getCurrentCard(), newPin)) {
                System.out.println("Change pin success!");
            } else {
                System.out.println("Change pin fail!");
            }
            showTwoNextAction(STRING_MENU, STRING_LOGOUT, HomeImpl.getView()::showMenu, LoginImpl.getView()::logout);
            HomeImpl.getView().showMenu();
        } else {
            System.out.println("New pin not the same!");
            showTwoNextAction(STRING_TRY_AGAIN, STRING_MENU, this::enterNewPin, HomeImpl.getView()::showMenu);
        }
    }
}
