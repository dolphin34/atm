package com.vndirect.atm.util;

public class Constants {

    public static final long CASH_WITHDRAWAL_FEE = 1000;

    public static final long TRANSFER_FEE = 2000;

    public static final long MINIMUM_BALANCE_FOR_CASH_WITHDRAWAL = 50_000 + CASH_WITHDRAWAL_FEE;

    public static final long MINIMUM_BALANCE_FOR_TRANSFER = 50_000 + TRANSFER_FEE;
}
