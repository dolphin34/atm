package com.vndirect.atm.validator.impl;

import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NullException;
import com.vndirect.atm.service.AccountService;
import com.vndirect.atm.util.StringUtils;
import com.vndirect.atm.validator.AccountValidator;

public class AccountValidatorImpl implements AccountValidator {

    @Override
    public void validateAccountNumber(String accountNumber) throws InvalidInputException {
        boolean isValid = StringUtils.isNumericString(accountNumber) ;
        if(!isValid) {
            throw new InvalidInputException();
        }
    }

    @Override
    public void existAccountNumber(AccountService accountService, String accountNumber) throws NullException {
        if (!accountService.findByNumber(accountNumber).isPresent()) {
            throw new NullException("Account is not exist!");
        }
    }
}
