package com.vndirect.atm.exception;

public class NotEnoughCashInAtmException extends Exception {

    public NotEnoughCashInAtmException() {
        super("Not enough money in ATM!");
    }
}
