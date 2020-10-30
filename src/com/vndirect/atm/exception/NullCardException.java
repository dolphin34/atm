package com.vndirect.atm.exception;

public class NullCardException extends Exception {

    public NullCardException() {
        super("Card does not exist!");
    }
}
