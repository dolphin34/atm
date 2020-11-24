package com.vndirect.atm.util;

import com.vndirect.atm.controller.repo.data.Data;

public class Constants {

    public static final Data DATA = new Data();

    public static final long MINIMUM_BALANCE = 50_000;
    public static final long TRANSFER_FEE = 2000;
    public static final long CASH_WITHDRAWAL_FEE = 1000;

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}
