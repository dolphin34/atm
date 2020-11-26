package com.vndirect.atm.exception;

public class PinWrongException extends Exception {
    
    public PinWrongException() {
    }

    public PinWrongException(String message) {
        super(message);
    }
}