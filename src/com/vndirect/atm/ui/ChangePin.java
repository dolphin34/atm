package com.vndirect.atm.ui;

import com.vndirect.atm.exception.PinWrongException;

public interface ChangePin {

    void enterOldPin();
    void enterNewPin();
    void confirmNewPin(String newPin);
}
