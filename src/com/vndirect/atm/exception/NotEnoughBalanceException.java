package com.vndirect.atm.exception;

public class NotEnoughBalanceException extends Exception {

    public NotEnoughBalanceException() {
        super("Unavailable balance!");
    }
}
