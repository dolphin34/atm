package com.vndirect.atm.exception;

public class LockCardException extends Exception {

    public LockCardException() {
        super("Your card is locked!");
    }
}
