package com.vndirect.atm.exception;

public class PinWrongException extends Exception {

    public PinWrongException(int wrongTimes) {
        super("Pin is wrong (" + wrongTimes + " time)! (card will be lock over 3 time wrong)");
    }
}