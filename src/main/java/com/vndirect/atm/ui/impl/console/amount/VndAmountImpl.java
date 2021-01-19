package com.vndirect.atm.ui.impl.console.amount;

import com.vndirect.atm.ui.Amount;

public class VndAmountImpl implements Amount {

    @Override
    public String toString(long amount) {
        return  String.format("%,d", amount) + " VND  ";
    }
}
