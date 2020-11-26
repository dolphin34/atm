package com.vndirect.atm.exception;

public class CardLockedException extends Exception {

    public CardLockedException() {
        super("Your card is locked!");
    }
}
