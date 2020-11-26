package com.vndirect.atm.validator.impl;

import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NotEnoughBalanceException;
import com.vndirect.atm.util.Constants;
import com.vndirect.atm.util.StringUtils;
import com.vndirect.atm.validator.AmountValidator;

import static com.vndirect.atm.ui.impl.console.AtmView.SESSION;

public class AmountValidatorImpl implements AmountValidator {

    @Override
    public void validateAmount(String amount) throws InvalidInputException {
        amount = amount.trim();
        boolean isValidAmount =  StringUtils.isNumericString(amount) && Long.parseLong(amount) % 50_000 == 0;
        if (!isValidAmount) {
            throw new InvalidInputException("Number is multiples of 50,000");
        }
    }

    @Override
    public void confirmAmountCashWithdrawal(String amount) throws NotEnoughBalanceException {
        amount = amount.trim();
        boolean isEnoughAmount = Long.parseLong(amount) <= SESSION.getCurrentAccount().getAmount() - (Constants.MINIMUM_BALANCE + Constants.CASH_WITHDRAWAL_FEE);
        if (!isEnoughAmount) {
            throw new NotEnoughBalanceException();
        }
    }
}
