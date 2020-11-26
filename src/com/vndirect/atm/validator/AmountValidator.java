package com.vndirect.atm.validator;

import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NotEnoughBalanceException;

public interface AmountValidator {

    void validateAmount(String amount) throws InvalidInputException;
    void confirmAmountCashWithdrawal(String amount) throws NotEnoughBalanceException;


}
