package com.vndirect.atm.validator;

import com.vndirect.atm.exception.InvalidInputException;
import com.vndirect.atm.exception.NullException;
import com.vndirect.atm.service.AccountService;

public interface AccountValidator {

    void validateAccountNumber(String accountNumber) throws InvalidInputException;

    void existAccountNumber(AccountService accountService, String accountNumber) throws NullException;
}
