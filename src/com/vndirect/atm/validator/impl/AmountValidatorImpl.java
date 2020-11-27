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
        boolean isValidAmount = StringUtils.isNumericString(amount);
        if (!isValidAmount) {
            throw new InvalidInputException();
        }
    }

    @Override
    public void confirmAmountCashWithdrawal(String amount) throws NotEnoughBalanceException, InvalidInputException {
        amount = amount.trim();
        boolean isValidAmountCashWithdrawal = Long.parseLong(amount) % 50_000 == 0;
        if (!isValidAmountCashWithdrawal) {
            throw new InvalidInputException("Number is multiples of 50,000");
        }
        boolean isEnoughAmount = Long.parseLong(amount) <= SESSION.getCurrentAccount().getAmount() - (Constants.MINIMUM_BALANCE + Constants.CASH_WITHDRAWAL_FEE);
        if (!isEnoughAmount) {
            throw new NotEnoughBalanceException();
        }
    }

    @Override
    public void confirmAmountTransfer(String amount) throws NotEnoughBalanceException {
        amount = amount.trim();
        boolean isEnoughAmount = Long.parseLong(amount) <= SESSION.getCurrentAccount().getAmount() - (Constants.MINIMUM_BALANCE + Constants.TRANSFER_FEE);
        if (!isEnoughAmount) {
            throw new NotEnoughBalanceException();
        }
    }
}
