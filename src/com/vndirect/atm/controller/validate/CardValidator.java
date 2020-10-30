package com.vndirect.atm.controller.validate;

import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.LockCardException;
import com.vndirect.atm.exception.NullCardException;
import com.vndirect.atm.exception.PinWrongException;
import com.vndirect.atm.userinterface.View;
import com.vndirect.atm.userinterface.consoleviewImpl.ConsoleViewImpl;

public interface CardValidator {

    View VIEW = new ConsoleViewImpl();

    void checkCardNumber(String cardNumber) throws InvalidInputException, LockCardException, NullCardException;
    void checkPin(String pin, int time) throws InvalidInputException, PinWrongException, LockCardException;
}
